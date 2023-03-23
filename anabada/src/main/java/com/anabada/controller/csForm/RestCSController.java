package com.anabada.controller.csForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.service.csForm.CSFormService;

import lombok.extern.slf4j.Slf4j;

/**
 * cs처리 관련 ajax컨트롤러
 * @author 박용우
 * 230316
 */
@Slf4j
@RequestMapping({"csform"})
@RestController
public class RestCSController {
	
	@Autowired
	CSFormService service;

	/**
	 * 신고페이지의 신고대상이 등록된 사용자인지 실시간 확인
	 * @param reported 리포트 폼 내의 신고대상의 이메일
	 * @return	1 - 있을 때 0 - 없을 때
	 */
	@PostMapping("/reportedValidation")
	public int reportedValidation(String reported) {
		
		log.debug("신고대상 ajax진입");
		log.debug("들어온 데이터: {}", reported);
		
		int result = service.checkUserinOn(reported);
			
		return result;
	}
}
