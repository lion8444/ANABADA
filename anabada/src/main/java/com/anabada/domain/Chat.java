package com.anabada.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
	String chat_id;				// chat id
	String chatRoom_id;			// room id
	public enum MessageType {	// ENTER 입장 TALK 채팅
        ENTER, TALK
    }
    private MessageType type; 	// 메시지 타입
	String user_email;			// sender 보낸 사람
	String chat_contents;		// message
	String chat_date;			// 보낸 시간
	
}
