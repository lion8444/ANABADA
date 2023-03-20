package com.anabada.service.used;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.UsedDAO;
import com.anabada.domain.Used;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

@Service
public class UsedServiceImpl implements UsedService {

	@Autowired
	private UsedDAO dao;
	

	/**
	 * user_email로 user 한개 행 불러오기
	 */
	@Override
	public UserDTO findUser(String user_email) {
		UserDTO user = dao.findUser(user_email);
		return user;
	}


	@Override
	public Used findOneUsed(String used_id) {
		Used used = dao.findOneUsed(used_id);
		return used;
	}


	@Override
	public Used_detail findOneUseddetail(String used_id) {
		Used_detail used_detail= dao.findOneUseddetail(used_id);
		return used_detail;
	}


	@Override
	public int purchase(Used_detail used_detail) {
		if(used_detail.getUsed_id() == null || used_detail.getUsed_id().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_person() == null || used_detail.getUDetail_person().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_price() < 0) {
			return 0;
		}
		if(used_detail.getUDetail_phone() == null || used_detail.getUDetail_phone().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_post() == null || used_detail.getUDetail_post().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_addr1() == null || used_detail.getUDetail_addr1().length() ==0) {
			return 0;
		}
		
		int k = dao.purchase(used_detail);
		return k;
	}


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
