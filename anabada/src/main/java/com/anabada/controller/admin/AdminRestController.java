package com.anabada.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anabada.domain.Auction_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.admin.AdminService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"admin"})
@Controller
@ResponseBody
public class AdminRestController {
	
	@Autowired
	private AdminService service;
	
	@GetMapping({"confirm"})
	public String comfirm(String select, Integer index) {
		String change = service.statusChange(select, index);
		return change;
	}
	
}
