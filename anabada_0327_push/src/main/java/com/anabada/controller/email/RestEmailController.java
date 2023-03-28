package com.anabada.controller.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.service.email.EmailServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestEmailController {

    @Autowired
    EmailServiceImpl service;

    @PostMapping("/email")
    public void emailConfirm(String userId)throws Exception{
        log.info("post emailConfirm");
        log.debug("전달 받은 이메일 : {}", userId);
        service.sendSimpleMessage(userId);
        return;
    }
    
    @PostMapping("/verifyCode")
    public int verifyCode(String code) {
        log.info("Post verifyCode");
        
        int result = 0;
        log.debug("code : {}", code);
        log.debug("code match : {}", service.getEmailCode().equals(code));
        if(service.getEmailCode().equals(code)) {
            result = 1;
        }
        return result;
    }
}
