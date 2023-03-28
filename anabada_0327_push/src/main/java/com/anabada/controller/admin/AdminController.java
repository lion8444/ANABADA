package com.anabada.controller.admin;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Secured({"ROLE_SUPER","ROLE_ADMIN"})
@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@GetMapping({"","/"})
	public String adminPage() {
		return "admin/admin_index";
	}
}
