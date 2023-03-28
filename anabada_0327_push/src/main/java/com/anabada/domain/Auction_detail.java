package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auction_detail {
	String aDetail_id;
	String auction_id;
	String user_email;
	String chat_id;
	String aDetail_addr;
	int aDetail_price;
	String aDetail_status;
}
