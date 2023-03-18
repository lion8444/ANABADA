package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Used_detail {
	String uDetail_id;
	String used_id;
	String user_email;
	String uDetail_method;
	String uDetail_person;
	String uDetail_phone;
	String uDetail_post;
	String uDetail_addr1;
	String uDetail_addr2;
	String uDetail_memo;
	String chat_id;
	int uDetail_price;
	String uDetail_status;
	String uDetail_Date;
}
