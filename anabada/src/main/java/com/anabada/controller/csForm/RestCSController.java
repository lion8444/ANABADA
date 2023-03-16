package com.anabada.controller.csForm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * cs처리 관련 ajax컨트롤러
 * @author 박용우
 * 230316
 */
@Slf4j
@RestController
public class RestCSController {

	@PostMapping("/reportedValidation")
	public String reportedValidation(String reported) {
		log.debug("들어온 데이터: {}", reported);
		return "ㅎㅇㅎㅇ";
	}
}
