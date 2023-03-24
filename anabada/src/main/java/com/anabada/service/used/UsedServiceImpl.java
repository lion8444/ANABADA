package com.anabada.service.used;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anabada.dao.UsedDAO;
import com.anabada.domain.File;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;
@Transactional
@Slf4j
@Service
public class UsedServiceImpl implements UsedService {
	
	@Autowired
	UsedDAO dao;
	
	//파는 글 저장
	@Override
	public String usedSellWrite(Used used) {
		int res = dao.usedSellWrite(used);
		//log.debug("중고 거래 게시판 아이디 : {}",used.getUsed_id());
		return used.getUsed_id();
	}
	
	//파는 글 출력
	@Override
	public ArrayList<Used> usedSellBoard(int start, int count, String type, String searchWord) {
		//검색 대상과 검색어
				HashMap<String, String> map = new HashMap<>();
				map.put("type", type);
				map.put("searchWord", searchWord);
				//조회 결과 중 위치, 개수 지정
				RowBounds rb = new RowBounds(start, count);
		ArrayList<Used>usedSellList = dao.usedSellBoard(map, rb);
		
		return usedSellList;
	}
	
	
	//파는 글 하나 출력
	@Override
	public Used usedSellBoardRead(String used_id) {
		Used used = dao.usedSellBoardRead(used_id);
		return used;
	}
	
	
	//파는 글 하나 삭제
	@Override
	public int usedSellBoardDelete(Used used) {
		int n = dao.usedSellBoardDelete(used);
		return n;
	}
	
	//파는 글 수정
	@Override
	public String usedSellBoardUpdate(Used used) {
		int n = dao.usedSellBoardUpdate(used);
		Used one = dao.usedSellBoardRead(used.getUsed_id());
		String user_id = one.getUsed_id();
		return user_id;
	}
	
	

	//검색
	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		//검색 결과 개수
		int t = dao.total(map);
		//페이지 이동 링크수, 페이지당 글수, 현재페이지, 전체 글수를 전달하여 객체 생성
		PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, t);
		
		return navi;
	}
	
	//사진 출력
	@Override
	public ArrayList<File> fileList() {
		ArrayList<File> list = dao.fileList();
		return list;
	}
	
	//사진 저장
	@Override
	public int insertFile(File file) {
		int n = dao.insertFile(file);
		return n;
	}
	

	//사는 글 저장
	@Override
	public int usedBuyWrite(Used_buy used_buy) {
		int n = dao.usedBuyWrite(used_buy);
		return n;
	}
	
	//사는 글 목록 출력
	@Override
	public ArrayList<Used_buy> usedBuyBoard() {
		ArrayList<Used_buy>bboardlist= dao.usedBuyBoard();
		return bboardlist;
	}
	
	//사는 글 한개 출력
	@Override
	public Used_buy usedBuyBoardRead(String num) {
		//log.debug("db : {}",dao.usedBuyBoardRead(num));
		Used_buy used_buy = dao.usedBuyBoardRead(num);
		return used_buy ;
	}

	//사는 글 삭제
	@Override
	public int usedBuyBoardDelete(Used_buy used_buy) {
		int n = dao.usedBuyBoardDelete(used_buy);
		return n;
	}
	
	//사는 글 수정
	@Override
	public int usedBuyBoardUpdate(Used_buy used_buy) {
		int i = dao.usedBuyBoardUpdate(used_buy);
		return i;
	}
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	

}
