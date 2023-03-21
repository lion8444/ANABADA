package com.anabada.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Admin_board;
import com.anabada.domain.UserDTO;

@Mapper
public interface AdminDAO {

	ArrayList<UserDTO> findalluser();
	
	ArrayList<Admin_board> findboard();

	ArrayList<Admin_board> findboard(String user_email);

	int statusUpdate(HashMap<String, Object> map);

	int usermodify(HashMap<String, Object> map);

}
