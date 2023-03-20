package com.anabada.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Used;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

@Mapper
public interface UsedDAO {

	UserDTO findUser(String user_email);

	Used findOneUsed(String used_id);

	Used_detail findOneUseddetail(String used_id);

	int purchase(Used_detail used_detail);

	int usemoney(HashMap<String, Object> map);

}
