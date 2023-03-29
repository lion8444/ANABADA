package com.anabada.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class I18nController {	

	@GetMapping("test")
	public String test() {
		return "translateTest";
	}
	
	@GetMapping("test2")
	public String test2() {
		return "translateTest2";
	}
	
	@GetMapping("test3")
	public String test3() {
		return "translateTest3";
	}
}
