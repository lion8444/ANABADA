package com.anabada.controller;

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
public class IndexController {

	@Autowired
	LoginService service;

	@GetMapping({ "", "/" })
	public String index(@AuthenticationPrincipal UserDetails user, Model m) {
		log.debug("auth : {}", user);
		if (user != null) {
			UserDTO userDTO = service.findUser(user.getUsername());
			log.debug("user : {}", userDTO);
			m.addAttribute("user", userDTO);
		}
		return "index";
	}
}