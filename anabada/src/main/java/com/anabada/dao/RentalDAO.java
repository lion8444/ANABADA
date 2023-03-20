package com.anabada.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.UserDTO;

@Mapper
public interface RentalDAO {

	int purchase(Rental_detail rd);

	Rental findOneRental(String rental_id);

	UserDTO findUser(String user_email);

	int usemoney(HashMap<String, Object> map);

}
