package com.anabada.controller.wish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.service.wish.WishService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("wish")
@RestController
public class RestWishController {
	
	@Autowired
	WishService service;

	@PostMapping("insertWish")
	public void insertWish(String boardno, String email) {
		log.debug("찜 추가 진입");
		log.debug("보드넘 : {}, 이메일 : {}", boardno, email);
		
		service.insertWish(boardno, email);
	}
	
	@PostMapping("deleteWish")
	public void deleteWish(String boardno, String email) {
		log.debug("찜 삭제 진입");
		log.debug("보드넘 : {}, 이메일 : {}", boardno, email);
		
		service.deleteWish(boardno, email);
	}
	
}
