package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board_temp {
	String bTemp_id;
	String user_email;
	String category_id;
	String bTemp_title;
	String bTemp_date;
	Integer bTemp_price;
	String bTemp_content;
	String bTemp_quality;
	String bTemp_sDate;
	String bTemp_eDate;
	String bTemp_finish;
	String uloc_id;
	String sloc_id;
}
