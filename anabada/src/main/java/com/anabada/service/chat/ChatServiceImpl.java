package com.anabada.service.chat;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.anabada.dao.ChatDAO;
import com.anabada.domain.UserDTO;
import com.anabada.domain.chat.ChatMessage;
import com.anabada.domain.chat.ChatRoom;
import com.anabada.domain.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    ChatDAO dao;

    @Override
    public ChatRoom selectChatRoom(String roomId) {
        ChatRoom chatRoom = dao.selectChatRoom(roomId);
        return chatRoom;
    }

    @Override
    public int insertMessage(ChatMessage chatMessage) {
        int res = dao.insertMessage(chatMessage);
        return res;
    }

    @Override
    public List<ChatMessage> messageList(String roomId) {
        List<ChatMessage> list = dao.messageList(roomId);
        return list;
    }

    @Override
    public int createChat(ChatRoom room) {
        room.setRoomId(UUID.randomUUID().toString());
        int res = dao.createChat(room);
        return res;
    }

    @Override
    public ChatRoom searchChatRoom(ChatRoom room) {
        ChatRoom res = dao.searchChatRoom(room);
        return res;
    }

    @Override
    public List<ChatRoom> chatRoomList(String userid) {
        List<ChatRoom> list = dao.chatRoomList(userid);
        return list;
    }

    @Override
    public int selectUnReadCount(ChatMessage message) {
        int res = dao.selectUnReadCount(message);
        return res;
    }

    @Override
    public int updateCount(ChatMessage message) {
        int res = dao.updateCount(message);
        return res;
    }

    @Override
    public UserDTO getTargetSeller(String target) {
        UserDTO user = dao.findTarget(target);
        return user;
    }

    @Override
    public Boolean findBoard(String board_no) {
        String str = board_no.substring(0, 4);
        boolean res = false;
        if(str.equals("USED")) {
            res = dao.findUsed(board_no);
        } else if(str.equals("RENT")) {
            res = dao.findRental(board_no);
        } else if(str.equals("AUCT")) {
            res = dao.findAuction(board_no);
        }
        log.debug("@ChatServiceImpl findBoard return : {}", res);
        return res;
    }
    
}