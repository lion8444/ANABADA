package com.anabada.controller.auction;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anabada.domain.Auction;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.Category;
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
	public UserDTO myaddr(@AuthenticationPrincipal UserDetails user) {
	
	String user_email = user.getUsername();
	UserDTO userone = service.findUser(user_email);

	return userone;
	}
	
	@GetMapping({"nowprice"})
	public int nowprice(String auction_id) {
		Auction_detail auction_detail = service.findOneAuctiondetail(auction_id);
		int price = 0;
		if(auction_detail == null) {
			Auction auction = service.findOneAuction(auction_id);
			price = auction.getAuction_price();
			return price;
		}
		price = auction_detail.getADetail_price();
	return price;
	}
	
	@PostMapping({"tempadd"})
	public void tempadd(@AuthenticationPrincipal UserDetails user
				,Auction formdata) {
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
