package com.anabada.service.csForm;

import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.Review;

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

	public int insertFile(File file);

}
