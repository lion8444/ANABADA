package com.anabada.domain.chat;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String roomId;            // 방 번호
    private String messageId;        // 메세지 번호
    private String message;            // 메세지 내용
    private String sender;
    private int unReadCount;        // 안 읽은 메세지 수
    private int sessionCount;       // 현재 세션 수
    private String chattime;

    public ChatMessage(String roomId, String messageId, String message, String sender, int unReadCount,
            int sessionCount) {
        super();
        this.roomId = roomId;
        this.messageId = UUID.randomUUID().toString();
        this.message = message;
        this.sender = sender;
        this.unReadCount = unReadCount;
        this.sessionCount = sessionCount;
    }
}
