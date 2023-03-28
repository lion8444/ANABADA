package com.anabada.service.email;

import java.util.Random;
 
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Service
public class EmailServiceImpl implements EmailService{
 
    @Autowired
    JavaMailSender emailSender;
 
    private String emailCode = createKey();

    public String getEmailCode() {
        return this.emailCode;
    }
 
    private MimeMessage createMessage(String to)throws Exception{

        log.debug("보내는 대상 : "+ to);
        log.debug("인증 번호 : "+emailCode);
        MimeMessage  message = emailSender.createMimeMessage();
 
        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("ANABADA회원가입 이메일 인증");//제목
 
        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 ANABADA입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<pre>깨끗하고 안전한 개인거래 시장을 만드는 ANABADA입니다.</pre>";
        msgg+= "<br>";
        msgg+= "<p>아래 8자리 코드를 회원가입 창에서 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>앞으로도 안전한 개인거래 시장을 위해 노력하겠습니다. 감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= emailCode+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("anabada.global@gmail.com","ANABADA"));//보내는 사람
 
        return message;
    }
 
    private String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();
 
        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤
 
            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
 
        return key.toString();
    }
    @Override
    public String sendSimpleMessage(String to)throws Exception {
        MimeMessage message = createMessage(to);
        try{    //예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return emailCode;
    }
 
}

