package com.anabada.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anabada.dao.UserDAO;
import com.anabada.domain.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserDAO dao;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public int joinUser(UserDTO user) {
		String pwd = encoder.encode(user.getUser_pwd());
		user.setUser_pwd(pwd);
		int res = dao.joinUser(user);
		return res;
	}

	@Override
	public UserDTO findUser(String username) {
		UserDTO user = dao.findUser(username);
		return user;
	}

	

}
