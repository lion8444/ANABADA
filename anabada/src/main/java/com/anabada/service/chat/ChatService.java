package com.anabada.service.chat;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anabada.domain.UserDTO;
import com.anabada.domain.chat.ChatMessage;
import com.anabada.domain.chat.ChatRoom;

@Service
public interface ChatService {

    /**
     * 방 번호를 선택하는 메소드
     * @param roomId
     * @return
     */
    ChatRoom selectChatRoom(String roomId);
 
    /**
     * 채팅 메세지 DB 저장
     * @param chatMessage
     * @return 
     */
    int insertMessage(ChatMessage chatMessage);
 
    /**
     * 메세지 내용 리스트 출력
     * @param roomId
     * @return
     */
    List<ChatMessage> messageList(String roomId);
 
    /**
     * 채팅 방 DB 저장
     * @param room
     * @return
     */
    int createChat(ChatRoom room);
 
    /**
     * DB에 채팅 방이 있는지 확인
     * @param room
     * @return
     */
    ChatRoom searchChatRoom(ChatRoom room);
 
    /**
     * 채팅 방 리스트 출력
     * @param userEmail
     * @return
     */
    List<ChatRoom> chatRoomList(String userEmail);
 
    /**
     * 채팅 읽지 않은 메세지 수 출력
     * @param message
     * @return
     */
    int selectUnReadCount(ChatMessage message);
 
    /**
     * 읽은 메세지 숫자 0으로 바꾸기
     * @param message
     * @return
     */
    int updateCount(ChatMessage message);

    /**
     * 상세 게시글 판매자 정보 
     * 여기선 편의상 ChatService 이용
     * @param target 판매자 아이디
     * @return
     */
    UserDTO getTargetSeller(String target);

    /**
     * 만들어진 채팅방 확인
     * @param str
     * @return
     */
    Boolean findBoard(String str);
    
}