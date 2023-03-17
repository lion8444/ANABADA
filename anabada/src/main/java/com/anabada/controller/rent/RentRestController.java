package com.anabada.controller.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anabada.domain.UserDTO;
import com.anabada.service.rent.RentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"rent"})
@Controller
@ResponseBody
public class RentRestController {
	
	@Autowired
	private RentService service;
	
	@GetMapping({"myaddr"})
	public UserDTO myaddr() {
	
	String user_email = "anabada@gmail.com";
	UserDTO user = service.findUser(user_email);

	return user;
	}
}
