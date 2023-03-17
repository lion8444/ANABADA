package com.anabada.controller.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabada.domain.Auction;
import com.anabada.domain.Auction_bid;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.auction.AuctionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"auction"})
@Controller
public class AuctionController {
	
	@Autowired
	private AuctionService service;
	
	@GetMapping({"purchase"})
	public String purchase(String auction_id, Model model) {
		Auction auction = service.findOneAuction(auction_id);
		Auction_detail auction_detail= service.findOneAuctiondetail(auction_id);
		String user_email = "anabada@gmail.com";
		UserDTO user = service.findUser(user_email);
		//Auction_bid auction_bid= service.findOneAuctionbid();
		
		model.addAttribute("auction", auction);
		model.addAttribute("auction_detail", auction_detail);
		model.addAttribute("user", user);

		return "auction/auctionPurchase(GBP).html";
	}
	
	@PostMapping({"purchase"})
	public String purchase(
			@AuthenticationPrincipal UserDetails user
			, Auction_detail auction_detail
			, int user_account
			) {
		String user_email = "anabada@gmail.com";
		auction_detail.setUser_email(user_email);
		int k = service.bid(auction_detail);
		
		int i = service.usemoney(user_email, user_account);		
		
		if(k == 0 || i ==0) {
			return "redirect:/auction/purchase?auction_id=" + auction_detail.getAuction_id();
		}
		
		
		return "auction/auctionThanks.html";
	}
}
