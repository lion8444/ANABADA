package com.anabada.controller.chat;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import com.anabada.domain.Auction;
import com.anabada.domain.Board;
import com.anabada.domain.Rental;
import com.anabada.domain.Used;
import com.anabada.domain.UserDTO;
import com.anabada.domain.chat.ChatMessage;
import com.anabada.domain.chat.ChatRoom;
import com.anabada.domain.user.User;
import com.anabada.handler.ChatHandler;
import com.anabada.service.chat.ChatService;
import com.anabada.service.login.LoginService;
import com.anabada.service.map.MapService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class RestChatController {
    
    @Autowired
    ChatHandler chatHandler;

    @Autowired
    ChatService cService;

    @Autowired
    LoginService service;

    @Autowired
    MapService mapService;
    
    // @Autowired
    // private ChatSession cSession;
    

    /**
     * 해당 채팅방의 채팅 메세지 불러오기
     * @param roomId RequestParameter roomId
     * @param user  AuthenticationPrincipal User 로그인한 유저
     * @param model
     * @return
     */
    @GetMapping("messageList")
    public List<ChatMessage> messageList(
            @RequestParam String roomId
            , @AuthenticationPrincipal UserDetails userDetails
            , Model model) {
        log.debug("@ChatController messageList(@RequestParam String roomId) : {}", roomId);
        log.debug("@ChatController messageList(@AuthenticationPrincipal UserDetails user) : {}", userDetails);
        // 안읽은 메세지의 숫자 0으로 바뀌기
        ChatMessage message = new ChatMessage();
        UserDTO user = service.findUser(userDetails.getUsername());
        message.setSender(user.getUser_nick());
        message.setRoomId(roomId);
        cService.updateCount(message);
                
        List<ChatMessage> mList = cService.messageList(roomId);
        log.debug("@ChatController messageList : {}", mList);
        return mList;
    }
    
    /**
     * 채팅 방이 없을 때 생성
     * @param room
     * @param user_nick
     * @param masterNickname
     * @return
     */
    @PostMapping("makeChatRoom")
    public String createChat(@AuthenticationPrincipal UserDetails userDetails
    ,String target
    ,String board_no){
        UserDTO user = service.findUser(userDetails.getUsername());
        // UserDTO targetUser = service.findUserNick(target);        // 상품 상세페이지 판매자 정보



        log.debug("target : {}, board : {}", target, board_no);
        Board board = mapService.findBoard(board_no);
        ChatRoom room = new ChatRoom();
        room.setRoomId(UUID.randomUUID().toString());
        room.setOpener(user.getUser_nick());
        room.setName(board.getTitle());
        room.setTarget(target);
        room.setBoard_no(board_no);
        


        ChatRoom exist  = cService.searchChatRoom(room);

        log.debug("exist : {}, room : {}", exist, room);

        // DB에 방이 없을 때
        if(exist == null) {
            log.debug("방이 없다!! : {}", room);
            int result = cService.createChat(room);
            if(result == 1) {
                log.debug("방 만들었다!!");
                return room.getRoomId();
            }else {
                return "error";
            }
        }
        // DB에 방이 있을 때
        else{
            log.debug("방이 있다!!");
            return exist.getRoomId();
        }
    }
    
    /**
     * 채팅 방 목록 불러오기
     * @param room
     * @param user_nick
     * @param response
     */
    @PostMapping("chatList")
    public List<ChatRoom> chatList(
        @AuthenticationPrincipal UserDetails userDetails
        , Model m
    ) {
        UserDTO user = service.findUser(userDetails.getUsername());
        List<ChatRoom> list = cService.chatRoomList(user.getUser_nick());
        ChatMessage message = new ChatMessage();
        for(int i = 0; i < list.size(); i++) {
            message.setRoomId(list.get(i).getRoomId());
            message.setSender(user.getUser_nick());
            int count = cService.selectUnReadCount(message);
            list.get(i).setUnReadCount(count);
        }
        m.addAttribute("sender", user.getUser_nick());
        return list;
    }
    
    @PostMapping("chatSession")
    public List<WebSocketSession> chatSession( 
        HttpServletResponse response
        ) {
        log.debug("{}",ChatHandler.uSessions);
        return ChatHandler.uSessions;
        // ArrayList<String> chatSessionList = cSession.getLoginUser();
        
        // response.setContentType("application/json; charset=utf-8");
 
        // Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        // gson.toJson(chatSessionList,response.getWriter());
    }
    
}