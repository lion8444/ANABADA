package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Used_buy {
	String uBuy_id;
	String user_email;
	String category_id;
	String uBuy_title;
	String uBuy_content;
	String uBuy_status;
	String user_nick;
}
