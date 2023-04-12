package com.anabada.service.wish;

import org.springframework.stereotype.Service;

import com.anabada.domain.Wish;

/**
 * 위시리스트 관련 기능 서비스
 * @author user
 *
 */
@Service
public interface WishService {

	public int insertWish(String boardno, String email);

	public int deleteWish(String boardno, String email);
	
	public Wish selectWish(String board_no, String username);

}
