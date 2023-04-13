package com.anabada.controller.used;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
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

import com.anabada.domain.Category;
import com.anabada.domain.Damagochi;
import com.anabada.domain.File;
import com.anabada.domain.Location;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;
import com.anabada.domain.Wish;
import com.anabada.service.login.LoginService;
import com.anabada.service.map.MapService;
import com.anabada.service.mypage.MyPageService;
import com.anabada.service.used.UsedService;
import com.anabada.service.wish.WishService;
import com.anabada.util.FileService;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/used")
@Controller
public class UsedController {
	@Autowired
	UsedService service;

	@Autowired
	LoginService lservice;
	
	// 위시리스트 관련 서비스
	@Autowired
	WishService wservice;
	
	// 마이페이지 관련 서비스
	@Autowired
	MyPageService mservice;

	// 설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입(from application.properites)

	@Autowired
	MapService mapservice;
	
	//설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입(from application.properites)
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	// 페이지 당 글 수
	@Value("${user.board.page}")
	int countPerPage;

	// 페이지당 이동링크 수
	@Value("${user.board.group}")
	int pagePerGroup;

	/**
	 * 중고거래 팝니다 게시판으로 이동(다 보여줌) ok 0324
	 **/
	@GetMapping("/usedSellBoard") // @GetMapping("/SellBoard") 리퀘스트 매핑 넣고 바꿔주기
	public String usedSellBoard(
			@AuthenticationPrincipal UserDetails userDetails
			,@RequestParam(name="page", defaultValue="1") int page
			, String type
			, String searchWord
			, String check
			, Model model
			, HttpServletResponse response) {
		PageNavigator navi = 
			service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord, check);
		
		String email = null;
		
		if(userDetails != null) {
		email = userDetails.getUsername();
		}
		ArrayList <Used> usedList = service.usedSellBoard(
				navi.getStartRecord(),countPerPage, type, searchWord, check, email);

		ArrayList <Used> usedSellList = new ArrayList<>();
		for (Used used : usedList) {
			UserDTO target = lservice.findUser(used.getUser_email());
			used.setUser_nick(target.getUser_nick());
			usedSellList.add(used);
		}
		UserDTO user = lservice.findUser(userDetails.getUsername());
		
		model.addAttribute("usedSellList",usedSellList);
		model.addAttribute("navi",navi);
		model.addAttribute("type",type);
		model.addAttribute("searchWord",searchWord);
		model.addAttribute("user", user);
		model.addAttribute("check",check);		
		
		return "used/usedSellBoard(JPB)";
	}

	/**
	 * 중고거래 팝니다 게시판으로 이동(한개 보여줌)
	 * 조회수
	 **/
	@GetMapping("usedSellBoardRead")
	public String usedSellBoardRead(
			@AuthenticationPrincipal UserDetails userDetails
			,@RequestParam(name="used_id",defaultValue="0") String used_id
			,Model model
			,@RequestParam(name="page", defaultValue="1") int page
			) {
		PageNavigator navi = 
				service.getPageNavigator(pagePerGroup, countPerPage, page, null, null, null);
		ArrayList <Used> usedSellList = service.usedSellBoard(
				navi.getStartRecord(),countPerPage, null, null, null, null);
		
		model.addAttribute("usedSellList",usedSellList);
		
		// used_sell 정보 가져오기
		Used used_sell = service.usedSellBoardRead(used_id);
		ArrayList<File> fileList = service.fileListByid(used_id);

		Location location = mapservice.findBoardLocation(used_id);

		UserDTO user = lservice.findUser(userDetails.getUsername());
		UserDTO targetUser = lservice.findUser(used_sell.getUser_email());
		
		// 위시리스트 유무 정보 가져오기
		Wish wish = wservice.selectWish(used_id, userDetails.getUsername());
		
		// 글쓴이의 다마고치 정보
		Damagochi dama = mservice.selectMyDamaInfoById(used_sell.getUser_email());
		
		model.addAttribute("user", user);
		model.addAttribute("target", targetUser);
		model.addAttribute("location", location);

		model.addAttribute("used_sell", used_sell);
		model.addAttribute("fileList", fileList);
		
		model.addAttribute("wish", wish);
		model.addAttribute("dama", dama);

		return "used/usedSellBoardRead(JPBR)";
	}

	/**
	 * 중고거래 팝니다 글 삭제 ok 0324
	 **/

	@GetMapping("usedSellBoardDelete")
	public String usedSellBoardDelete(
			@RequestParam(name = "used_id", defaultValue = "0") String used_id,
			@RequestParam(name = "file_saved", defaultValue = "") String file_saved,
			@AuthenticationPrincipal UserDetails userDetails, Model model) {

		Used used = service.usedSellBoardRead(used_id);
		// 해당번호의 글이 있는지 확인. 없으면 글목록으로
		if (used == null) {
			return "redirect:/";
		}
		// 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로
		if (!used.getUser_email().equals(userDetails.getUsername())) {
			return "redirect:/";
		}
		// 첨부된 파일이 있으면 파일삭제
		if (!file_saved.isEmpty()) {
			FileService.deleteFile(uploadPath + "/" + file_saved);
		}
		UserDTO user = lservice.findUser(userDetails.getUsername());
		UserDTO targetUser = lservice.findUser(used.getUser_email());
		model.addAttribute("user", user);
		model.addAttribute("target", targetUser);
		// 실제 글 DB에서 삭제
		service.usedSellBoardDelete(used);
		// 글 목록으로 리다이렉트
		return "redirect:/";
	}

	/**
	 * 중고거래 팝니다 글쓰기 게시판으로 이동
	 **/
	@GetMapping("/usedSellWrite")
	public String usedSellWrite(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());
		// UserDTO targetUser = lservice.findUser(used_sell.getUser_email());
		model.addAttribute("user", user);
		ArrayList<Category> category_main = service.maincategory();
		model.addAttribute("category_main", category_main);
		// model.addAttribute("target", targetUser);
		return "used/usedSellWrite(JPBW)";
	}

	/**
	 * 중고거래 팝니다 글쓰기 처리
	 **/
	@PostMapping("/usedSellWrite")
	public String usedSellWrite(
			@AuthenticationPrincipal UserDetails userDetails,
			@ModelAttribute Used used,
			Location location,
			@RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne) {

		// 로그인한 아이디 읽어서 board객체에 추가
		used.setUser_email(userDetails.getUsername());

		if (uploadOne.isEmpty()) {
			return "redirect:/";
		}
		
		String used_id = service.usedSellWrite(used, uploadOne);
		
		if (used_id.equals("0")) {
			return "redirect:/";
		}
		
		location.setBoard_no(used_id);
		mapservice.insertLocation(location);

		// 추가된 사진 처리
		if (!uploadOne.isEmpty()) {
			String filename = FileService.saveFile(uploadOne, uploadPath);
			File savedFile = new File();
			savedFile.setFile_origin(uploadOne.getOriginalFilename());
			savedFile.setFile_saved(filename);
			savedFile.setBoard_no(used_id);
			savedFile.setBoard_status("중고 거래");
			service.insertFile(savedFile);
		}

		if (!upload.get(0).isEmpty()) {
			for (int i = 0; i < upload.size(); ++i) {
				MultipartFile file = upload.get(i);
				String filename = FileService.saveFile(file, uploadPath);
				File savedFile = new File();
				savedFile.setFile_origin(file.getOriginalFilename());
				savedFile.setFile_saved(filename);
				savedFile.setBoard_no(used_id);
				savedFile.setBoard_status("중고 거래");
				service.insertFile(savedFile);
			}
		}

		return "redirect:/";
	}

	/**
	 * 중고거래 팝니다 글 수정(사용자 측)
	 **/
	@GetMapping("usedSellBoardUpdate")
	public String usedSellBoardUpdate(
			Model model, @RequestParam(name = "used_id", defaultValue = "0") String used_id,
			@AuthenticationPrincipal UserDetails userDetails) {

		// 전달된 아이디의 글정보 읽기
		Used used = service.usedSellBoardRead(used_id);
		Location location = mapservice.findBoardLocation(used_id);

		// 본인 글인지 확인, 아니면 글목록으로 이동
		if (!used.getUser_email().equals(userDetails.getUsername())) {
			return "redirect:/";
		}
		// 글정보를 모델에 저장
		model.addAttribute("used", used);
		model.addAttribute("location", location);
		ArrayList<Category> category_main = service.maincategory();
		model.addAttribute("category_main", category_main);

		// 수정을 html로 포워딩
		return "used/usedSellBoardUpdate.html";
	}

	// 수정폼에서 보낸 내용 처리
	@PostMapping("usedSellBoardUpdate")
	public String usedSellBoardUpdate(
			@ModelAttribute Used used, @RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			Location location,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne,
			@AuthenticationPrincipal UserDetails userDetails) {
		String used_id = service.usedSellBoardUpdate(used);
		
		

		Location updateLoc = mapservice.findBoardLocation(used_id);
		location.setLoc_id(updateLoc.getLoc_id());
		mapservice.updateLocation(location);

		if (used_id == null) {
			return "redirect:/";
		}
		// 처음에 해당 글에 file있는지 여부 확인해서 isempty 그래서 있으면
		// 걍 싹 다 지우고 다시 저장하는데 순서가 맨 처음에 uploadOne 을 넣고 그다음에
		// 배열 돌리기

		// 추가된 사진 처리
		if (!uploadOne.isEmpty()) {
			ArrayList<File> files = service.fileListAll(used_id);

			if (!files.isEmpty()) {
				// service.deleteFile
			}

			String filename = FileService.saveFile(uploadOne, uploadPath);
			File savedFile = new File();
			savedFile.setFile_origin(uploadOne.getOriginalFilename());
			savedFile.setFile_saved(filename);
			savedFile.setBoard_no(used_id);
			savedFile.setBoard_status("중고 거래");
			service.insertFile(savedFile);
		}

		for (int i = 0; i < upload.size(); ++i) {
			MultipartFile file = upload.get(i);
			if (!file.isEmpty()) {
				String filename = FileService.saveFile(file, uploadPath);
				File savedFile = new File();
				savedFile.setFile_origin(file.getOriginalFilename());
				savedFile.setFile_saved(filename);
				savedFile.setBoard_no(used_id);
				savedFile.setBoard_status("중고 거래");
				service.insertFile(savedFile);
			}
		}

		return "redirect:/";
	}

	/**
	 * 중고거래 삽니다 글쓰기 게시판으로 이동 ok 0324
	 **/
	@GetMapping("/usedBuyWrite")
	public String usedBuyWrite(
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());
		// UserDTO targetUser = lservice.findUser(used_sell.getUser_email());
		model.addAttribute("user", user);
		// model.addAttribute("target", targetUser);
		return "used/usedBuyWrite(JSBW)";
	}

	/**
	 * 중고거래 삽니다 글목록 게시판으로 이동 ok 0324
	 **/

	@PostMapping("/usedBuyWrite")
	public String usedBuyWrite(
			@AuthenticationPrincipal UserDetails userDetails, Used_buy used_buy) {

		// 전달된 객체에 로그인한 아이디 추가 저장
		used_buy.setUser_email(userDetails.getUsername());

		service.usedBuyWrite(used_buy);
		return "redirect:/";
	}

	/**
	 * 중고거래 삽니다 글 읽기 게시판으로 이동 ok 0324
	 **/
	@GetMapping("/usedBuyBoard")
	public String usedBuyBoard(
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());
		// UserDTO targetUser = lservice.findUser(used_sell.getUser_email());
		model.addAttribute("user", user);
		// model.addAttribute("target", targetUser);

		ArrayList<Used_buy> bboardlist = service.usedBuyBoard();
		
		model.addAttribute("bboardlist", bboardlist);
		
		return "used/usedBuyBoard(JSB)";
	}

	/**
	 * 중고거래 삽니다 글 하나 읽기 게시판으로 이동 ok 0324
	 **/
	@GetMapping("usedBuyBoardRead")
	public String usedBuyBoardRead(
			@RequestParam(name = "uBuy_id", defaultValue = "0") String uBuy_id,
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		Used_buy used_buy = service.usedBuyBoardRead(uBuy_id);
		 log.debug("넘어오는 값: {}",uBuy_id);
		UserDTO user = lservice.findUser(userDetails.getUsername());
		UserDTO targetUser = lservice.findUser(used_buy.getUser_email());

		model.addAttribute("used_buy", used_buy);
		model.addAttribute("user", user);
		model.addAttribute("target", targetUser);

		return "used/usedBuyBoardRead(JSBR)";
	}

	/**
	 * 중고거래 삽니다 삭제 다른쪽으로 넘어가게 설정 OK
	 **/

	@GetMapping("usedBuyBoardDelete")
	public String usedBuyBoardDelete(
			@RequestParam(name = "uBuy_id", defaultValue = "0") String uBuy_id,
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());
		// UserDTO targetUser = lservice.findUser(used_sell.getUser_email());
		model.addAttribute("user", user);
		// model.addAttribute("target", targetUser);
		Used_buy used_buy = service.usedBuyBoardRead(uBuy_id);
		if (used_buy == null) {
			return "redirect:list";
		}
		// 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로
		if (!used_buy.getUser_email().equals(userDetails.getUsername())) {
			return "redirect:/";
		}
		int result = service.usedBuyBoardDelete(used_buy);

		return "redirect:/";
	}

	/**
	 * 중고거래 삽니다 글 수정 ok 0324
	 **/
	@GetMapping("usedBuyBoardUpdate")
	public String update(
			Model model, @RequestParam(name = "uBuy_id", defaultValue = "0") String uBuy_id,
			@AuthenticationPrincipal UserDetails userDetails) {
		UserDTO user = lservice.findUser(userDetails.getUsername());
		// UserDTO targetUser = lservice.findUser(used_sell.getUser_email());
		model.addAttribute("user", user);
		// model.addAttribute("target", targetUser);
		Used_buy used_buy = service.usedBuyBoardRead(uBuy_id);
		log.info(used_buy + "");

		// 본인 글인지 확인, 아니면 글목록으로 이동
		if (!used_buy.getUser_email().equals(userDetails.getUsername())) {
			return "redirect:/";
		}
		// 글정보를 모델에 저장
		model.addAttribute("used_buy", used_buy);

		// 수정을 html로 포워딩
		return "used/usedBuyBoardUpdate";
	}

	/**
	 * 중고거래 삽니다 글 수정 수정폼에서 보낸 내용 처리 ok 0324
	 **/
	@PostMapping("usedBuyBoardUpdate")
	public String usedBuyBoardUpdate(
			Used_buy used_buy, @AuthenticationPrincipal UserDetails userDetails,
			@RequestParam(name = "uBuy_id", defaultValue = "0") String uBuy_id) {

		// 전달된 객체에 로그인한 아이디 추가 저장
		used_buy.setUser_email(userDetails.getUsername());

		Used_buy oldBoard = null;

		// 객체를 전달해서 DB에서 글정보 수정
		service.usedBuyBoardUpdate(used_buy);
		log.info("저장이 되냐?: {}", used_buy);

		// 이전에 읽던 글로 리다이렉트
		return "redirect:/used/usedBuyBoardRead?uBuy_id=" + used_buy.getUBuy_id();
	}

	@GetMapping({ "purchase" })
	public String purchase(@AuthenticationPrincipal UserDetails userDetails, String used_id, Model model) {
		Used used = service.findOneUsed(used_id);
		// Used_detail used_detail= service.findOneUseddetail(used_id);
		UserDTO user = service.findUser(userDetails.getUsername());
		// //Auction_bid auction_bid= service.findOneAuctionbid();
			ArrayList<File> fileList = service.fileListByid(used_id);
			
		UserDTO target = service.findUser(used.getUser_email());
		
		// 글쓴이의 다마고치 정보
		Damagochi dama = mservice.selectMyDamaInfoById(used.getUser_email());
		
		model.addAttribute("target", target);

		model.addAttribute("used", used);
		// model.addAttribute("auction_detail", auction_detail);
		model.addAttribute("user", user);
		model.addAttribute("fileList", fileList);
		model.addAttribute("dama", dama);

		return "used/usedPurchase(JPBP).html";
	}

	@PostMapping({ "purchase" })
	public String purchase(
			@AuthenticationPrincipal UserDetails userDetails, Used_detail used_detail, int user_account) {
		String user_email = userDetails.getUsername();
		used_detail.setUser_email(user_email);
		int k = service.purchase(used_detail);

		int i = service.usemoney(user_email, user_account);

		if (k == 0 || i == 0) {
			return "redirect:/used/purchase?used_id=" + used_detail.getUsed_id();
		}		
		
		return "redirect:/mypage/mytransactionlistbuy";
	}

	@GetMapping({ "/imgshow" })
	public String download(HttpServletResponse response, String used_id) {
		ArrayList<File> fileList = service.fileList();

		for (int i = 0; i < fileList.size(); ++i) {
			if (!fileList.get(i).getBoard_status().equals("중고 거래")) {
				fileList.remove(i);
				--i;
			}
		}

		int index = 0;

		for (int i = 0; i < fileList.size(); ++i) {
			if (used_id.equals(fileList.get(i).getBoard_no())) {
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
	public String download(HttpServletResponse response, String used_id, int index) {
		log.info(index + "");

		ArrayList<File> fileList = service.fileListByid(used_id);
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
