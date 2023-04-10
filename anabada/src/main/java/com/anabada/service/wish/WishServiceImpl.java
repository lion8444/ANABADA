package com.anabada.service.wish;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.WishDAO;
import com.anabada.domain.Wish;

/**
 * 위시리스트 기능 관련 서비스 임플
 * @author user
 *
 */
@Service
public class WishServiceImpl implements WishService {
	
	@Autowired
	WishDAO dao;

	@Override
	public int insertWish(String boardno, String email) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("board_no", boardno);
		map.put("user_email", email);
		
		if(boardno.contains("USED")) {
			map.put("board_status", "중고 거래");
		}
				
		if(boardno.contains("RENT")) {
			map.put("board_status", "렌탈 거래");
		}
		
		if(boardno.contains("AUCT")) {
			map.put("board_status", "경매 거래");
		}
		
		int result = dao.insertWish(map);
		
		return result;
	}

	@Override
	public int deleteWish(String boardno, String email) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("board_no", boardno);
		map.put("user_email", email);
		
		int result = dao.deleteWish(map);
		
		return result;
	}

	@Override
	public Wish selectWish(String board_no, String username) {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("board_no", board_no);
		map.put("user_email", username);
		
		Wish wish = dao.selectWish(map);
		return wish;
	}

}
