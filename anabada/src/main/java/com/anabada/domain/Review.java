package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
	String review_id;
	String user_email;
	String review_person;
	String board_status;
	String board_no;
	String review_moment;
	int review_star;
	String review_comment;
}
