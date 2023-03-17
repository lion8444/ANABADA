package com.anabada.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 컨트롤러
 * @author 박용우
 * 230317
 */
@Slf4j
@Controller
public class MyPageController {

	/**
	 * 마이페이지 포워딩
	 * @return 마이페이지
	 */
	@GetMapping("/mypage")
	public String myPage() {
		return "mypage/mypage";
	}
}
