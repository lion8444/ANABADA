package com.anabada.service.login;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.UserDAO;
import com.anabada.domain.UserDTO;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserDAO dao;
	
	@Override
	public List<UserDTO> allUser() {
		List<UserDTO> list = dao.allUser();
		return list;
	}

}
