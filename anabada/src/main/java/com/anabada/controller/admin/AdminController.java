package com.anabada.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabada.service.admin.AdminService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"admin"})	
@Controller
public class AdminController {
	@Autowired
	private AdminService service;
	
	@GetMapping({"user"})
	public String user() {


		return "admin/admin_user.html";
	}
}
