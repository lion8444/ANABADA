package com.anabada.service.login;

import org.springframework.stereotype.Service;

import com.anabada.domain.UserDTO;

@Service
public interface CheckService {

	int nickNameCheck(String user_nick);

	int emailCheck(String user_email);

    int phoneCheck(String user_phone);

	int nickNameCheck(UserDTO user, String user_nick);

    int phoneCheck(UserDTO user, String user_phone);
}
