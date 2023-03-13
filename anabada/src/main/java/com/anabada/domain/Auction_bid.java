package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auction_bid {
	String aBid_id;
	String auction_id;
	String user_email;
	int bid_price;
	String bid_date;
}
