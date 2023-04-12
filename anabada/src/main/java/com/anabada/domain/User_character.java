package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User_character {
	String uChar_id;
	String user_email;
	String char_id;
	int char_level;
	int char_exp;
	int char_selected;
}
