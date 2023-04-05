package com.anabada.dao;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.AuctionAndFile;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Rental;
import com.anabada.domain.RentalAndFile;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.Report;
import com.anabada.domain.Review;
import com.anabada.domain.Used;
import com.anabada.domain.UsedAndFile;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

/**
 * 신고하기, 후기, 문의하기 DAO 인터페이스
 * @author 박용우
 * 0314
 */
@Mapper
public interface CSFormDAO {

	/**
	 * 신고하기 처리
	 * @param report : 신고하기로 올라온 데이터
	 */
	public int insertReport(Report report);

	/**
	 * 문의하기 처리
	 * @param report : 문의하기로 올라온 데이터
	 */
	public int insertInquiry(Inquiry inquiry);
	
	/**
	 * 후기작성 처리
	 * @param review : 후기작성으로 올라온 데이터
	 */
	public int insertReview(Review review);

	/**
	 * 파일 저장 처리
	 * @param file : 올라온 파일
	 * @return 저장된 개수
	 */
	public int insertFile(File file);

	/**
	 * 중고거래 글 검색 - 중고거래글 id로
	 * @param used_id : 중고거래 글 ID
	 * @return 검색된 개수
	 */
	public Used selectUsedByID(String used_id);

	/**
	 * 렌탈거래 글 검색 - 렌탈거래글 id로
	 * @param rental_id 렌탈거래글 id
	 * @return
	 */
	public Rental selectRentalById(String rental_id);

	/**
	 * 실시간 받아온 이메일로 등록된 유저가 있는지 검색
	 * @param reported 신고대상으로 입력되는 이메일
	 * @return 검색된 개수
	 */
	public int checkUserinOn(String reported);

	/**
	 * 받아온 이메일로 등록된 유저의 정보 검색
	 * @param user_email
	 * @return
	 */
	public UserDTO selectUserById(String user_email);

	/**
	 * 등록된 중고 완료 거래 글 검색 - 중고id로
	 * @param used_id 중고글 ID
	 * @return Used_detail 객체
	 */
	public Used selectUsedFinished(String used_id);

	/**
	 * 등록된 렌탈 완료 거래 글 검색 - 중고id로
	 * @param rental_id 렌탈글 ID
	 * @return rental_detail 객체
	 */
	public Rental selectRentalFinished(String rental_id);

	

}
