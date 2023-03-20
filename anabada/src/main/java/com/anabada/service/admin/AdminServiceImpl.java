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

	@Override
	public ArrayList<UserDTO> findalluser() {
		ArrayList<UserDTO> user = dao.findalluser();
		return user;
	}

	@Override
	public ArrayList<Admin_board> findboard() {
		ArrayList<Admin_board> board = dao.findboard();
		return board;
	}

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
}
