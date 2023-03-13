package com.anabada.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.anabada.domain.UserDTO;

@Repository
@Mapper
public interface UserDAO {
	
	List<UserDTO> allUser();
}
