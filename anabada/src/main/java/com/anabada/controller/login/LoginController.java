package com.anabada.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.anabada.domain.UserDTO;
import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	private LoginService service;
	
	@Autowired
	RestSignUpController sign;
	
	@GetMapping("/login")
	public String login() {
		return "/login/loginForm";
	}

	@GetMapping("/join")
	public String join() {
		return "/login/signupForm";
	}
	
	@PostMapping("/join")
	public String join(UserDTO user) {
		log.debug("가입자 : {}", user);
		sign.joinTest(user);
		return "redirect:/";
	}
	
}