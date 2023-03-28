package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	String report_id;
	String user_email;
	String report_reported;
	String report_category;
	String report_url;
	String report_comment;
	String report_answer;
	String report_status;
}
