package com.anabada.service.auction;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.anabada.domain.Auction;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.UserDTO;
import com.anabada.util.PageNavigator;

@Service
public interface AuctionService {

	Auction findOneAuction(String auction_id);

	UserDTO findUser(String user_email);

	Auction_detail findOneAuctiondetail(String auction_id);

	int bid(Auction_detail auction_detail);

	int usemoney(String user_email, int user_account);
	
	ArrayList<Auction> recommendList(int startRecord, int countPerPage, String type, String searchWord);
	
	//추가된 사진 처리
	ArrayList<File> fileListAll(String auction_id);
	
	//옥션 글 목록 출력
	public ArrayList<Auction>auctionBoard(int start, int count, String type, String searchWord, String check, String email);
	public ArrayList<File> fileList();
	
	//페이지 정보 객체 생성
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord, String check);
	
	//옥션 글 저장
	public String auctionWrite(Auction auction);
		
	//옥션 글 목록 한개 출력
	public Auction auctionBoardRead(String auction_id);
	
	//옥션 글 삭제
	public int auctionBoardDelete(Auction auction);
	
	//사진 저장
	public int insertFile(File file);
	
	//옥션 글 수정
	public String auctionBoardUpdate(Auction auction);

	ArrayList<File> fileListByid(String auction_id);

	int addtemp(Auction formdata);

	int addmoney(String email, String money);

	ArrayList<Category> maincategory();

	ArrayList<Category> subcategory(String main);

	ArrayList<Auction_detail> findAllAuctionDetail();
}
