package com.anabada.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Admin_board;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.DetailData;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.Report;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

@Mapper
public interface AdminDAO {

	ArrayList<UserDTO> findalluser();
	
	ArrayList<Admin_board> findboard();

	ArrayList<Admin_board> findboard(String user_email);

	int statusUpdate(HashMap<String, Object> map);

	int usermodify(HashMap<String, Object> map);

	int count(HashMap<String, Object> map);

	int salesamount(int month);

	int joinamount(int month);

	ArrayList<Inquiry> getinquiry();

	int updateanswer(HashMap<String, Object> map);

	ArrayList<Report> getclaim();

	int updateclaimanswer(HashMap<String, Object> map);

	ArrayList<Inquiry> getallinquiry();

	ArrayList<Report> getallreport();
	//details
	ArrayList<DetailData> getuseddata(HashMap<String, Object> map);

	ArrayList<DetailData> getrentaldata(HashMap<String, Object> map);

	ArrayList<DetailData> getauctiondata(HashMap<String, Object> map);

	ArrayList<DetailData> getjoin(HashMap<String, Object> map);

//	ArrayList<DetailData> getvisitor(HashMap<String, Object> map);


	

}
