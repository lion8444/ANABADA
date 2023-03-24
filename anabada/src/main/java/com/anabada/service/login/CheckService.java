package com.anabada.service.login;

import org.springframework.stereotype.Service;

@Service
public interface CheckService {

	int nickNameCheck(String user_nick);

	int emailCheck(String user_email);

	int phoneCheck(String user_phone);

}
