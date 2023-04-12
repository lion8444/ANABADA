package com.anabada.service.email;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anabada.domain.AuctionEndEmail;

@Service
public interface AuctionEmailService {

    void sendSimpleMessage(List<AuctionEndEmail> auction) throws Exception;

}
