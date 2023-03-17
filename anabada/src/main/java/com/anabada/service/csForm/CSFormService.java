package com.anabada.service.csForm;

import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.Report;
import com.anabada.domain.Review;
import com.anabada.domain.Used;
import com.anabada.domain.Used_detail;

/**
 * 신고하기, 후기, 문의하기 서비스 인터페이스
 * @author 박용우
 * 0314
 */
public interface CSFormService {
	
	// 신고하기 처리
	public int insertReport(Report report);

	// 문의하기 처리
	public int insertInquiry(Inquiry inquiry);
	
	// 후기 처리
	public int insertReview(Review review);
	
	// 파일 저장
	public int insertFile(File file);

	// 중고 거래 완료 글 검색 - 중고id로
	public Used_detail selectUsedDetailById(String used_id);

	// 등록된 중고 거래 글 검색 - 중고id로
	public Used selectUsedByID(String used_id);

	// 렌탈 거래 완료 글 검색 - 렌탈id로
	public Rental_detail selectRentalDetailById(String rental_id);

	// 등록된 렌탈 거래 글 검색 - 렌탈id로
	public Rental selectRentalById(String rental_id);

	// 실시간 받아온 이메일로 등록된 유저가 있는지 검색 
	public int selectUserById(String reported);

}
