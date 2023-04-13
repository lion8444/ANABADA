package com.anabada.controller.auction;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.domain.Auction;
import com.anabada.domain.AuctionAndFile;
import com.anabada.domain.Auction_bid;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.Category;
import com.anabada.domain.Damagochi;
import com.anabada.domain.File;
import com.anabada.domain.Location;
import com.anabada.domain.Rental;
import com.anabada.domain.Used;
import com.anabada.domain.UserDTO;
import com.anabada.service.login.LoginService;
import com.anabada.service.map.MapService;
import com.anabada.service.mypage.MyPageService;
import com.anabada.domain.Wish;
import com.anabada.service.auction.AuctionService;
import com.anabada.service.wish.WishService;
import com.anabada.util.FileService;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({ "auction" })
@Controller
public class AuctionController {

	@Autowired
	private AuctionService service;
	
	@Autowired
	private LoginService lservice;
	
	@Autowired
	private MyPageService mpservice; 
	
	// 위시리스트 관련 서비스
	@Autowired
	WishService wservice;

	@Autowired
	MapService mservice;

	// 설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입(from application.properites)
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	// 페이지 당 글 수
	@Value("${user.board.page}")
	int countPerPage;

	// 페이지당 이동링크 수
	@Value("${user.board.group}")
	int pagePerGroup;

	@GetMapping({ "purchase" })
	public String purchase(String auction_id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
		UserDTO user = service.findUser(userDetails.getUsername());
		model.addAttribute("user", user);
		
		Auction auction = service.findOneAuction(auction_id);
		Auction_detail auction_detail = service.findOneAuctiondetail(auction_id);
		ArrayList<File> fileList = service.fileListByid(auction_id);
		
		//0411
		Auction auction_sell = service.auctionBoardRead(auction_id);
		UserDTO target = service.findUser(auction_sell.getUser_email());
		
		// 글쓴이의 다마고치 정보
		Damagochi dama = mpservice.selectMyDamaInfoById(auction_sell.getUser_email());
		
		model.addAttribute("target", target);
		model.addAttribute("auction_sell", auction_sell);
		
		model.addAttribute("auction", auction);
		model.addAttribute("auction_detail", auction_detail);
		model.addAttribute("fileList", fileList);
		model.addAttribute("dama", dama);

		return "auction/auctionPurchase(GBP).html";
	}

	@PostMapping({ "purchase" })
	public String purchase(
			Auction_detail auction_detail, int user_account, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		UserDTO user = service.findUser(userDetails.getUsername());
		model.addAttribute("user", user);
		auction_detail.setUser_email(userDetails.getUsername());
		int k = service.bid(auction_detail);

		int i = service.usemoney(userDetails.getUsername(), user_account);

		if (k == 0 || i == 0) {
			return "redirect:/auction/purchase?auction_id=" + auction_detail.getAuction_id();
		}

		return "redirect:/mypage/myauctionlistbid";
	}

	/**
	 * 옥션 게시판으로 이동(다 보여줌)
	 **/
	@GetMapping("/auctionBoard")
	public String auctionBoard(
			@AuthenticationPrincipal UserDetails userDetails
			,@RequestParam(name="page", defaultValue="1") int page
			, String type
			, String searchWord
			, String check
			, String auction_id
			, Model model) {

		PageNavigator navi = 
			service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord, check);
		
		log.debug("옥션보드진입");
		
		// 게시판 들어가면 그 시간에 경매 마감되는 게시물 '거래 완료'로 변경 후 안보이게 
		List<AuctionAndFile> listAll = mpservice.selectAuctionListAll();
		mpservice.updateAuctionStatus();
		log.debug("업데이트 처리");
		
		//s 페이지 들어갈 때 거래 완료인거 aTrade에 넣기
		int aDetailResult = mpservice.insertATrade(listAll);
//		int aDetailResult = mservice.insertATrade(listDetail);
//		log.debug("aTrade에 추가된 개수 : {}", aDetailResult); 
		
		ArrayList <Auction> auctionLists = service.auctionBoard(
				navi.getStartRecord(),countPerPage, type, searchWord, check, userDetails.getUsername());
		
		//0408 추가 
		ArrayList <Auction> auctionList = new ArrayList<>();
		for (Auction auction : auctionLists) {
			UserDTO target = lservice.findUser(auction.getUser_email());
			auction.setUser_nick(target.getUser_nick());
			auctionList.add(auction);
		}
		
		ArrayList <Auction_detail> auction_details = service.findAllAuctionDetail();
		
		for(int i = 0; i < auctionList.size(); ++i) {
			for(int j = 0; j < auction_details.size(); ++j) {
				log.info(auction_details.get(j).getADetail_price()+"");
				if (auctionList.get(i).getAuction_id().equals(auction_details.get(j).getAuction_id())) {
					auctionList.get(i).setAuction_price(auction_details.get(j).getADetail_price());
				}
			}
		}
		
		UserDTO user = service.findUser(userDetails.getUsername());
		
		model.addAttribute("user", user);
		model.addAttribute("auctionList",auctionList);
		model.addAttribute("navi",navi);
		model.addAttribute("type",type);
		model.addAttribute("searchWord",searchWord);
		model.addAttribute("check",check);		

		return "auction/auctionBoard(GB)";
	}

	/**
	 * 렌탈 상세 게시판으로 이동(한개 보여줌)
	 * 조회수
	 **/
	@GetMapping("auctionBoardRead")
	public String auctionBoardRead(
			@AuthenticationPrincipal UserDetails userDetails
			,@RequestParam(name="auction_id",defaultValue="0") String auction_id
			,Model model
			,@RequestParam(name="page", defaultValue="1") int page
			) {			
		PageNavigator navi = 
				service.getPageNavigator(pagePerGroup, countPerPage, page, null, null, null);
		ArrayList <Auction> auctionList = service.auctionBoard(
				navi.getStartRecord(),countPerPage, null, null, null, null);
		
		Auction auction_sell = service.auctionBoardRead(auction_id);
		ArrayList<File> fileList = service.fileListByid(auction_id);
		
		ArrayList <Auction_detail> auction_details = service.findAllAuctionDetail();
		
		for(int i = 0; i < auctionList.size(); ++i) {
			for(int j = 0; j < auction_details.size(); ++j) {
				log.info(auction_details.get(j).getADetail_price()+"");
				if (auctionList.get(i).getAuction_id().equals(auction_details.get(j).getAuction_id())) {
					auctionList.get(i).setAuction_price(auction_details.get(j).getADetail_price());
					break;
				}
			}
		}
		
		UserDTO target = service.findUser(auction_sell.getUser_email());
		UserDTO user = service.findUser(userDetails.getUsername());
		model.addAttribute("user", user);
		
		// 위시리스트 유무 정보 가져오기
		Wish wish = wservice.selectWish(auction_id, userDetails.getUsername());
		
		Auction auction = service.findOneAuction(auction_id);
		Location location = mservice.findBoardLocation(auction_id);
		
		// 글쓴이의 다마고치 정보
		Damagochi dama = mpservice.selectMyDamaInfoById(auction_sell.getUser_email()); 
		
		model.addAttribute("location", location);
		model.addAttribute("auction_details", auction_details);
		model.addAttribute("auctionList",auctionList);
		model.addAttribute("auction", auction);
		model.addAttribute("target", target);
		model.addAttribute("auction_sell", auction_sell);
		model.addAttribute("fileList", fileList);
		model.addAttribute("wish", wish);
		model.addAttribute("dama", dama);
		return "auction/auctionBoardRead(GBR)";
	}

	/**
	 * 옥션 글 삭제
	 **/

	@GetMapping("auctionBoardDelete")
	public String auctionBoardDelete(
			@RequestParam(name = "auction_id", defaultValue = "0") String auction_id,
			@RequestParam(name = "file_saved", defaultValue = "") String file_saved, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		UserDTO user = service.findUser(userDetails.getUsername());
		model.addAttribute("user", user);

		Auction auction = service.auctionBoardRead(auction_id);
		// 해당번호의 글이 있는지 확인. 없으면 글목록으로
		if (auction == null) {
			return "redirect:/";
		}
		// 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로
		if (!auction.getUser_email().equals(userDetails.getUsername())) {
			return "redirect:/";
		}
		// 첨부된 파일이 있으면 파일삭제
		if (!file_saved.isEmpty()) {
			FileService.deleteFile(uploadPath + "/" + file_saved);
		}
		// 실제 글 DB에서 삭제
		service.auctionBoardDelete(auction);
		// 글 목록으로 리다이렉트
		return "redirect:/";
	}

	/**
	 * 옥션 글쓰기 게시판으로 이동
	 **/
	@GetMapping("/auctionWrite")
	public String auctionWrite(
			Model model, @AuthenticationPrincipal UserDetails userDetails) {
		UserDTO user = service.findUser(userDetails.getUsername());
		model.addAttribute("user", user);
		ArrayList<Category> category_main = service.maincategory();
		model.addAttribute("category_main", category_main);
		return "auction/auctionWrite(GBW)";
	}

	/**
	 * 옥션 글쓰기 처리
	 **/
	@PostMapping("/auctionWrite")
	public String auctionWrite(
			@AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute Auction auction,
			Location location,
			@RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne) {

		// 로그인한 아이디 읽어서 board객체에 추가
		auction.setUser_email(userDetails.getUsername());

		String auction_id = service.auctionWrite(auction);

		location.setBoard_no(auction_id);
		mservice.insertLocation(location);

		if (uploadOne.isEmpty()) {
			return "redirect:/";
		}

		// 추가된 사진 처리
		if (!uploadOne.isEmpty()) {
			String filename = FileService.saveFile(uploadOne, uploadPath);
			File savedFile = new File();
			savedFile.setFile_origin(uploadOne.getOriginalFilename());
			savedFile.setFile_saved(filename);
			savedFile.setBoard_no(auction_id);
			savedFile.setBoard_status("옥션 거래");
			service.insertFile(savedFile);
		}

		if (!upload.get(0).isEmpty()) {
			for (int i = 0; i < upload.size(); ++i) {
				MultipartFile file = upload.get(i);
				String filename = FileService.saveFile(file, uploadPath);
				File savedFile = new File();
				savedFile.setFile_origin(file.getOriginalFilename());
				savedFile.setFile_saved(filename);
				savedFile.setBoard_no(auction_id);
				savedFile.setBoard_status("옥션 거래");
				service.insertFile(savedFile);
			}
		}
		return "redirect:/";
	}

	/**
	 * 옥션 글 수정
	 **/
	@GetMapping("auctionBoardUpdate")
	public String auctionBoardUpdate(
			@RequestParam(name = "auction_id", defaultValue = "0") String auction_id, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		UserDTO user = service.findUser(userDetails.getUsername());
		model.addAttribute("user", user);

		// 전달된 아이디의 글정보 읽기
		Auction auction = service.auctionBoardRead(auction_id);
		Location location = mservice.findBoardLocation(auction_id);


		// 본인 글인지 확인, 아니면 글목록으로 이동
		if (!auction.getUser_email().equals(userDetails.getUsername())) {
			return "redirect:/";
		}
		// 글정보를 모델에 저장
		model.addAttribute("auction", auction);
		model.addAttribute("location", location);
		ArrayList<Category> category_main = service.maincategory();
		model.addAttribute("category_main", category_main);

		// 수정을 html로 포워딩
		return "auction/auctionBoardUpdate.html";
	}

	// 수정폼에서 보낸 내용 처리
	@PostMapping("auctionBoardUpdate")
	public String auctionBoardUpdate(
			@ModelAttribute Auction auction, @RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			Location location,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne,
			@AuthenticationPrincipal UserDetails userDetails) {

		Auction originAuction = service.findOneAuction(auction.getAuction_id());
		auction.setAuction_status(originAuction.getAuction_status());
		auction.setAuction_date(originAuction.getAuction_date());
		String auction_id = service.auctionBoardUpdate(auction);

		// 로그인한 사용자의 아이디를 읽음

		Location updateLoc = mservice.findBoardLocation(auction_id);
		location.setLoc_id(updateLoc.getLoc_id());
		mservice.updateLocation(location);

		if (auction_id == null) {
			return "redirect:/";
		}
		// 처음에 해당 글에 file있는지 여부 확인해서 isempty 그래서 있으면
		// 걍 싹 다 지우고 다시 저장하는데 순서가 맨 처음에 uploadOne 을 넣고 그다음에
		// 배열 돌리기

		// 추가된 사진 처리
		if (!uploadOne.isEmpty()) {
			ArrayList<File> files = service.fileListAll(auction_id);

			if (!files.isEmpty()) {
				// service.deleteFile
			}

			String filename = FileService.saveFile(uploadOne, uploadPath);
			File savedFile = new File();
			savedFile.setFile_origin(uploadOne.getOriginalFilename());
			savedFile.setFile_saved(filename);
			savedFile.setBoard_no(auction_id);
			savedFile.setBoard_status("옥션 거래");
			service.insertFile(savedFile);
		}

		for (int i = 0; i < upload.size(); ++i) {
			MultipartFile file = upload.get(i);
			if (!file.isEmpty()) {
				String filename = FileService.saveFile(file, uploadPath);
				File savedFile = new File();
				savedFile.setFile_origin(file.getOriginalFilename());
				savedFile.setFile_saved(filename);
				savedFile.setBoard_no(auction_id);
				savedFile.setBoard_status("옥션 거래");
				service.insertFile(savedFile);
			}
		}
		return "redirect:/";
	}

	@GetMapping({ "/imgshow" })
	public String download(HttpServletResponse response, String auction_id) {
		ArrayList<File> fileList = service.fileList();

		for (int i = 0; i < fileList.size(); ++i) {
			if (!fileList.get(i).getBoard_status().equals("옥션 거래")) {
				fileList.remove(i);
				--i;
			}
		}

		int index = 0;

		for (int i = 0; i < fileList.size(); ++i) {
			if (auction_id.equals(fileList.get(i).getBoard_no())) {
				index = i;
			}
		}

		String file = uploadPath + "/" + fileList.get(index).getFile_saved();

		FileInputStream in = null;
		ServletOutputStream out = null;

		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(fileList.get(index).getFile_origin(), "UTF-8"));
			in = new FileInputStream(file);
			out = response.getOutputStream();

			FileCopyUtils.copy(in, out);

			in.close();
			out.close();
		} catch (Exception e) {
			return "redirect:/";
		}
		return "redirect:/";
	}

	@GetMapping({ "/imgshowone" })
	public String download(HttpServletResponse response, String auction_id, int index) {
		log.info(index + "");

		ArrayList<File> fileList = service.fileListByid(auction_id);
		String file = uploadPath + "/" + fileList.get(index).getFile_saved();

		FileInputStream in = null;
		ServletOutputStream out = null;

		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(fileList.get(index).getFile_origin(), "UTF-8"));
			in = new FileInputStream(file);
			out = response.getOutputStream();

			FileCopyUtils.copy(in, out);

			in.close();
			out.close();
		} catch (Exception e) {
			return "redirect:/";
		}
		return "redirect:/";
	}
	
	@GetMapping("charge")
	public String charge() {
		return "/mypage/charge.html";
	}
}
