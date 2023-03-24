package com.anabada.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	
	@PostMapping({"usermodify"})
	public UserDTO usermodify(String selectn, String selects, Integer index) {
		UserDTO newuser = service.usermodify(selectn, selects, index);
		return newuser;
	}
	
	@PostMapping({"chartdate"})
	public int[ ][ ] chartdate(int[] monthnum) {
		int[ ][ ] num = new int[3][6];  
		num = service.getdata(monthnum);
		return num;
	}
}
