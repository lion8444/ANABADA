package com.anabada.controller.chat;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anabada.domain.ChatRoom;
import com.anabada.service.chat.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ChatRoom createRoom(
            ChatRoom room
            , String roomName
            , HttpServletResponse response
            , @RequestParam String name
            , @AuthenticationPrincipal UserDetails user) {

        Cookie cookie = new Cookie("chatName", roomName);
        cookie.setDomain("localhost");
        cookie.setPath("/createChatName");
        // 100년간 저장
        cookie.setMaxAge(60 * 60 * 24 * 365 * 100);
        cookie.setSecure(true);
        response.addCookie(cookie);

        room.setChatRoom_name(roomName);
        room.setUser_email(user.getUsername());
        
        return chatService.createRoom(room, roomName);
    }

    @GetMapping
    public List<ChatRoom> findAllRoom() {
        return chatService.findAllRoom();
    }
}