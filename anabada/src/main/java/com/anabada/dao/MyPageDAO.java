package com.anabada.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.CharacterDTO;
import com.anabada.domain.Inquiry;
import com.anabada.domain.UserDTO;

@Mapper
public interface MyPageDAO {

	/**
	 * 받아온 이메일로 유저의 정보 검색
	 * @param user_email 유저의 이메일(아이디)
	 * @return 유저의 정보 객체
	 */
	public UserDTO selectUserById(String user_email);

	/**
	 * 받아온 이메일로 유저의 다마고치 검색
	 * @param user_email 유저의 이메일(아이디)
	 * @return 유저의 다마고치 정보 객체
	 */
	public CharacterDTO selectUserDama(String user_email);

	/**
	 * 받아온 이메일로 유저의 문의 내역 리스트 검색
	 * @param user_email 유저의 이메일
	 * @return 유저의 문의내역 리스트
	 */
	public List<Inquiry> selectInquiryList(String user_email);

}
