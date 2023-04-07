// package com.anabada.websocket;

// import java.util.logging.Logger;

// import org.springframework.stereotype.Component;
// import org.springframework.web.socket.TextMessage;
// import org.springframework.web.socket.WebSocketSession;
// import org.springframework.web.socket.handler.TextWebSocketHandler;

// import com.anabada.domain.Chat;
// import com.anabada.domain.chat.ChatRoom;
// // import com.anabada.domain.ChatRoom;
// import com.anabada.service.chat.ChatService;
// import com.fasterxml.jackson.databind.ObjectMapper;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Component
// public class WebSocketHandler extends TextWebSocketHandler {

// 	private ObjectMapper objectMapper;
// 	private ChatService chatService;

// //	private final static Logger LOG = Logger.getGlobal();

// 	@Override
// 	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
// 		String payload = message.getPayload();
// 		log.info("payload" + payload);
// //        TextMessage textMessage = new TextMessage("Welcome chatting server~ ^^");
// //        session.sendMessage(textMessage);
// 		Chat chat = objectMapper.readValue(payload, Chat.class);
// 		ChatRoom room = chatService.findRoomById(chat.getChatRoom_id());
// 		room.handleActions(session, chat, chatService);
// 	}
// }