package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	String user_email;
	String user_nick;
	String user_pwd;
	String user_phone;
	String user_addr;
	int user_level;
	int user_trade;
	int user_penalty;
	int user_account;
	String user_role;
	String user_nation;
	String user_date;
	
}
