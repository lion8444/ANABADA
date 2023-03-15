package com.anabada.service.csForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.CSFormDAO;
import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.Review;

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

	@Override
	public int insertFile(File file) {
		int result = dao.insertFile(file);
		return result;
	}
}
