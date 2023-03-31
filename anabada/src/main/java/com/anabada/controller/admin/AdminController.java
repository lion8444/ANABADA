package com.anabada.controller.admin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabada.domain.Admin_board;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.UserDTO;
import com.anabada.service.admin.AdminService;

import lombok.extern.slf4j.Slf4j;

@Secured({"ROLE_SUPER","ROLE_ADMIN"})
@Slf4j
@RequestMapping({"admin"})
@Controller
public class AdminController {
	@Autowired
	private AdminService service;
		
	@GetMapping({"","/"})
	public String adminPage() {
		return "admin/admin_index";
	}

	@GetMapping({"user"})
	public String user(Model model) {
		ArrayList<UserDTO> user = service.findalluser();
		model.addAttribute("user", user);

		return "admin/admin_user.html";
	}
	
	@GetMapping({"board"})
	public String board(Model model, String user_email) {
		ArrayList<Admin_board> board = service.findboard(user_email);
		model.addAttribute("board", board);

		return "admin/admin_board.html";
	}
	
	@GetMapping("admin")
	public String adminPage(Model model) {
		int sum = service.salesamount();
		int join = service.joinamount();
		
		model.addAttribute("sum", sum);
		model.addAttribute("join", join);
		return "admin/admin_index.html";
	}
	
	@GetMapping("detail")
	public String detail(Model model) {
		
		return "admin/admin_details.html";
	}
	
	
	@GetMapping("ask")
	public String ask(Model model) {
		ArrayList<Inquiry> inquiry = service.getallinquiry();
		model.addAttribute("inquiry", inquiry);
		return "admin/admin_inquiry.html";
	}
	
	@GetMapping("report")
	public String report(Model model) {
		ArrayList<Report> report = service.getallreport();
		model.addAttribute("report", report);
		return "admin/admin_report.html";
	}
}