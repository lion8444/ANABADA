package com.anabada.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.anabada.domain.Auction;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.UserDTO;

@Mapper
public interface AuctionDAO {
	
	List<UserDTO> allUser();

	Auction findOneAuction(String auction_id);
	
	UserDTO findUser(String user_email);

	Auction_detail findOneAuctiondetail(String auction_id);

	int bid(Auction_detail auction_detail);

	int usemoney(HashMap<String, Object> map);
	
	/**
	 * 렌탈 글 목록 출력*/
	public ArrayList<Auction> auctionBoard(HashMap<String, String> map, RowBounds r);
	public ArrayList<File> fileList();
	
	/**
	 * 글 갯수*/
	public int total(HashMap<String, String> map);
	
	/**
	 * 렌탈 글 저장*/
	public int auctionWrite(Auction auction);
	
	/**
	 * 렌탈 글 사진저장*/
	int insertFile(File file);
	
	/**
	 * 렌탈 글 한개 출력*/
	public Auction auctionBoardRead(String auction_id);
	
	/**
	 * 렌탈 글 삭제*/
	public int auctionBoardDelete(Auction auction);
	
	/**
	 * 중고거래 파는 글 수정*/
	public int auctionBoardUpdate(Auction auction);
	
	ArrayList<Auction> recommendList(HashMap<String, String> map, RowBounds rb);

	ArrayList<String> gettitle();

	ArrayList<File> fileListAll(String auction_id);

	ArrayList<File> fileListByid(String auction_id);

	int addtemp(Auction formdata);

	void addsearchWord(HashMap<String, Object> save);

	void deleteWord();

	int addmoney(HashMap<String, Object> map);

	ArrayList<Category> allcategory();

	ArrayList<Category> subcategory(String main);

	ArrayList<Category> maincategory();
	
	ArrayList<Auction_detail> findAllAuctionDetail();

}
