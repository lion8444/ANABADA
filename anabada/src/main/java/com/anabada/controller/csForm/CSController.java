package com.anabada.controller.csForm;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.Report;
import com.anabada.domain.Review;
import com.anabada.domain.Used;
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
			, String auction_id
			, HttpServletRequest hsr
			 , Model model) {
		
		log.debug("신고페이지 진입");
		
		// http://localhost 까지만 가져오기
		String aa = hsr.getRequestURL().toString().replace(hsr.getRequestURI(),"");
		
		String url = "";
				
		if(used_id != null) {
					
			url = aa + "/usedSellBoardRead?used_id=" +  used_id;
			
			model.addAttribute("url", url);
		}
		
		if(rental_id != null) {
			
			url = aa + "/rentalSellBoardRead?used_id=" +  rental_id;
			
			model.addAttribute("url", url);
		}
		
		if(auction_id != null) {
			
			url = aa + "/auctionSellBoardRead?used_id=" +  auction_id;
			
			model.addAttribute("url", url);
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
			
			return "redirect:/mypage";
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
			
		return "redirect:/mypage";
	}
	
	/**
	 * 마이페이지 - 문의하기 폼으로 포워딩
	 * @return 문의하기 작성 폼
	 */
	@GetMapping("/inquiry")
	public String inquiry(
			String user_email
			, Model model) {
		
		log.debug("문의하기 작성 폼 이동");
		
		user_email = "anabada@gmail.com";
		
		UserDTO userdto = service.selectUserById(user_email);
		
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
			
			return "redirect:/";
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
		
		return "redirect:/mypage";
	}
	
	/**
	 * 후기작성 폼으로 포워딩_중고 거래의 경우
	 * @return 후기작성 폼으로 이동
	 */
	@GetMapping("/review_used")
	public String review_used(
			@AuthenticationPrincipal UserDetails user
			, String used_id
			, Model model) {
		
		// 리뷰할 글이 없거나 완료 상태가 아닌 글인지 체크 
		Used_detail usedDetail = service.selectUsedDetailById(used_id);
		
		if(usedDetail == null) {
			log.debug("해당 글 X or 완료 상태 X");
			
			return "redirect:/";
		}
		
		// 구매자 이메일과 현재 로그인한 이메일이 같지 않을 경우 처리 X
		if(!usedDetail.getUser_email().equals(user.getUsername())) {
			log.debug("구매자 ID가 아님");
			
			return "redirect:/";
		}
		
		model.addAttribute("used_Detail", usedDetail);
			
		return "csForm/reviewForm?used_id=" + usedDetail.getUsed_id();
	}
	
	/**
	 * 후기작성 폼으로 포워딩_렌탈 거래의 경우
	 * @return 후기작성 폼으로 이동
	 */
	@GetMapping("/review_rental")
	public String review_rental(
			@AuthenticationPrincipal UserDetails user
			, String rental_id
			, Model model) {
		
		// 리뷰할 글이 없거나 완료 상태가 아닌 글인지 체크 
		Rental_detail rentalDetail = service.selectRentalDetailById(rental_id);
		Rental rental = service.selectRentalById(rental_id);
		
		if(rentalDetail == null || rental == null) {
			log.debug("해당 글 X or 완료 상태 X");
			
			return "redirect:/";
		}
		
		// 빌린사람 or 빌려준사람 이메일과 현재 로그인한 이메일이 같지 않을 경우 처리 X 
		if(!rentalDetail.getUser_email().equals(user.getUsername()) || !rental.getUser_email().equals(user.getUsername())) {
			log.debug("렌탈자 or 렌탈러 ID가 아님");
			
			return "redirect:/";
		}
		
		model.addAttribute("rental_Detail", rentalDetail);
		model.addAttribute("rental", rental);
			
		return "csForm/reviewForm?rental_id=" + rentalDetail.getRDetail_id();
	}
	
	/**
	 * 후기작성 처리 - 렌탈
	 * @param rental 후기작성 폼에서 올라오는 데이터
	 * @return 리다이렉트
	 */
	@PostMapping("/review_rental")
	public String review_rental(
			@AuthenticationPrincipal UserDetails user
			, String rental_id
			, Review review) {
		
		log.debug("후기로 올라온 데이터 :{}", review);
		
		Rental_detail rentalDetail = service.selectRentalDetailById(rental_id);
		Rental rental = service.selectRentalById(rental_id);
		
		// 렌탈 글이 없거나 렌탈 완료 상태가 아닐 시 처리 X
		if(rentalDetail == null || rental == null) {
			log.debug("해당 글 X or 완료 상태 X");
			
			return "redirect:/";
		}
			
		// 리뷰작성자 id와 로그인 한 id가 다를 시 처리 X or 리뷰대상자 id와 로그인 한 id가 같지 않을 시 처리 X 
		if(!review.getUser_email().equals(user.getUsername()) || !review.getReview_person().equals(user.getUsername())) {
			
			log.debug("후기작성 - ID일치 X");
			
			return "redirect:/";
		}
		
		if(!review.getUser_email().equals(rental.getUser_email()))
		
		review.setBoard_no(rentalDetail.getRDetail_id());		// 리뷰_게시글 번호에 렌탈 아이디 넣기 	 
		review.setBoard_status("렌탈 거래");					// 리뷰_게시글 종류에 렌탈거래 넣기
		
		// 로그인한 사용자와 렌탈 판매자의 id가 같으면 렌탈 구매자를 리뷰 대상자로 설정
		if(user.getUsername().equals(rental.getUser_email())) {	
			review.setReview_person(rentalDetail.getUser_email());;
		} 
		
		// 로그인한 사용자와 렌탈 구매자의 id가 같으면 렌탈 판매자를 리뷰 대상자로 설정 
		if(user.getUsername().equals(rentalDetail.getUser_email())) {
			review.setReview_person(rental.getUser_email());
		}
			
		service.insertReview(review);
		
		return "redirect:/mypage";
	}
	
	/**
	 * 후기작성 처리 - 중고
	 * @param review 후기작성 폼에서 올라오는 데이터
	 * @return 리다이렉트
	 */
	@PostMapping("/review_used")
	public String review_used(
			@AuthenticationPrincipal UserDetails user
			, String used_id
			, Review review) {
		
		// 리뷰할 글이 없거나 완료 상태가 아닌 글인지 체크 
		Used_detail usedDetail = service.selectUsedDetailById(used_id);
		Used used = service.selectUsedByID(used_id);
		
		if(usedDetail == null || used == null) {
			log.debug("해당 글 X or 완료 상태 X");
			
			return "redirect:/";
		}
			
		// 리뷰작성자 id와 로그인 한 id가 다를 시 처리 X or 리뷰대상자 id와 글 작성자 id가 같지 않을 시 처리 X 
		if(!review.getUser_email().equals(user.getUsername()) || !review.getReview_person().equals(used.getUser_email())) {
			
			log.debug("후기작성 - ID일치 X");
			
			return "redirect:/";
		}
		
		review.setBoard_no(usedDetail.getUDetail_id());	// 리뷰_게시글 번호에 거래 아이디 넣기 
		review.setBoard_status("중고거래");				// 리뷰_게시글 종류에 중고거래 넣기
		review.setReview_person(used.getUser_email());	// 리뷰 대상자에 글 작성자 이름 넣기
		
		log.debug("후기로 올라온 데이터 :{}", review);
		
		service.insertReview(review);
		
		return "redirect:/mypage";
	}
	
}
