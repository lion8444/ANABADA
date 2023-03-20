package com.anabada.service.used;

import org.springframework.stereotype.Service;

import com.anabada.domain.Used;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

@Service
public interface UsedService {

	UserDTO findUser(String user_email);

	Used findOneUsed(String used_id);

	Used_detail findOneUseddetail(String used_id);

	int purchase(Used_detail used_detail);

	int usemoney(String user_email, int user_account);

}
