package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Used {
	String used_id;
	String user_nick;
	String user_email;
	String category_id;
	String used_title;
	String used_date;
	Integer used_price;
	String used_content;
	String used_quality;
	String used_status;
	String uloc_id;
	String sloc_id;
}
