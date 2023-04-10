package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionAndFile {
	String auction_id;
	String user_email;
	String buyer_email;
	String category_id;
	String auction_title;
	String auction_date;
	int auction_price;
	String auction_content;
	String auction_quality;
	String auction_status;
	String auction_finish;
	String file_origin;
	String file_saved;
	String aDetail_status;
	String aDetail_Date;
}
