package com.anabada.dao;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.ChatRoom;

@Mapper
public interface ChatDAO {

	int insertChatRoom(ChatRoom chatRoom);
	
}
