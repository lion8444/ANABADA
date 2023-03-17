package com.anabada.dao;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.Auction;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.UserDTO;

@Mapper
public interface AuctionDAO {

	Auction findOneAuction(String auction_id);
	
	UserDTO findUser(String user_email);

	Auction_detail findOneAuctiondetail(String auction_id);

	int bid(Auction_detail auction_detail);

	int usemoney(HashMap<String, Object> map);

}
