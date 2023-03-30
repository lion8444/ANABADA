package com.anabada.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.domain.UserDTO;
import com.anabada.service.login.CheckService;
import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestSignUpController {

	@Autowired
	CheckService cservice;
	
	@Autowired
	LoginService uservice;
	
	@PostMapping("check/nick")
	public int nickCheck(String user_nick) {
		log.debug("닉네임 체크 {}",user_nick);
		int availAble = cservice.nickNameCheck(user_nick);
		log.debug("닉네임 중복 : {}", availAble);
		return availAble;
	}
	
	@PostMapping("check/email")
	public int emailCheck(String user_email) {
		log.debug("이메일 체크 {}", user_email);
		int availAble = cservice.emailCheck(user_email);
		return availAble;
	}

	@PostMapping("check/phone")
	public int phoneCheck(String user_phone) {
		log.debug("전화번호 체크 {}", user_phone);
		int availAble = cservice.phoneCheck(user_phone);
		return availAble;
	}
	
	@PostMapping("signup")
	public int joinTest(UserDTO user) {
		log.debug("회원가입 동작 중");
		int result = uservice.joinUser(user); 
		return result;
	}
	
}
