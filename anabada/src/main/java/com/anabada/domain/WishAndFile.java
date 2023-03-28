package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishAndFile {
	String wish_id;
	String user_email;
	String board_status;
	String board_no;
	String file_origin;
	String file_saved;
	String used_title;
	String used_date;
	String used_status;
	String rental_title;
	String rental_date;
	String rental_status;
	String auction_title;
	String auction_date;
	String auction_status;
	String used_id;
	String rental_id;
	String auction_id;
}
