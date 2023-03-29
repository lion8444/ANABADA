package com.anabada.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.anabada.domain.DetailData;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
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
	
	@GetMapping({"inquiry"})
	public ArrayList<Inquiry> inquiry(){
		ArrayList<Inquiry> inquiry = service.getinquiry();
		return inquiry;
	}
	
	@GetMapping({"submitanswer"})
	public void submitanswer(String answer, int find) {
		int i = service.updateanswer(answer, find);
	}
	
	@GetMapping({"claim"})
	public ArrayList<Report> claim(){
		ArrayList<Report> claim = service.getclaim();
		return claim;
	}
	
	@GetMapping({"claimanswer"})
	public void claimanswer(String claimanswer, int find) {
		int i = service.updateclaimanswer(claimanswer, find);
	}
	
	@PostMapping({"getdata"})
	public ArrayList<DetailData> getdata(
			String used
			, String rental
			, String auction
			, String number
			, String amount
			, String nkorea
			, String njapan
			, String lkorea
			, String ljapan
			, String visitor
			, String join
			, String sdate
			, String edate){
		ArrayList<DetailData> alldata = service.getdetaildata(used, rental, auction, number, amount, nkorea, 
				njapan, lkorea, ljapan, visitor, join, sdate, edate);
		
		if(alldata.size() == 0) {
			return null;
		}
		
		return alldata;
	}
	
}
