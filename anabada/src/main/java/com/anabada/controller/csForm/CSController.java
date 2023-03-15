package com.anabada.controller.csForm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.Review;
import com.anabada.service.csForm.CSFormService;
import com.anabada.util.FileService;

import lombok.extern.slf4j.Slf4j;

/**
 * 신고하기, 후기, 문의하기 관련 컨트롤러
 * @author 박용우
 *	0314
 */
@Slf4j
@Controller
public class CSController {
	
	@Autowired
	CSFormService service;
	
	// 설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	/**
	 * 신고하기 폼으로 이동하기
	 * @return 신고하기 작성 폼
	 */
	@GetMapping("/report")
	public String report() {
		
		log.debug("신고페이지 진입");
		
		return "csForm/reportForm";
	}
	
	/**
	 * 신고하기 처리하기
	 * @param report : 신고하기로 올라온 데이터
	 * @return 리다이렉트
	 */
	@PostMapping("/report")
	public String report(
			Report report
			, File file
			, ArrayList<MultipartFile> upload) {
		log.debug("신고하기로 올라온 데이터 : {}", report);
		
//		log.debug("업로드된 파일 이름 : {}", ((MultipartFile) upload).getOriginalFilename());
//		log.debug("업로드된 파일의 타입 : {}", ((MultipartFile) upload).getContentType());
//		log.debug("업로드된 파일의 사이즈 : {}", ((MultipartFile) upload).getSize());
//		log.debug("업로드된 파일 is Empty?? : {}", upload.isEmpty());
		
		for (MultipartFile multipartFile : upload) {
			log.debug("파일 형식 : {}", multipartFile);
		}
		
		
		if(upload != null && upload.size() != 0) {			
			for(int i = 0; i < upload.size(); ++i) {
				String filename = FileService.saveFile(upload.get(i), uploadPath);
				file.setFile_origin(upload.get(i).getOriginalFilename());
				file.setFile_saved(filename);
				service.insertFile(file);
			}			
		}
		
		service.insertReport(report);
		
		
		return "redirect:/";
	}
	
	/**
	 * 마이페이지_문의하기 폼으로 포워딩
	 * @return 문의하기 작성 폼
	 */
	@GetMapping("/inquiry")
	public String inquiry() {
		
		log.debug("문의하기 작성 폼 이동");
		
		return "csForm/my_inquireForm";
	}
	
	/**
	 * 문의하기 처리
	 * @param inquiry : 문의하기로 올라온 데이터
	 * @return 리다이렉트
	 */
	@PostMapping("/inquiry")
	public String inquiry(Inquiry inquiry) {
		
		log.debug("문의하기로 올라온 데이터 : {}", inquiry);
		
		service.insertInquiry(inquiry);
		
		return "redirect:/";
	}
	
	/**
	 * 후기작성 폼으로 포워딩
	 * @return 후기작성 폼으로 이동
	 */
	@GetMapping("/review")
	public String review() {
		
		log.debug("후기 페이지로 접속");
		
		return "csForm/reviewForm";
	}
	
	/**
	 * 후기작성 처리
	 * @param review 후기작성 폼에서 올라오는 데이터
	 * @return 리다이렉트
	 */
	@PostMapping("/review")
	public String review(Review review) {
		
		log.debug("후기로 올라온 데이터 :{}", review);
		
		service.insertReview(review);
		
		return "redirect:/";
	}
}
