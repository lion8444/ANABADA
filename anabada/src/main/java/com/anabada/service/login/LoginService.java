package com.anabada.service.login;


import org.springframework.stereotype.Service;

import com.anabada.domain.UserDTO;

@Service
public interface LoginService {

	int joinUser(UserDTO user);

	UserDTO findUser(String username);
	
	UserDTO findUserNick(String nick);

	boolean findUser(String username, String pwd);

	int withdraw(String username);

	int updateUser(UserDTO user);

}
