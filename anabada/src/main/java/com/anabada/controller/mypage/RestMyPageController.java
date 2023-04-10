package com.anabada.controller.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.domain.Used;
import com.anabada.domain.UsedAndFile;
import com.anabada.service.mypage.MyPageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 관련 ajax 컨트롤러
 * @author 박용우
 * 230321
 */
@Slf4j
@RequestMapping({"mypage"})
@RestController
public class RestMyPageController {
	
	@Autowired
	MyPageService service;

	/**
	 * 회원 정보 수정 페이지 실시간 현재 비밀번호 확인
	 * @param user_email 현재 유저의 이메일
	 * @param nowPw	유저가 입력하는 현재 비밀번호
	 * @return
	 */
	@PostMapping("/modify_nowPw")
	public int nowPw(			
			String user_email
			, String nowPw) {
		
		log.debug("이메일: {}, 비번: {}", user_email, nowPw);
		
		int result = service.checkIdAndPw(user_email, nowPw);
		
		return result;
	}
	
//	@PostMapping("/mytransactionlistall")
//	public List<UsedAndFile> myTransactionListAll(
//			@AuthenticationPrincipal UserDetails user
//			, String user_email
//			, Model model) {
//		
//		List<UsedAndFile> list = service.selectUsedListAllById(user_email);
//		
////		model.addAttribute("usedList", list);
//				
////		list = service.selectUsedListAllById(user_email);
//		
//		return list;
//	}
	
	@PostMapping("charge")
	public int charge(@AuthenticationPrincipal UserDetails userDetails, String money) {
			if(isInteger(money)) {
				String email = userDetails.getUsername();
				int result = service.addmoney(email, money);
				return result;
			}
		
		return 0;
	}

	private boolean isInteger(String money) {
	    try {
	        Integer.parseInt(money);
	        return true;
	      } catch (NumberFormatException ex) {
	        return false;
	      }
	    }
}
