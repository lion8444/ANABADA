package com.anabada.controller.auction;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

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
import com.anabada.domain.Auction_bid;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.File;
import com.anabada.domain.Used;
import com.anabada.domain.UserDTO;
import com.anabada.service.auction.AuctionService;
import com.anabada.util.FileService;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"auction"})
@Controller
public class AuctionController {
	
	@Autowired
	private AuctionService service;
	
	//설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입(from application.properites)
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	//페이지 당 글 수
	@Value("${user.board.page}")
	int countPerPage;
	
	//페이지당 이동링크 수
	@Value("${user.board.group}")
	int pagePerGroup;
	
	@GetMapping({"purchase"})
	public String purchase(String auction_id, Model model) {
		Auction auction = service.findOneAuction(auction_id);
		Auction_detail auction_detail= service.findOneAuctiondetail(auction_id);
		String user_email = "anabada@gmail.com";
		UserDTO user = service.findUser(user_email);
		//Auction_bid auction_bid= service.findOneAuctionbid();
		
		model.addAttribute("auction", auction);
		model.addAttribute("auction_detail", auction_detail);
		model.addAttribute("user", user);

		return "auction/auctionPurchase(GBP).html";
	}
	
	@PostMapping({"purchase"})
	public String purchase(
			@AuthenticationPrincipal UserDetails user
			, Auction_detail auction_detail
			, int user_account
			) {
		String user_email = user.getUsername();
		auction_detail.setUser_email(user_email);
		int k = service.bid(auction_detail);
		
		int i = service.usemoney(user_email, user_account);		
		
		if(k == 0 || i ==0) {
			return "redirect:/auction/purchase?auction_id=" + auction_detail.getAuction_id();
		}
		
		
		return "auction/auctionThanks.html";
	}
	
	/**
	 * 옥션 게시판으로 이동(다 보여줌) 
	 **/
	@GetMapping("/auctionBoard")       
	public String auctionBoard(
			@RequestParam(name="page", defaultValue="1") int page
			, String type
			, String searchWord
			, Model model) {
		PageNavigator navi = 
			service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);
		
		ArrayList <Auction> auctionList = service.auctionBoard(
				navi.getStartRecord(),countPerPage, type, searchWord);
		
		model.addAttribute("auctionList",auctionList);
		model.addAttribute("navi",navi);
		model.addAttribute("type",type);
		model.addAttribute("searchWord",searchWord);
		return "auction/auctionBoard(GB)";
	}
	
	/**
	 * 렌탈 상세 게시판으로 이동
	 * 조회수
	 **/
	@GetMapping("auctionBoardRead")
	public String auctionBoardRead(
			@RequestParam(name="auction_id",defaultValue="0") String auction_id
			,Model model
			,@RequestParam(name="page", defaultValue="1") int page
			) {
		PageNavigator navi = 
				service.getPageNavigator(pagePerGroup, countPerPage, page, null, null);
		ArrayList <Auction> auctionList = service.auctionBoard(
				navi.getStartRecord(),countPerPage, null, null);
		model.addAttribute("auctionList",auctionList);
		
		Auction auction_sell = service.auctionBoardRead(auction_id);
		ArrayList <File> fileList = service.fileListByid(auction_id);
		
		model.addAttribute("auction_sell", auction_sell);
		model.addAttribute("fileList", fileList);
		return "auction/auctionBoardRead(GBR)";
	}
	
	/**
	 * 옥션 글 삭제
	 **/
	
	@GetMapping("auctionBoardDelete") 
	public String auctionBoardDelete(
	        @RequestParam(name="auction_id", defaultValue="0") String auction_id
	        ,@RequestParam(name="file_saved", defaultValue="") String file_saved
	        ,@AuthenticationPrincipal UserDetails user
	        ) {

		String id = user.getUsername();

	    Auction auction = service.auctionBoardRead(auction_id);
	    // 해당번호의 글이 있는지 확인. 없으면 글목록으로
	    if (auction == null) return "redirect:/";
	    // 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로
	    if (!auction.getUser_email().equals(user.getUsername())) return "redirect:/";

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
	public String auctionWrite() {
		return"auction/auctionWrite(GBW)";
	}
	
	/**
	 * 옥션 글쓰기 처리
	 **/
	@PostMapping("/auctionWrite")
		public String auctionWrite(
			@AuthenticationPrincipal UserDetails user,
			@ModelAttribute Auction auction,
			@RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne) {
			
		
			//로그인한 아이디 읽어서 board객체에 추가
			auction.setUser_email(user.getUsername());
				
			String auction_id = service.auctionWrite(auction);
			
			if(uploadOne.isEmpty()) {
			    log.debug("이미지 X");
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
			for(int i = 0; i < upload.size(); ++i) {
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
			Model model
			,@RequestParam(name="auction_id", defaultValue="0") String auction_id
			,@AuthenticationPrincipal UserDetails user
			) {
		
		//전달된 아이디의 글정보 읽기
		Auction auction = service.auctionBoardRead(auction_id);
		
		//본인 글인지 확인, 아니면 글목록으로 이동
		if (!auction.getUser_email().equals(user.getUsername())) return "redirect:/";
		
		//글정보를 모델에 저장
		model.addAttribute("auction",auction);
		
		//수정을 html로 포워딩
		return "auction/auctionBoardUpdate.html";
	}
	
	//수정폼에서 보낸 내용 처리
	@PostMapping("auctionBoardUpdate")
	public String auctionBoardUpdate(
	    @ModelAttribute Auction auction
	    ,@RequestParam(name = "upload") ArrayList<MultipartFile> upload
	    ,@RequestParam(name = "uploadOne") MultipartFile uploadOne
	    ,@AuthenticationPrincipal UserDetails user
	) {
	    String auction_id = service.auctionBoardUpdate(auction);
	    
	  //로그인한 사용자의 아이디를 읽음
	  	String id = user.getUsername();
	    
	    if (auction_id == null) {
	        return "redirect:/";
	    }
	    //처음에 해당 글에 file있는지 여부 확인해서 isempty 그래서 있으면 
//	    걍 싹 다 지우고 다시 저장하는데 순서가 맨 처음에 uploadOne 을 넣고 그다음에
//	    배열 돌리기
	    
	    // 추가된 사진 처리
	    if (!uploadOne.isEmpty()) {
			ArrayList<File> files = service.fileListAll(auction_id);
			
			if(!files.isEmpty()) {
				//service.deleteFile
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
	
	@GetMapping({"/imgshow"})
	public String download(HttpServletResponse response, String auction_id) {
		ArrayList <File> fileList = service.fileList();

		for(int i=0 ; i < fileList.size(); ++i) {
			if(!fileList.get(i).getBoard_status().equals("옥션 거래")) {
				fileList.remove(i);
				--i;
				}
		}
		
		int index = 0;
		
		for (int i = 0; i < fileList.size(); ++i) {
			if (auction_id.equals(fileList.get(i).getBoard_no())){
				index = i;
			}
		}
		
		String file = uploadPath + "/" + fileList.get(index).getFile_saved();
		
		FileInputStream in = null;		
		ServletOutputStream out = null;

	try {	
			response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileList.get(index).getFile_origin(), "UTF-8"));
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
	
	@GetMapping({"/imgshowone"})
	public String download(HttpServletResponse response, String auction_id, int index) {
		log.info(index+"");
		
		ArrayList <File> fileList = service.fileListByid(auction_id);
		String file = uploadPath + "/" + fileList.get(index).getFile_saved();

		FileInputStream in = null;		
		ServletOutputStream out = null;

	try {	
			response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileList.get(index).getFile_origin(), "UTF-8"));
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
}
