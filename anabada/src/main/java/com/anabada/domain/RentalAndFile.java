package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalAndFile {
	String rental_id;
	String user_email;
	String buyer_email;
	String category_id;
	String rental_title;
	String rental_date;
	int rental_price;
	String rental_content;
	String rental_quality;
	String rental_status;
	String uloc_id;
	String sloc_id;
	String file_origin;
	String file_saved;
	String rDetail_status;
	String rDetail_Date;
	String rDetail_sDate;
	String rDetail_eDate;
}
