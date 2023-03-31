package com.anabada.controller.csForm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.domain.AuctionAndFile;
import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Rental;
import com.anabada.domain.RentalAndFile;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.Report;
import com.anabada.domain.Review;
import com.anabada.domain.Used;
import com.anabada.domain.UsedAndFile;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.csForm.CSFormService;
import com.anabada.util.FileService;

import lombok.extern.slf4j.Slf4j;

/**
 * 신고하기, 후기, 문의하기 관련 컨트롤러
 * @author 박용우
 *	0314
 */
@Slf4j
@RequestMapping({"csform"})
@Controller
public class CSController {
	
	@Autowired
	CSFormService service;
	
	// 설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	/**
	 * 신고하기 폼으로 이동하기
	 * @return 신고하기 작성 폼
	 */
	@GetMapping("/report")
	public String report(
			String used_id
			, String rental_id
			, HttpServletRequest hsr
			 , Model model) {
		
		log.debug("신고페이지 진입");
		
		// http://localhost 까지만 가져오기
		String aa = hsr.getRequestURL().toString().replace(hsr.getRequestURI(),"");
		
		String url = "";
			
		if(used_id != null) {
			url = aa + "/usedSellBoardRead?used_id=" +  used_id;
			
			Used us = service.selectUsedByID(used_id);
			
			model.addAttribute("url", url);
			model.addAttribute("us", us);
		}
		
		
		if(rental_id != null) {
			url = aa + "/rentalSellBoardRead?used_id=" +  rental_id;
			
			Rental rt = service.selectRentalById(rental_id);

			model.addAttribute("url", url);
			model.addAttribute("rt", rt);

		}
		
					
		return "csForm/reportForm";
	}
	
	/**
	 * 신고하기 처리하기
	 * @param report : 신고하기로 올라온 데이터
	 * @return 리다이렉트
	 */
	@PostMapping("/report")
	public String report(
			@AuthenticationPrincipal UserDetails user
			, Report report
			, File file
			, ArrayList<MultipartFile> upload) {
		log.debug("신고하기로 올라온 데이터 : {}", report);
		
		// id가 다를 시 처리 X
//		if(!report.getUser_email().equals(user.getUsername())) {
//			log.debug("신고하기 - ID일치 X");
//			return "redirect:/";
//		}
				
		// 첨부파일 없을 때 - 신고하기만 처리
		if(upload.get(0).isEmpty()) {
			
			log.debug("신고하기 - 첨부파일 X");
			
			service.insertReport(report);
			
			return "redirect:/mypage/mypage";
		}
								
		// 첨부파일 있을 때 - 파일 저장 후 신고하기 처리
		for(int i = 0; i < upload.size(); ++i) {
			
			String filename = FileService.saveFile(upload.get(i), uploadPath);
			
			file.setFile_origin(upload.get(i).getOriginalFilename());
			
			file.setFile_saved(filename);
			
			service.insertFile(file);
		}			
			
		service.insertReport(report);
		
		log.debug("첨부파일과 신고하기 처리");
			
		return "redirect:/mypage/mypage";
	}
	
	/**
	 * 마이페이지 - 문의하기 폼으로 포워딩
	 * @return 문의하기 작성 폼
	 */
	@GetMapping("/inquiry")
	public String inquiry(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
		
		log.debug("문의하기 작성 폼 이동");
		
		UserDTO userdto = service.selectUserById(user.getUsername());
		
		model.addAttribute("user", userdto);
		
		return "csForm/my_inquireForm";
	}
	
	/**
	 * 문의하기 처리
	 * @param inquiry : 문의하기로 올라온 데이터
	 * @return 리다이렉트
	 */
	@PostMapping("/inquiry")
	public String inquiry(
			@AuthenticationPrincipal UserDetails user
			, Inquiry inquiry
			, File file
			, ArrayList<MultipartFile> upload) {
		
		log.debug("문의하기로 올라온 데이터 : {}", inquiry);
		
		// ID가 로그인 한 ID와 다르면 처리X
		if(!inquiry.getUser_email().equals(user.getUsername())) {
			log.debug("문의하기 - ID가 다름");
			
			return "redirect:/";
		}
						
		// 첨부파일 없을 때 - 문의하기만 처리
		if(upload.get(0).isEmpty()) {
			log.debug("문의하기 - 첨부파일 X");
			
			service.insertInquiry(inquiry);
			
			return "redirect:/mypage/mypage";
		}
		
		// board_status가 문의하기가 아니면 처리 X
		if(!upload.get(0).isEmpty() && !file.getBoard_status().equals("문의하기")) {
			
			log.debug("문의하기로 파일이 들어오지 않음");
			
			return "redirect:/";
		}
		
		// 첨부파일 있을 때 - 파일 저장 후 문의하기 처리
		for(int i = 0; i < upload.size(); ++i) {
			String filename = FileService.saveFile(upload.get(i), uploadPath);
			
			file.setFile_origin(upload.get(i).getOriginalFilename());
			
			file.setFile_saved(filename);
			
			service.insertFile(file);
		}
		
		service.insertInquiry(inquiry);
		
		log.debug("첨부파일과 문의하기 처리");
		
		return "redirect:/mypage/mypage";
	}
	
	// ★★리뷰 하기★★ - 리뷰 폼 포워딩
	@GetMapping("/review")
	public String review(
			@AuthenticationPrincipal UserDetails user
			, String used_id
			, String rental_id
			, Model model) {
		
		log.debug("user : {}", user.getUsername());
		log.debug("usedID : {}", used_id);
		log.debug("rental_id : {}", rental_id);
		
		if(used_id == null && rental_id == null) {
			log.debug("올바른 접근 X");
			return "redirect:/";
		}	
		
		// used_id를 받아 올 때
		if(used_id != null) {
			Used_detail ud = service.selectUsedFinished(used_id);
			
			log.debug("UD: {}", ud);
						
			if(ud == null) {
				log.debug("중고 거래 완료 된 글이 아님");
				return "redirect:/";
			}
			
			if(!ud.getUser_email().equals(user.getUsername())) {
				log.debug("중고 거래의 구매자가 아님");
				return "redirect:/";
			}
			
			model.addAttribute("used_detail", ud); 
		}
		
		// rental_id를 받아올 때
		if(rental_id != null) {
			Rental_detail rd = service.selectRentalFinished(rental_id);
			
			Rental rt = service.selectRentalById(rental_id);
			
			log.debug("rental_detail : {}", rd);
			log.debug("rental : {}", rt);
			
			if(rd == null) {
				log.debug("렌탈 완료 된 글이 아님");
				return "redirect:/";
			}
			
			if(!rd.getUser_email().equals(user.getUsername()) && !rt.getUser_email().equals(user.getUsername())) {
				log.debug("렌탈자 or 렌탈러 ID가 아님");			
				return "redirect:/";
			}
			
			model.addAttribute("rental_detail", rd);
			model.addAttribute("rental", rt);
		}
			
		return "csForm/reviewForm";
	}
	
	// ★★리뷰 하기★★ - 리뷰 처리
	@PostMapping("/review")
	public String review(
			@AuthenticationPrincipal UserDetails user
			, String used_id
			, String rental_id
			, Review review) {
		
		log.debug("user : {}", user.getUsername());
		log.debug("user_email : {}", user.getUsername());
		log.debug("usedID : {}", used_id);
		log.debug("rental_id : {}", rental_id);
		log.debug("리뷰: {}", review);

		
		if(used_id != null) {
			Used_detail ud = service.selectUsedFinished(used_id);
			Used us = service.selectUsedByID(used_id);
			
			if(ud == null) {
				log.debug("잘못된 글");
				return "redirect:/";
			}
			
			if(us == null) {
				log.debug("잘못된 글");
				return "redirect:/";
			}
			
			if(!ud.getUser_email().equals(user.getUsername())) {
				log.debug("아이디가 맞지 않음");
				return "redirect:/";
			}
			
			review.setBoard_no(used_id);
			review.setBoard_status("중고 거래");
			review.setReview_person(us.getUser_email());
			
			service.insertReview(review);
		}
		
		if(rental_id != null) {
			Rental_detail rd = service.selectRentalFinished(rental_id);
			Rental rt = service.selectRentalById(rental_id);
			
			log.debug("Rental_detail : {}", rd);
			log.debug("Rental : {} ", rt);
//			log.debug("구매자 {}", rd.getUser_email());
//			log.debug("판마자 {}", rt.getUser_email());
			
			if(rd == null || rt == null) {
				log.debug("잘못된 글");
				return "redirect:/";
			}
			
			if(!review.getUser_email().equals(user.getUsername())) {				
				log.debug("후기작성 - ID일치 X");				
				return "redirect:/";
			}
			
			// 아이디가 렌탈 구매자인 경우 렌탈 판매자를 리뷰 대상자로
			if(review.getUser_email().equals(rd.getUser_email())) {
				review.setReview_person(rt.getUser_email());
			}
			
			// 아이다가 렌탈 판매자인 경우 렌탈 구매자를 리뷰 대상자로
			if(review.getUser_email().equals(rt.getUser_email())) {
				review.setReview_person(rd.getUser_email());
			}
			
			review.setBoard_no(rental_id);
			review.setBoard_status("렌탈 거래");
			
			service.insertReview(review);
		}
		
		return "redirect:/mypage/mypage";
	}
		

	
}
