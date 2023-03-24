package com.anabada.controller.used;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.domain.File;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.used.UsedService;
import com.anabada.util.FileService;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;

import com.anabada.util.PageNavigator;





@Slf4j
@RequestMapping("/used") 
@Controller
public class UsedController {
	@Autowired
	UsedService service;
	
	//설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입(from application.properites)
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	//페이지 당 글 수
	@Value("${user.board.page}")
	int countPerPage;
	
	//페이지당 이동링크 수
	@Value("${user.board.group}")
	int pagePerGroup;
	
	/**
	 * 중고거래 팝니다 게시판으로 이동(다 보여줌)
	 **/
	@GetMapping("/usedSellBoard")      //@GetMapping("/SellBoard") 리퀘스트 매핑 넣고 바꿔주기
	public String usedSellBoard(
			@RequestParam(name="page", defaultValue="1") int page
			, String type
			, String searchWord
			, Model model) {
		PageNavigator navi = 
			service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord);
		
		ArrayList <Used> usedSellList = service.usedSellBoard(
				navi.getStartRecord(),countPerPage, type, searchWord);
		ArrayList <File> fileList = service.fileList();
		
		ArrayList <Used> recommendList = service.recommendList(
				navi.getStartRecord(),countPerPage, type, searchWord);
		
		model.addAttribute("usedSellList",usedSellList);
		model.addAttribute("navi",navi);
		model.addAttribute("type",type);
		model.addAttribute("searchWord",searchWord);
		model.addAttribute("fileList", fileList);
		
//		log.debug("filelist {}: ", fileList);
		return "used/usedSellBoard(JPB)";
	}
	
	/**
	 * 중고거래 팝니다 게시판으로 이동(한개 보여줌)
	 **/
	@GetMapping("usedSellBoardRead")
	public String usedSellBoardRead(
			@RequestParam(name="used_id",defaultValue="0") String used_id
			,Model model
			) {
		
		Used used_sell = service.usedSellBoardRead(used_id);
//		log.debug("넘어오는 값: {}",used_sell);
		model.addAttribute("used_sell", used_sell);
		
		return "used/usedSellBoardRead(JPBR)";
	}
	
	
	/**
	 * 중고거래 팝니다 글 삭제
	 **/
//	@GetMapping("usedSellBoardDelete")
//	public String usedSellBoardDelete(
//			@RequestParam(name="used_id", defaultValue="0") String used_id
//			, File file
//			, @AuthenticationPrincipal UserDetails user) {
//
//		String email = user.getUsername();
//
//		Used used = service.usedSellBoardRead(used_id);
////		해당번호의 글이 있는지 확인. 없으면 글목록으로
//		if (used == null) return "redirect:list";
////		로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로
//		if (!used.getUser_email().equals(email)) return "redirect:list";
//		
////		첨부된 파일이 있으면 파일삭제
//		if (file.getFile_saved() != null) {
//			FileService.deleteFile(uploadPath + "/" + file.getFile_saved());
//		}
////		실제 글 DB에서 삭제
//		service.usedSellBoardDelete(used);
////		글 목록으로 리다이렉트
//		return "redirect:usedSellBoard(JPB)";
//	}
	
	@GetMapping("usedSellBoardDelete")
	public String usedSellBoardDelete(
	        @RequestParam(name="used_id", defaultValue="0") String used_id,
	        @RequestParam(name="file_saved", defaultValue="") String file_saved
	        //@AuthenticationPrincipal UserDetails user
	        ) {

	    //String email = user.getUsername();

	    Used used = service.usedSellBoardRead(used_id);
	    // 해당번호의 글이 있는지 확인. 없으면 글목록으로
	    if (used == null) return "redirect:list";
	    // 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로
	    //if (!used.getUser_email().equals(email)) return "redirect:list";

	    // 첨부된 파일이 있으면 파일삭제
	    if (!file_saved.isEmpty()) {
	        FileService.deleteFile(uploadPath + "/" + file_saved);
	    }
	    // 실제 글 DB에서 삭제
	    service.usedSellBoardDelete(used);
	    // 글 목록으로 리다이렉트
	    return "redirect:/";
	   
	}
	
	/**
	 * 중고거래 팝니다 글쓰기 게시판으로 이동
	 **/
	@GetMapping("/usedSellWrite")
	public String usedSellWrite() {
		return"used/usedSellWrite(JPBW)";
	}
	
	/**
	 * 중고거래 팝니다 글쓴거 DB에 저장
	 **/
	@PostMapping("/usedSellWrite")
	public String usedSellWrite(
			 Used used
			, File file
			, ArrayList<MultipartFile> upload) {	
		
		log.debug("받은 데이터: {}", used);
		
		String used_id = service.usedSellWrite(used);
		
		if(upload.get(0).isEmpty()) {
	         log.debug("이미지 X");
	   
	         service.usedSellWrite(used);
	         return "redirect:/";
	    }
		
		for(int i = 0; i < upload.size(); ++i) {
	         String filename = FileService.saveFile(upload.get(i), uploadPath);
	         file.setFile_origin(upload.get(i).getOriginalFilename());
	         file.setFile_saved(filename);
	         
	         file.setBoard_no(used_id);
	         log.debug("{}:", used_id);
	         file.setBoard_status("중고 거래");
	         
	         service.insertFile(file);
	         
	      }         

		//로그인한 아이디 읽어서 Used객체에 추가
		//used.setUser_email(user.getUsername());
		
		//db에 저장
		
		
		return "redirect:/";
		
		/*
		 * used00001
		 * substring(0,3)
		 * String str = USED
		 * string
		 * 
		 * func_file_name(str)*/

	
	@PostMapping("/usedSellWrite")
		public String usedSellWrite(
			@ModelAttribute Used used,
			@RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne) {
			String used_id = service.usedSellWrite(used);

			if(upload.isEmpty() || upload.get(0).isEmpty()) {
			    log.debug("이미지 X");
			    return "redirect:/";
			}
	
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
		
			for(int i = 0; i < upload.size(); ++i) {
			    MultipartFile file = upload.get(i);
			    String filename = FileService.saveFile(file, uploadPath);
			    File savedFile = new File();
			    savedFile.setFile_origin(file.getOriginalFilename());
			    savedFile.setFile_saved(filename);
			    savedFile.setBoard_no(used_id);
			    savedFile.setBoard_status("중고 거래");
			    service.insertFile(savedFile);
			}
			
			return "redirect:/";
			}

	/**
	 * 중고거래 팝니다 글 수정(사용자 측)
	 **/
	@GetMapping("usedSellBoardUpdate")
	public String usedSellBoardUpdate(
			Model model
			,@RequestParam(name="used_id", defaultValue="0") String used_id
			//,@AuthenticationPrincipal UserDetails user
			) {
		
		//전달된 아이디의 글정보 읽기
		Used used = service.usedSellBoardRead(used_id);
		
		//로그인한 사용자의 아이디를 읽음
		//String id = user.getUsername();
		
		//본인 글인지 확인, 아니면 글목록으로 이동
		//if (!used.getUser_email().equals(user.getUsername())) return "redirect:list");
		
		//글정보를 모델에 저장
		model.addAttribute("used",used);
		
		//수정을 html로 포워딩
		return "used/usedSellBoardUpdate.html";
	}
	
	//수정폼에서 보낸 내용 처리
	@PostMapping("usedSellBoardUpdate")
	public String usedSellBoardUpdate(
	    @ModelAttribute Used used,
	    @RequestParam(name = "upload") ArrayList<MultipartFile> upload,
	    @RequestParam(name = "uploadOne") MultipartFile uploadOne
	) {
	    String used_id = service.usedSellBoardUpdate(used);
	    
	    if (used_id == null) {
	        return "redirect:/";
	    }
	    
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
	 * 중고거래 삽니다 글쓰기 게시판으로 이동
	 **/
	@GetMapping("/usedBuyWrite")
	public String usedBuyWrite() {
		return"used/usedBuyWrite(JSBW)";
	}
	
	/**
	 * 중고거래 삽니다 글목록 게시판으로 이동
	 **/
	
	@PostMapping("/usedBuyWrite")
	public String usedBuyWrite(Used_buy used_buy) {
		service.usedBuyWrite(used_buy);
		return "redirect:/";	
	}	
	
	/**
	 * 중고거래 삽니다 글 읽기 게시판으로 이동
	 **/
	@GetMapping("/usedBuyBoard")
	public String usedBuyBoard(Model model) {
		ArrayList<Used_buy>bboardlist=service.usedBuyBoard();
		//log.debug("db에서 넘어오나?:{}",bboardlist);
		model.addAttribute("bboardlist",bboardlist);
		return "used/usedBuyBoard(JSB)";
	}
	
	/**
	 * 중고거래 삽니다 글 하나 읽기 게시판으로 이동
	 **/
	@GetMapping("usedBuyBoardRead")
	public String usedBuyBoardRead(
			@RequestParam(name="uBuy_id",defaultValue="0") String uBuy_id
			,Model model
			) {
		
		Used_buy used_buy = service.usedBuyBoardRead(uBuy_id);
//		log.debug("넘어오는 값: {}",used_buy);
		model.addAttribute("used_buy", used_buy);
		
		return "used/usedBuyBoardRead(JSBR)";
	}
	
	/**
	 * 중고거래 삽니다 글 삭제
	 **/
	
	@GetMapping("usedBuyBoardDelete")
	public String usedBuyBoardDelete(
	        @RequestParam(name="uBuy_id", defaultValue="0") String uBuy_id
//	      ,@AuthenticationPrincipal UserDetails user  //나중에 로그인 설정하고 주석 해제   
	        ) {
		log.debug("오냐고  : {}", uBuy_id);
	    Used_buy used_buy = service.usedBuyBoardRead(uBuy_id);
	    if (used_buy == null) return "redirect:list";
	    // 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로 나중에 로그인 설정하고 주석 해제   
	//  if (!used_buy.getUser_email().equals(user.getUsername())) return "redirect:list");

	    int result = service.usedBuyBoardDelete(used_buy);

	    return "redirect:/";
	}
	
	/**
	 * 중고거래 삽니다 글 수정 (여기까진 ok)
	 **/
	@GetMapping("usedBuyBoardUpdate")
	public String update(
			Model model
			,@RequestParam(name="uBuy_id", defaultValue="0") String uBuy_id
			//,@AuthenticationPrincipal UserDetails user
			) {
		
		//전달된 아이디의 글정보 읽기
		Used_buy used_buy = service.usedBuyBoardRead(uBuy_id);
		log.info(used_buy+"");
		//로그인한 사용자의 아이디를 읽음
		//String id = user.getUsername();
		
		//본인 글인지 확인, 아니면 글목록으로 이동
		//if (!used_buy.getUser_email().equals(user.getUsername())) return "redirect:list");
		
		//글정보를 모델에 저장
		model.addAttribute("used_buy",used_buy);
		
		//수정을 html로 포워딩
		return "used/usedBuyBoardUpdate.html";
	}
	
	//수정폼에서 보낸 내용 처리
		@PostMapping("usedBuyBoardUpdate")
		public String usedBuyBoardUpdate(
			Used_buy used_buy
			//,@AuthenticationPrincipal UserDetails user
			,@RequestParam(name="uBuy_id", defaultValue="0") String uBuy_id
			) {
			
			//전달된 객체에 로그인한 아이디 추가 저장
			//used_buy.setUser_email(user.getUsername());

			Used_buy oldBoard = null;
				
			//객체를 전달해서 DB에서 글정보 수정
			service.usedBuyBoardUpdate(used_buy);
			log.info("저장이 되냐?: {}",used_buy);
		
			//이전에 읽던 글로 리다이렉트
			return "redirect:/usedBuyBoardRead?uBuy_id="+used_buy.getUBuy_id();
		}
	


	
	@GetMapping({"purchase"})
	public String purchase(@AuthenticationPrincipal UserDetails user
			,String used_id, Model model) {
		Used used = service.findOneUsed(used_id);
//		Used_detail used_detail= service.findOneUseddetail(used_id);
		String user_email = user.getUsername();
		UserDTO userone = service.findUser(user_email);
//		//Auction_bid auction_bid= service.findOneAuctionbid();
		
		model.addAttribute("used", used);
//		model.addAttribute("auction_detail", auction_detail);
		model.addAttribute("user", userone);

		return "used/usedPurchase(JPBP).html";
	}
	
	@PostMapping({"purchase"})
	public String purchase(
			@AuthenticationPrincipal UserDetails user
			, Used_detail used_detail
			, int user_account
			) {
		String user_email = user.getUsername();
		used_detail.setUser_email(user_email);
		int k = service.purchase(used_detail);
		
		int i = service.usemoney(user_email, user_account);		
		
		if(k == 0 || i ==0) {
			return "redirect:/used/purchase?used_id=" + used_detail.getUsed_id();
		}		
		
		return "used/usedThanks.html";
	}
}
