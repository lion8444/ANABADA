package com.anabada.controller.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anabada.domain.Auction_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.auction.AuctionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"auction"})
@Controller
@ResponseBody
public class AuctionRestController {
	
	@Autowired
	private AuctionService service;
	
	@GetMapping({"myaddr"})
	public UserDTO myaddr() {
	
	String user_email = "anabada@gmail.com";
	UserDTO user = service.findUser(user_email);

	return user;
	}
	
	@GetMapping({"nowprice"})
	public Auction_detail nowprice(String auction_id) {
		Auction_detail auction_detail = service.findOneAuctiondetail(auction_id);
	return auction_detail;
	}
}
