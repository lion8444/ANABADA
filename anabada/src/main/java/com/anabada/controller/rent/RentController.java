package com.anabada.controller.rent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.rent.RentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({"rent"})
@Controller
public class RentController {
	
	@Autowired
	private RentService service;
	
	@GetMapping({"purchase"})
	public String purchase(String rental_id, Model model) {
		Rental rental = service.findOneRental(rental_id);
		String user_email = "anabada@gmail.com";
		UserDTO user = service.findUser(user_email);
		
		model.addAttribute("rental", rental);
		model.addAttribute("user", user);

		return "rental/rentalPurchase(RBRP).html";
	}
	
	@PostMapping({"purchase"})
	public String purchase(
			@AuthenticationPrincipal UserDetails user
			,String rental_id
			,String rDetail_sDate
			,String rDetail_eDate
			,int rDetail_price
			,String rDetail_person
			,String rDetail_phone
			,String rDetail_post
			,String rDetail_addr1
			,String rDetail_addr2
			,String rDetail_memo
			,int user_account) {
		
		String user_email = "anabada@gmail.com";

		Rental_detail rd = new Rental_detail(null, rental_id, user_email, null, rDetail_person, rDetail_phone, rDetail_memo, rDetail_post, rDetail_addr1, rDetail_addr2, rDetail_price, null, rDetail_sDate, rDetail_eDate);
		int j = service.purchase(rd);		
		
		int i = service.usemoney(user_email, user_account);		
		
		if(j == 0 || i ==0) {
			return "redirect:/rent/purchase?rental_id=" + rental_id;
		}
		
		
		return "rental/rentalThanks.html";
	}
}	
