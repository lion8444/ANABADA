package com.anabada.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.anabada.domain.UserDTO;
import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	LoginService service;

	@GetMapping("/mypage")
	public String mypage(
			@AuthenticationPrincipal UserDetails user, Model m) {

		UserDTO usr = service.findUser(user.getUsername());

		m.addAttribute("user", usr);

		return "mypage/mypage";
	}

	@GetMapping("/checkUser")
	public String userCheck(
			@AuthenticationPrincipal UserDetails user, Model m) {
		m.addAttribute("user_email", user.getUsername());
		return "mypage/my_checkPwForm";
	}

	@PostMapping("/checkUser")
	public String userCheck(@AuthenticationPrincipal UserDetails user, String pwd) {
		log.debug("memebercontroller user : {}", user.getUsername());
		if (service.findUser(user.getUsername(), pwd)) {
			return "redirect:/updateUser";
		}
		return "redirect:/mypage";

	}

	@GetMapping("/updateUser")
	public String updateUser(@AuthenticationPrincipal UserDetails user, Model m) {
		UserDTO dto = service.findUser(user.getUsername());
		m.addAttribute("user", dto);
		
		return "mypage/my_modifyInfoForm";
	}

	@GetMapping("/re_pwd")
	public String changePwd() {
		return "/mypage/my_changepwd";
	}

}
