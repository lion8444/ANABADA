package com.anabada.service.auction;

import org.springframework.stereotype.Service;

import com.anabada.domain.Auction;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.UserDTO;

@Service
public interface AuctionService {
	Auction findOneAuction(String auction_id);

	UserDTO findUser(String user_email);

	Auction_detail findOneAuctiondetail(String auction_id);

	int bid(Auction_detail auction_detail);

	int usemoney(String user_email, int user_account);
}
