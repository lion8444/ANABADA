package com.anabada.service.login;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anabada.domain.UserDTO;

@Service
public interface LoginService {

	List<UserDTO> allUser();

}
