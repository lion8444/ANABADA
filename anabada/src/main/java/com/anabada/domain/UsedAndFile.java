package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedAndFile {
	String uDetail_id;
	String used_id;
	String user_email;
	String buyer_email;
	String category_id;
	String used_title;
	String used_date;
	int used_price;
	String used_content;
	String used_quality;
	String used_status;
	String uloc_id;
	String sloc_id;
	String file_origin;
	String file_saved;
	String uDetail_status;
	String uDetail_Date;
}

