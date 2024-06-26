package com.anabada.service.csForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.CSFormDAO;
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
 * 신고하기, 후기, 문의하기 서비스 인터페이스임플
 * @author 박용우
 * 0314
 */
@Service
public class CSFormServiceImpl implements CSFormService {

	@Autowired
	CSFormDAO dao;
	
	/**
	 * 신고하기 처리
	 * @param report : 신고하기로 올라온 데이터
	 * @return 신고하기 처리된 개수
	 */
	@Override
	public int insertReport(Report report) {
		
		int result = dao.insertReport(report);
		
		return result;
	}

	/**
	 * 문의하기 처리
	 * @param inquiry 문의하기로 올라온 데이터
	 * @return 문의하기 처리된 개수
	 */
	@Override
	public int insertInquiry(Inquiry inquiry) {
		
		int result = dao.insertInquiry(inquiry);
		
		return result;
	}

	/**
	 * 후기작성 처리
	 * @param review 후기작성으로 올라온 데이터
	 * @return 후기작성 처리된 개수
	 */
	@Override
	public int insertReview(Review review) {
		
		int result = dao.insertReview(review);
		
		return result;
	}
	
	/**
	 * 파일 저장 처리
	 * @param file 올라온 파일 데이터
	 * @return 저장된 개수
	 */
	@Override
	public int insertFile(File file) {
		int result = dao.insertFile(file);
		return result;
	}
	
	/**
	 * 중고거래 글 검색 - 중고거래글 id로
	 */
	@Override
	public Used selectUsedByID(String used_id) {
		Used used = dao.selectUsedByID(used_id);
		return used;
	}

	/**
	 * 렌탈거래 글 검색 - 렌탈거래글 id로
	 */
	@Override
	public Rental selectRentalById(String rental_id) {
		Rental rental = dao.selectRentalById(rental_id);
		return rental;
	}

	/**
	 * 실시간 받아온 이메일로 등록된 유저가 있는지 검색
	 */
	@Override
	public int checkUserinOn(String reported) {
		int result = dao.checkUserinOn(reported);
		return result;
	}

	/**
	 * 받아온 이메일로 등록된 유저의 정보 검색
	 */
	@Override
	public UserDTO selectUserById(String user_email) {
		UserDTO userDTO = dao.selectUserById(user_email);
		return userDTO;
	}

	/**
	 * 중고 거래 완료 글 검색(조인)
	 */
	@Override
	public Used selectUsedFinished(String used_id) {
		Used ud = dao.selectUsedFinished(used_id);
		return ud;
	}

	/**
	 * 렌탈 거래 완료 글 검색(조인)
	 */
	@Override
	public Rental selectRentalFinished(String rental_id) {
		Rental rd = dao.selectRentalFinished(rental_id);
		return rd;
	}

	


	
	

}
