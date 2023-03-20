package com.anabada.service.mypage;

import java.util.List;

import com.anabada.domain.CharacterDTO;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.UserDTO;

public interface MyPageService {

	/**
	 * 이메일로 유저의 정보 검색
	 * @param user_email 유저의 이메일
	 * @return user타입 객체
	 */
	public UserDTO selectUserById(String user_email);

	/**
	 * 이메일로 유저의 다마고치 정보 검색
	 * @param user_email 유저의 이메일
	 * @return 다마고치 정보 객체
	 */
	public CharacterDTO selectUserDama(String user_email);

	/**
	 * 이메일로 유저의 모든 문의 내역 검색
	 * @param user_email 유저의 이메일
	 * @return 유저가 했던 모든 문의내역 리스트
	 */
	public List<Inquiry> selectInquiryList(String user_email);

	public List<Report> selectReportList(String user_email);

}
