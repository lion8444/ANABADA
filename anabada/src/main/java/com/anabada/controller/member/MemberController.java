package com.anabada.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.anabada.domain.UserDTO;
import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	
	@Autowired
	LoginService service;
	
	@GetMapping("/MyPage")
	public String mypage(
			@AuthenticationPrincipal UserDetails user
			, Model m) {
		
		UserDTO usr = service.findUser(user.getUsername());
		
		m.addAttribute("user", usr);
		
		return "mypage/mypage";
	}

}
