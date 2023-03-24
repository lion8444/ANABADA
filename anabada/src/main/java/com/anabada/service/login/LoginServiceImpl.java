package com.anabada.service.login;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.UserDAO;
import com.anabada.domain.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserDAO dao;
	
	@Override
	public List<UserDTO> allUser() {
//		log.debug("로그 확인!!!!!!!!!!!!!!!!!!!!!!!!!!");
		List<UserDTO> list = dao.allUser();
//		log.debug("{}",list);
		return list;
	}

}
