package com.anabada.domain.chat;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    private String roomId;
    private String name;
    private String roomdate;
    private String opener;
    private String target;
    private String board_no;

    private int unReadCount;    // 안 읽은 메세지 수

    
    public ChatRoom(String name, String opener, String target, int unReadCount) {
        super();
        this.roomId = UUID.randomUUID().toString();
        this.name = name;
        this.opener = opener;
        this.target = target;
        this.unReadCount = unReadCount;
    }

}