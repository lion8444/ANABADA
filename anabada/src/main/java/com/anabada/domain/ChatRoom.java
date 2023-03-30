package com.anabada.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import com.anabada.service.chat.ChatService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
	String chatRoom_id;
	String chatRoom_name;
	private Set<WebSocketSession> sessions = new HashSet<>();
	String user_email;
	String chatRoom_date;
	
	@Builder
	public ChatRoom(String chatRoom_id, String chatRoom_name) {
		this.chatRoom_id = chatRoom_id;
		this.chatRoom_name = chatRoom_name;
	}
	
	public void handleActions(WebSocketSession session, Chat chat, ChatService chat_service) {
		if (chat.getType().equals(Chat.MessageType.ENTER)) {
			sessions.add(session);
			chat.setChat_contents(chat.getUser_email() + "님이 입장하였습니다.");
		}
		sendMessage(chat, chat_service);
	}
	
	public <T> void sendMessage(T message, ChatService chat_service) {
		sessions.parallelStream().forEach(session -> chat_service.sendMessage(session, message));
	}
}
