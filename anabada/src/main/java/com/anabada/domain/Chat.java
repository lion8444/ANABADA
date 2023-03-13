package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
	String chat_id;
	String chatRoom_id;
	String user_email;
	String chat_contents;
	String chat_date;
}
