package com.anabada.controller.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.anabada.domain.UserDTO;
import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

	@Autowired
	private LoginService service;
	
	@GetMapping("/login")
	public String login(Model m) {
//		log.debug("로그 확인!!!!!!!!!!!!!!!!!!!!!!!!!!");
		List<UserDTO> list = service.allUser();
		
		m.addAttribute("list", list);
		return "login/checkPwForm";
	}
}