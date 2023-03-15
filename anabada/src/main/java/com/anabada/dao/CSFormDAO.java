package com.anabada.dao;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.Review;

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
}
