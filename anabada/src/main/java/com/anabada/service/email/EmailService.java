package com.anabada.service.email;

import org.springframework.stereotype.Service;

import com.anabada.domain.Auction;

@Service
public interface EmailService {

    String sendSimpleMessage(String to) throws Exception;

}
