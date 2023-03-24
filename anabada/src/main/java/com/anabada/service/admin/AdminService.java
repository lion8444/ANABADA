package com.anabada.service.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anabada.domain.Admin_board;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

@Service
public interface AdminService {

	ArrayList<UserDTO> findalluser();

	ArrayList<Admin_board> findboard(String user_email);

	String statusChange(String select, Integer index);

	UserDTO usermodify(String selectn, String selects, Integer index);

	int[][] getdata(int[] monthnum);

//	ArrayList<Auction_detail> getauctiondetail();
//
//	ArrayList<Rental_detail> getrentaldetail();
//
//	ArrayList<Used_detail> getuseddetail();

}
