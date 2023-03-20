package com.anabada.controller.used;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabada.domain.Used;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.used.UsedService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"used"})
@Controller
public class UsedController {
	@Autowired
	private UsedService service;
	
	@GetMapping({"purchase"})
	public String purchase(@AuthenticationPrincipal UserDetails user
			,String used_id, Model model) {
		Used used = service.findOneUsed(used_id);
//		Used_detail used_detail= service.findOneUseddetail(used_id);
		String user_email = user.getUsername();
		UserDTO userone = service.findUser(user_email);
//		//Auction_bid auction_bid= service.findOneAuctionbid();
		
		model.addAttribute("used", used);
//		model.addAttribute("auction_detail", auction_detail);
		model.addAttribute("user", userone);

		return "used/usedPurchase(JPBP).html";
	}
	
	@PostMapping({"purchase"})
	public String purchase(
			@AuthenticationPrincipal UserDetails user
			, Used_detail used_detail
			, int user_account
			) {
		String user_email = user.getUsername();
		used_detail.setUser_email(user_email);
		int k = service.purchase(used_detail);
		
		int i = service.usemoney(user_email, user_account);		
		
		if(k == 0 || i ==0) {
			return "redirect:/used/purchase?used_id=" + used_detail.getUsed_id();
		}		
		
		return "used/usedThanks.html";
	}
}
