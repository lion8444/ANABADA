package com.anabada.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.UserDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CheckServiceImpl implements CheckService {
	
	@Autowired
	UserDAO dao;
	
	@Override
	public int nickNameCheck(String user_nick) {
		int availAble = dao.nickNameCheck(user_nick); 
		return availAble;
	}

	@Override
	public int emailCheck(String user_email) {
		int availAble = dao.emailCheck(user_email);
		return availAble;
	}

	@Override
	public int phoneCheck(String user_phone) {
		int availAble = dao.phoneCheck(user_phone);
		return availAble;
	}

}
