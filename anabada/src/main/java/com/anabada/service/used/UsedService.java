package com.anabada.service.used;

import java.util.ArrayList;

import com.anabada.domain.File;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;

import com.anabada.util.PageNavigator;

public interface UsedService {
	
	//파는 글 저장
	public String usedSellWrite(Used used);
	
	//파는 글 목록 출력
	public ArrayList<Used>usedSellBoard(int start, int count, String type, String searchWord);
	public ArrayList<File> fileList();
	
	//페이지 정보 객체 생성
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord);
	
	//파는 글 목록 한개 출력
	public Used usedSellBoardRead(String used_id);
	
	//파는 글 삭제
	public String usedSellBoardDelete(Used used);
	
	//사진 저장
	public int insertFile(File file);
	
	//사는 글 저장
	public int usedBuyWrite(Used_buy used_buy);
	
	//사는 글 목록 출력
	public ArrayList<Used_buy>usedBuyBoard();
	
	//사는 글 목록 한개 출력
	public Used_buy usedBuyBoardRead(String num);
	
	
	

	
}
