package com.anabada.controller.email;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anabada.dao.AuctionDAO;
import com.anabada.domain.AuctionEndEmail;
import com.anabada.service.email.AuctionEmailService;
import com.anabada.service.email.AuctionEmailServiceImpl;
import com.anabada.service.email.EmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@Component
public class AuctionEndEmailController {

    @Autowired
    AuctionEmailService service;

    @Autowired
    AuctionDAO auctionDAO;

    @Scheduled(cron = "0 0 0 * * *")
    public void auctionEndTest() throws Exception {
        List<AuctionEndEmail> auction = auctionDAO.auctionEmail();
        log.debug("@AuctionEndEmailController auctionEndTest : {}", auction);
        if(auction == null) {
            return;
        }
        service.sendSimpleMessage(auction);
    }
    
}
