package com.anabada.service.rent;

import org.springframework.stereotype.Service;

import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.UserDTO;

@Service
public interface RentService {

	int purchase(Rental_detail rd);

	Rental findOneRental(String rental_id);

	UserDTO findUser(String user_email);

	int usemoney(String user_email, int user_account);
	
	
}
