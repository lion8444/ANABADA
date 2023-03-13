package com.anabada.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	private LoginService service;
	
	@GetMapping("/")
	public String login() {
		service.allUser();
		return "/checkPwForm";
	}
}
