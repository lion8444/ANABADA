package com.anabada.dao;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.UserDTO;

@Mapper
public interface UserDAO {
	
	int nickNameCheck(String user_nick);

	int emailCheck(String user_email);
	
	int joinUser(UserDTO user);

	UserDTO findUser(String username);

    int phoneCheck(String user_phone);

    int withdraw(String username);

    int updateUser(UserDTO user);

}
