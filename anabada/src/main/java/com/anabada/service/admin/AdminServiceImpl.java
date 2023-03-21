package com.anabada.service.admin;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.AdminDAO;
import com.anabada.domain.Admin_board;
import com.anabada.domain.UserDTO;

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
}
