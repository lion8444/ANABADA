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
	String rDetail_addr;
	int rDetail_price;
	String rDetail_status;
	String rDetail_sDate;
	String rDetail_eDate;
}
