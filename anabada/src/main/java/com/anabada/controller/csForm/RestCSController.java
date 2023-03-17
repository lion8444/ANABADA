package com.anabada.controller.csForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.service.csForm.CSFormService;

import lombok.extern.slf4j.Slf4j;

/**
 * cs처리 관련 ajax컨트롤러
 * @author 박용우
 * 230316
 */
@Slf4j
@RestController
public class RestCSController {
	
	@Autowired
	CSFormService service;

	@PostMapping("/reportedValidation")
	public int reportedValidation(String reported) {
		log.debug("신고대상 ajax진입");
		log.debug("들어온 데이터: {}", reported);
		
		int result = service.selectUserById(reported);
			
		return result;
	}
}
