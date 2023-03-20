package com.anabada.controller.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabada.domain.CharacterDTO;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.UserDTO;
import com.anabada.service.mypage.MyPageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 컨트롤러
 * @author 박용우
 * 230317
 */
@Slf4j
@RequestMapping({"mypage"})
@Controller
public class MyPageController {
	
	@Autowired
	MyPageService service;

	/**
	 * 마이페이지 포워딩
	 * @return 마이페이지
	 */
	@GetMapping("/mypage")
	public String myPage(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
//		if(!user_email.equals(user.getUsername())) {
//			return "redirect:/";
//		}
		
		user_email = user.getUsername();
		
		// 현재 로그인한 유저의 개인 정보
		UserDTO userDTO = service.selectUserById(user_email);
		
		CharacterDTO characterDTO = service.selectUserDama(user_email);
		
		log.debug("정보 : {}", userDTO);
		log.debug("다마정보 : {}", characterDTO);
		
		model.addAttribute("user", userDTO);
		model.addAttribute("dama", characterDTO);
		
		return "mypage/mypage";
	}
	
	/**
	 * 나의 문의 내역 포워딩
	 * @param user 유저의 이메일
	 * @return 나의 문의 내역페이지
	 */
	@GetMapping("/myinquirelist")
	public String myInquireList(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		log.debug("나의 문의 내역 페이지 진입");
		
		// 로그인한 아이디와 user_email이 같이 않으면 메인으로 이동
//		if(!user_email.equals(user.getUsername())) {
//			return "redirect:/";
//		}
		
//		log.debug("유저이메일:{}", user_email);
		
		String email = user.getUsername();
		
		UserDTO userdto = service.selectUserById(email);
		
		List<Inquiry> inquiry = service.selectInquiryList(userdto.getUser_email());
		
		model.addAttribute("user", userdto);
		model.addAttribute("inquiry", inquiry);
		
		return "mypage/my_inquireList";
	}
	
	/**
	 * 마이페이지 -> 회원정보 변경 -> 회원정보 확인 페이지 이동(이메일, 비밀번호 한번 더 입력)
	 * @param user
	 * @param user_email
	 * @return
	 */
	@GetMapping("/checkPw")
	public String myCheckPw(
			@AuthenticationPrincipal UserDetails user
			, String user_email) {
		
		// 로그인한 아이디와 user_email이 같이 않으면 메인으로 이동
//		if(!user_email.equals(user.getUsername())) {
//			return "redirect:/";
//		}
		
		return "/mypage/my_checkPwForm";
	}
	
	@GetMapping("/myreportlist")
	public String myreportlist(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
//		if(!user_email.equals(user.getUsername())) {
//			log.debug("아이디 일치 X");
//			return "redirect:/";
//		}
		
		user_email = "anabada@gmail.com";
		
		List<Report> list = service.selectReportList(user_email);
		
		model.addAttribute("report", list);
		
		return "mypage/my_reportList";
	}
	
}
