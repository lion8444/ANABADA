package com.anabada.service.auction;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.AuctionDAO;
import com.anabada.domain.Auction;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.UserDTO;

@Service
public class AuctionServiceImpl implements AuctionService {
	
	@Autowired
	private AuctionDAO dao;
	
	/**
	 * auction_id에 맞는 경매 한개 행 불러오기
	 */
	@Override
	public Auction findOneAuction(String auction_id) {
		Auction auction = dao.findOneAuction(auction_id);
		return auction;
	}

	/**
	 * user_email로 user 한개 행 불러오기
	 */
	@Override
	public UserDTO findUser(String user_email) {
		UserDTO user = dao.findUser(user_email);
		return user;
	}

	/**
	 * auction_id로 경매 세부사항 중 입찰금액 가장 큰 한개 행 불러오기
	 */
	@Override
	public Auction_detail findOneAuctiondetail(String auction_id) {
		Auction_detail auction_detail= dao.findOneAuctiondetail(auction_id);
		return auction_detail;
	}


	/**
	 * auction_detail 내용 데이터베이스에 저장
	 */
	@Override
	public int bid(Auction_detail auction_detail) {
		if(auction_detail.getAuction_id() == null || auction_detail.getAuction_id().length() ==0) {
			return 0;
		}
		if(auction_detail.getADetail_person() == null || auction_detail.getADetail_person().length() ==0) {
			return 0;
		}
		if(auction_detail.getADetail_price() < 0) {
			return 0;
		}
		if(auction_detail.getADetail_phone() == null || auction_detail.getADetail_phone().length() ==0) {
			return 0;
		}
		if(auction_detail.getADetail_post() == null || auction_detail.getADetail_post().length() ==0) {
			return 0;
		}
		if(auction_detail.getADetail_addr1() == null || auction_detail.getADetail_addr1().length() ==0) {
			return 0;
		}
		int k = dao.bid(auction_detail);
		return k;
	}

	/**
	 * 경매 참여금액에 맞게 포인트 차감
	 */
	@Override
	public int usemoney(String user_email, int user_account) {
		UserDTO user = dao.findUser(user_email);
		int account = user.getUser_account() - user_account;
		if (account < 0) {
			return 0;
		}
		
		HashMap<String, Object> map = new HashMap<>();
		map.put("user_email", user_email);
		map.put("account", account);
		
		int i = dao.usemoney(map);
		return i;
	}

}
