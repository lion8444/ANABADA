package com.anabada.service.email;

import java.util.List;
import java.util.Random;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.anabada.dao.AuctionDAO;
import com.anabada.domain.Auction;
import com.anabada.domain.AuctionEndEmail;
import com.anabada.domain.UserDTO;
import com.anabada.service.login.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuctionEmailServiceImpl implements AuctionEmailService{
 
    @Autowired
    JavaMailSender emailSender;

    @Autowired
    LoginService loginService;

    @Autowired
    AuctionDAO auctionDAO;


    private MimeMessage createMessageKorean(AuctionEndEmail auction)throws Exception{

        log.debug("보내는 대상 : "+ auction.getBuyer());
        log.debug("경매 상세 페이지 : "+ createLink(auction.getAuction_id()));

        String user_nick = loginService.findUser(auction.getBuyer()).getUser_nick();
        MimeMessage message = emailSender.createMimeMessage();
 
        message.addRecipients(RecipientType.TO, auction.getBuyer());//보내는 대상
        message.setSubject("ANABADA 입찰중인 상품 경매마감 임박 알림");//제목
 
        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h2> 안녕하세요 ANABADA입니다. </h2>";
        msgg+= "<br>";
        msgg+= "<pre style='font-size: 20px'>깨끗하고 안전한 개인거래 시장을 만드는 ANABADA입니다. \n 저희 사이트를 이용해주셔서 감사합니다.</pre>";
        msgg+= "<br>";
        msgg+= "<p style='font-size: 20px'>'"+user_nick+"'고객님께서 입찰하신 경매 상품이 마감일 <span style='color: red;'>하루 전</span>이라서 알려드립니다.<p>";
        msgg+= "<br>";
        msgg+= "<p style='font-size: 20px'>꼭 원하시는 가격에 좋은 상품 얻으시길 바라겠습니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>해당 마감 임박 경매 상품페이지 링크입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "링크 : <a style='font-size: 20px' href='";
        msgg+= createLink(auction.getAuction_id())+"'>"+ auction.getTitle() +"</a><div><br/> ";
        msgg+= "<br>";
        msgg+= "<h4>앞으로도 안전한 개인거래 시장을 위해 노력하겠습니다. 이용해주셔서 감사합니다.<h4>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("anabada.global@gmail.com","ANABADA"));//보내는 사람
 
        return message;
    }
    private MimeMessage createMessageJpanese(AuctionEndEmail auction)throws Exception{

        log.debug("보내는 대상 : "+ auction.getBuyer());
        log.debug("경매 상세 페이지 : "+ createLink(auction.getAuction_id()));

        String user_nick = loginService.findUser(auction.getBuyer()).getUser_nick();
        MimeMessage message = emailSender.createMimeMessage();
 
        message.addRecipients(RecipientType.TO, auction.getBuyer());//보내는 대상
        message.setSubject("ANABADA 入札中の商品競売締め切り間近のお知らせ");//제목
 
        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h2> こんにちは、ANABADAです。</h2>";
        msgg+= "<br>";
        msgg+= "<pre style='font-size: 20px'>クリーンで安全な個人取引市場を作るANABADAです。\n当サイトをご利用いただきありがとうございます。</pre>";
        msgg+= "<br>";
        msgg+= "<p style='font-size: 20px'>'"+user_nick+"'お客様が入札したオークション商品が締め切り日<span style='color: red;'>前日</span>ですのでお知らせいたします。<p>";
        msgg+= "<br>";
        msgg+= "<p style='font-size: 20px'>ぜひご希望の価格で良い商品を手に入れることを願います。<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>該当締切間近のオークション商品ページへのリンクです。</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "링크 : <a style='font-size: 20px' href='";
        msgg+= createLink(auction.getAuction_id())+"'>"+ auction.getTitle() +"</a><div><br/> ";
        msgg+= "<br>";
        msgg+= "<h4>これからも安全な個人取引市場のために努力していきます。ご利用ありがとうございます。<h4>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("anabada.global@gmail.com","ANABADA"));//보내는 사람
 
        return message;
    }
 
    private String createLink(String auction_id) {
        StringBuffer link = new StringBuffer();
        link.append("http://localhost:80/auction/auctionBoardRead?auction_id=");
        link.append(auction_id);
        return link.toString();
    }


    @Override
    public void sendSimpleMessage(List<AuctionEndEmail> auction)throws Exception {
        for (int i = 0; i < auction.size(); i++) {

            UserDTO user = loginService.findUser(auction.get(i).getBuyer());
            MimeMessage message;
            if (user.getUser_nation().equals("korea")) {
                message = createMessageKorean(auction.get(i));
            } else {
                message = createMessageJpanese(auction.get(i));
            }

            try { // 예외처리
                emailSender.send(message);
            } catch (MailException es) {
                es.printStackTrace();
                throw new IllegalArgumentException();
            }
        }
    }
}
