package com.anabada.controller.used;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anabada.domain.Category;
import com.anabada.domain.Used;
import com.anabada.domain.UserDTO;
import com.anabada.service.used.UsedService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"used"})
@Controller
@ResponseBody
public class UsedRestController {
	
	@Autowired
	private UsedService service;
	
	@GetMapping({"myaddr"})
	public UserDTO myaddr(@AuthenticationPrincipal UserDetails user) {
	
	String user_email = user.getUsername();
	UserDTO userone = service.findUser(user_email);

	return userone;
	}
	
	@PostMapping({"tempadd"})
	public void tempadd(@AuthenticationPrincipal UserDetails user
				,Used formdata) {
		formdata.setUser_email(user.getUsername());
		int i = service.addtemp(formdata);
	}
	
	@PostMapping("charge")
	public int charge(@AuthenticationPrincipal UserDetails userDetails, String money) {
			if(isInteger(money)) {
				String email = userDetails.getUsername();
				int result = service.addmoney(email, money);
				return result;
			}
		
		return 0;
	}

	private boolean isInteger(String money) {
	    try {
	        Integer.parseInt(money);
	        return true;
	      } catch (NumberFormatException ex) {
	        return false;
	      }
	    }
	
	@GetMapping("subcate")
	public ArrayList<Category> subcate(String main) {
		ArrayList<Category> category_sub = service.subcategory(main);
		return category_sub;
	}
}
