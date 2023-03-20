package com.anabada.service.mypage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.MyPageDAO;
import com.anabada.domain.CharacterDTO;
import com.anabada.domain.Inquiry;
import com.anabada.domain.UserDTO;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	MyPageDAO dao;
	
	/**
	 * 유저 이메일로 유저 정보 검색
	 */
	@Override
	public UserDTO selectUserById(String user_email) {
		UserDTO UserDTO = dao.selectUserById(user_email);
		return UserDTO;
	}

	/**
	 * 유저 이메일로 유저의 다마고치 정보 검색
	 */
	@Override
	public CharacterDTO selectUserDama(String user_email) {
		CharacterDTO characterDTO = dao.selectUserDama(user_email);
		return characterDTO;
	}

	/**
	 * 유저 이메일로 유저의 모든 문의 내역 리스트 검색
	 */
	@Override
	public List<Inquiry> selectInquiryList(String user_email) {
		List<Inquiry> list = dao.selectInquiryList(user_email);
		return list;
	}

}
