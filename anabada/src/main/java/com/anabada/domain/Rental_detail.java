package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental_detail {
	String rDetail_id;
	String rental_id;
	String user_email;
	String chat_id;
	String rDetail_person;
	String rDetail_phone;
	String rDetail_memo;
	String rDetail_post;
	String rDetail_addr1;
	String rDetail_addr2;
	int rDetail_price;
	String rDetail_status;
	String rDetail_sDate;
	String rDetail_eDate;
}
