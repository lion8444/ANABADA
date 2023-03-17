package com.anabada.service.rent;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.RentalDAO;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.UserDTO;

@Service
public class RentServiceImpl implements RentService {
	@Autowired
	private RentalDAO dao;

	/**
	 * Rental_detail 내용 데이터베이스에 저장
	 */
	@Override
	public int purchase(Rental_detail rd) {
		if(rd.getRDetail_person() == null || rd.getRDetail_person().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_sDate() == null || rd.getRDetail_sDate().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_eDate() == null || rd.getRDetail_eDate().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_price() < 0) {
			return 0;
		}
		if(rd.getRDetail_phone() == null || rd.getRDetail_phone().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_post() == null || rd.getRDetail_post().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_addr1() == null || rd.getRDetail_addr1().length() ==0) {
			return 0;
		}
		
		int i = dao.purchase(rd);
		return i;
	}
	
	/**
	 * rental_id에 맞는 렌탈 한개 행 불러오기
	 */
	@Override
	public Rental findOneRental(String rental_id) {
		Rental rental = dao.findOneRental(rental_id);
		return rental;
	}

	/**
	 * user_email로 user 한개 행 불러오기
	 */
	@Override
	public UserDTO findUser(String user_email) {
		UserDTO user = dao.findUser(user_email);
		return user;
	}

	/**
	 * 경매 참여금액에 맞게 포인트 차감
	 */
	@Override
	public int usemoney(String user_email, int user_account) {
		UserDTO user = dao.findUser(user_email);
		int account = user.getUser_account() - user_account;
		if (account < 0) {
			return 0;
		}
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("user_email", user_email);
		map.put("account", account);
		
		int i = dao.usemoney(map);
		return i;
	}
}
