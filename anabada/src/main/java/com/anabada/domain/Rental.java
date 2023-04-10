package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rental {
	String rental_id;
	String user_email;
	String user_nick;
	String category_id;
	String rental_title;
	String rental_date;
	Integer rental_price;
	String rental_content;
	String rental_quality;
	String rental_sDate;
	String rental_eDate;
	String rental_status;
	String uloc_id;
	String sloc_id;
}
