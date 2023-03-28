package com.anabada.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.domain.UserDTO;
import com.anabada.service.login.CheckService;
import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestMemberController {

    @Autowired
    CheckService cservice;

    @Autowired
    LoginService service;

    @PostMapping("update/check/nick")
	public int nickCheck(@AuthenticationPrincipal UserDetails userdetail, String user_nick) {
		log.debug("닉네임 체크 {}",user_nick);

        UserDTO user = service.findUser(userdetail.getUsername());
		int availAble = cservice.nickNameCheck(user, user_nick);
		return availAble;
	}
	
	@PostMapping("update/check/phone")
	public int phoneCheck(
            @AuthenticationPrincipal UserDetails userdetail
            , String user_phone) {
		log.debug("휴대폰 체크 {}", user_phone);
        UserDTO user = service.findUser(userdetail.getUsername());
		int availAble = cservice.phoneCheck(user, user_phone);
		return availAble;
	}

    @PostMapping("/updateUser")
	public int updateUser(@AuthenticationPrincipal UserDetails uDetails, UserDTO user) {
        user.setUser_email(uDetails.getUsername());
        int res = service.updateUser(user);
        return res;
    }
    
    @PostMapping("/withdraw")
	public int withdraw(@AuthenticationPrincipal UserDetails user) {
		int result = service.withdraw(user.getUsername());
		return result;
	}

}
