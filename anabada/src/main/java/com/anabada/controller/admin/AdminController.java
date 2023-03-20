package com.anabada.controller.admin;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabada.domain.Admin_board;
import com.anabada.domain.UserDTO;
import com.anabada.service.admin.AdminService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"admin"})	
@Controller
public class AdminController {
	@Autowired
	private AdminService service;
	
	@GetMapping({"user"})
	public String user(Model model) {
		ArrayList<UserDTO> user = service.findalluser();
		model.addAttribute("user", user);

		return "admin/admin_user.html";
	}
	
	@GetMapping({"board"})
	public String board(Model model) {
		ArrayList<Admin_board> board = service.findboard();
		model.addAttribute("board", board);

		return "admin/admin_board.html";
	}
}
