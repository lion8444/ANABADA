package com.anabada.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

@Mapper
public interface UsedDAO {

	UserDTO findUser(String user_email);

	Used findOneUsed(String used_id);

	Used_detail findOneUseddetail(String used_id);

	int purchase(Used_detail used_detail);

	int usemoney(HashMap<String, Object> map);
	
	List<UserDTO> allUser();
	
	/**
	 * 중고거래 파는 글 저장*/
	public int usedSellWrite(Used used);
	
	/**
	 * 중고거래 파는 글 사진저장*/
	int insertFile(File file);
	
	/**
	 * 중고거래 파는 글 목록 출력*/
	public ArrayList<Used>usedSellBoard(HashMap<String, String> map, RowBounds rb);
	public ArrayList<File> fileList();
	
	/**
	 * 중고거래 파는 글 한개 출력*/
	public Used usedSellBoardRead(String used_id);
	
	/**
	 * 파는 글 삭제*/
	public int usedSellBoardDelete(Used used);
	
	/**
	 * 중고거래 파는 글 수정*/
	public int usedSellBoardUpdate(Used used);
	
	/**
	 * 글 갯수*/
	public int total(HashMap<String, String> map);
	
	/**
	 * 중고거래 사는 글 저장*/
	public int usedBuyWrite(Used_buy used_buy);
	
	/**
	 * 중고거래 사는 글 목록 출력*/
	public ArrayList<Used_buy> usedBuyBoard();
	
	/**
	 * 중고거래 사는 글 한개 출력*/
	public Used_buy usedBuyBoardRead(String num);
	
	/**
	 * 중고거래 사는 글 삭제*/
	public int usedBuyBoardDelete(Used_buy used_buy);
	
	/**
	 * 중고거래 사는 글 수정*/
	public int usedBuyBoardUpdate(Used_buy used_buy);
	
	
	
	ArrayList<Used> recommendList(HashMap<String, String> map, RowBounds rb);

	ArrayList<String> gettitle();

	ArrayList<File> fileListAll(String used_id);

	ArrayList<File> fileListByid(String id);

	int addtemp(Used formdata);

	void addsearchWord(HashMap<String, Object> save);

	void deleteWord();

	int addmoney(HashMap<String, Object> map);

	void purchaseupdate(String used_id);

	ArrayList<Category> allcategory();

	ArrayList<Category> subcategory(String main);

	ArrayList<Category> maincategory();

	void uTradeinsert(HashMap<String, Object> map);

	
}
