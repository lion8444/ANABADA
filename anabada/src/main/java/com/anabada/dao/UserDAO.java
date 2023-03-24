package com.anabada.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.anabada.domain.UserDTO;

@Mapper
public interface UserDAO {
	
	int nickNameCheck(String user_nick);

	int emailCheck(String user_email);
	
	int joinUser(UserDTO user);

	UserDTO findUser(String username);

	int phoneCheck(String user_phone);

}
