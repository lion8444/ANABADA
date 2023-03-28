package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry {
	String inq_id;
	String user_email;
	String inq_category;
	String inq_title;
	String inq_content;
	String inq_answer;
	String inq_status;
	String inq_date;
}
