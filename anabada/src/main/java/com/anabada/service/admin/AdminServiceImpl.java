package com.anabada.service.admin;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.AdminDAO;
import com.anabada.domain.Admin_board;
import com.anabada.domain.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDAO dao;

	/**
	 * 유저 전체 찾기
	 */
	@Override
	public ArrayList<UserDTO> findalluser() {
		ArrayList<UserDTO> user = dao.findalluser();
		return user;
	}
	/**
	 * 게시글 전체 찾기 (혹시 email 함께 보내면 해당 email 게시글만 반환)
	 */
	@Override
	public ArrayList<Admin_board> findboard(String user_email) {
		ArrayList<Admin_board> board = dao.findboard(user_email);
		return board;
	}

	/**
	 * 게시글 상태 변경
	 */
	@Override
	public String statusChange(String select, Integer index) {
		if (select.equals("post")) {
			select = "게시";
		}
		if (select.equals("unpost")) {
			select = "중지";
		}		
		ArrayList<Admin_board> board = dao.findboard();
		String id = board.get(index).getId();
		HashMap<String, Object> map = new HashMap<>();
		map.put("select", select);
		map.put("id", id);
		
		
		int i = dao.statusUpdate(map);

		ArrayList<Admin_board> newboard = dao.findboard();

		
		return newboard.get(index).getStatus();
	}

	/**
	 * 유저 상태, 닉네임 변경
	 */
	@Override
	public UserDTO usermodify(String selectn, String selects, Integer index) {
		ArrayList<UserDTO> user = dao.findalluser();
		String email = user.get(index).getUser_email();
		if(selects.equals("True")) {
			selects = "1";
		}
		if(selects.equals("False")) {
			selects = "0";
		}		
		HashMap<String, Object> map = new HashMap<>();
		map.put("selects", selects);
		map.put("selectn", selectn);
		map.put("email", email);			
		
		int i = dao.usermodify(map);
		UserDTO newuser = dao.findalluser().get(index);
		
		return newuser;
	}
//	@Override
//	public ArrayList<Auction_detail> getauctiondetail() {
//		ArrayList<Auction_detail> ad = dao.getauctiondetail();
//		return ad;
//	}
//	@Override
//	public ArrayList<Rental_detail> getrentaldetail() {
//		ArrayList<Rental_detail> rd = dao.getrentaldetail();
//		return rd;
//	}
//	@Override
//	public ArrayList<Used_detail> getuseddetail() {
//		ArrayList<Used_detail> ud = dao.getuseddetail();
//		return ud;
//	}
	@Override
	public int[][] getdata(int[] monthnum) {
		int[ ][ ] num = new int[3][6];  

			for (int i = 0; i < 6; ++i) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("monthnum", monthnum[i]);
				map.put("string", "used");
				num[0][i] = dao.count(map);
			}
	
			for (int i = 0; i < monthnum.length; ++i) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("monthnum", monthnum[i]);
				map.put("string", "rental");
				num[1][i] = dao.count(map);
			}
			
			for (int i = 0; i < monthnum.length; ++i) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("monthnum", monthnum[i]);
				map.put("string", "auction");
				num[2][i] = dao.count(map);
			}
		

				
		return num;
	}
	@Override
	public int salesamount() {
		
		
		return 0;
	}
}
