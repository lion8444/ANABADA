package com.anabada.service.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    String sendSimpleMessage(String to) throws Exception;

}
