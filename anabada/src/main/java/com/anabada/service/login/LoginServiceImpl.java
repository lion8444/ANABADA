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

	@Override
	public UserDTO findUserNick(String nick) {
		UserDTO user = dao.findUserNick(nick);
		return user;
	}

	@Override
	public boolean findUser(String user_email, String pwd) {
		UserDTO user = dao.findUser(user_email);
		log.debug("비밀번호 비교 : {}", user.getPassword());
		log.debug("비밀번호 비교 : {}", pwd);
		log.debug("비밀번호 비교 : {}", encoder.matches(pwd, user.getPassword()));
		boolean res = encoder.matches(pwd, user.getPassword());
		return res;	
	}

	@Override
	public int withdraw(String username) {
		int res = dao.withdraw(username);
		return res;
	}

	@Override
	public int updateUser(UserDTO user) {
		if(user.getUser_pwd() == null || user.getUser_pwd() == "") {
			user.setUser_pwd("");
		} else {
			user.setUser_pwd(encoder.encode(user.getUser_pwd()));
		}
		int res = dao.updateUser(user);
		return res;
	}
}
