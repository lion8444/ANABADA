package com.anabada.service.auction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.AuctionDAO;
import com.anabada.domain.Auction;
import com.anabada.domain.Auction_detail;
import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.UserDTO;
import com.anabada.util.PageNavigator;

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
	
	//렌탈 글 저장
		@Override
		public String auctionWrite(Auction auction) {
			int res = dao.auctionWrite(auction);
			return auction.getAuction_id();
		}
			
		@Override
		public int insertFile(File file) {
			int n = dao.insertFile(file);
			return n;
		}
			
		//파는 글 출력
		@Override
		public ArrayList<Auction> auctionBoard(int start, int count, String type, String searchWord, String check, String email) {
			//검색 대상과 검색어
			HashMap<String, String> map = new HashMap<>();
			if(type != null) {
			if(type.equals("제목")) {
			map.put("type", "auction_title");
			}
			if(type.equals("본문")) {
			map.put("type", "auction_content");
			}
			if(type.equals("작성자")) {
			map.put("type", "user_nick");
			}
			if(type.equals("전체")) {
			map.put("type", "all");
			}
			}
			if(searchWord != null && email != null) {
				HashMap<String, Object> save = new HashMap<>();
				save.put("searchWord", searchWord);
				save.put("email", email);
				dao.addsearchWord(save);
				dao.deleteWord();
			}
			map.put("searchWord", searchWord);
			map.put("check", check);
			//조회 결과 중 위치, 개수 지정
			RowBounds rb = new RowBounds(start, count);
			ArrayList<Auction>auctionList = dao.auctionBoard(map, rb);
			
			return auctionList;
			}

			//사진 출력
			@Override
			public ArrayList<File> fileList() {
				ArrayList<File> list = dao.fileList();
				return list;
			}
			
			//파는 글 하나 출력
			@Override
			public Auction auctionBoardRead(String auction_id) {
				Auction auction = dao.auctionBoardRead(auction_id);
				return auction;
			}
			
			//파는 글 하나 삭제
			@Override
			public int auctionBoardDelete(Auction auction) {
				int n = dao.auctionBoardDelete(auction);
				return n;
			}
			
			//파는 글 수정
			@Override
			public String auctionBoardUpdate(Auction auction) {
				int n = dao.auctionBoardUpdate(auction);
				Auction one = dao.auctionBoardRead(auction.getAuction_id());
				String auction_id = one.getAuction_id();
				return auction_id;
			}

			//검색
			@Override
			public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
					String searchWord, String check) {
				HashMap<String, String> map = new HashMap<>();
				map.put("type", type);
				map.put("searchWord", searchWord);
				//검색 결과 개수
				int t = dao.total(map);
				//페이지 이동 링크수, 페이지당 글수, 현재페이지, 전체 글수를 전달하여 객체 생성
				PageNavigator navi = new PageNavigator(pagePerGroup, countPerPage, page, t);
				
				return navi;
			}

//			@Override
//			public ArrayList<Auction> recommendList(int startRecord, int countPerPage, String type, String searchWord) {
//				//검색 대상과 검색어
//				
//		        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//		        ArrayList<String> miningList = dao.gettitle();
//		        
//		        for (String mining : miningList) {
//		        KomoranResult analyzeResultList = komoran.analyze(mining);
//		        	
//		        System.out.println(analyzeResultList.getPlainText());
//
//		        List<Token> tokenList = analyzeResultList.getTokenList();
//		        for (Token token : tokenList) {
//		            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
//		        }        
//		        
//		        }
//		        
//		        
//		        
//				HashMap<String, String> map = new HashMap<>();
//				map.put("type", type);
//				map.put("searchWord", searchWord);
//				//조회 결과 중 위치, 개수 지정
//				RowBounds rb = new RowBounds(startRecord, countPerPage);
//				ArrayList<Auction> recommendList = dao.recommendList(map, rb);
//				
//				return recommendList;
//			}

			@Override
			public ArrayList<File> fileListAll(String auction_id) {
				ArrayList<File> list = dao.fileListAll(auction_id);
				return list;
			}

			@Override
			public ArrayList<Auction> recommendList(int startRecord, int countPerPage, String type, String searchWord) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ArrayList<File> fileListByid(String auction_id) {
				ArrayList <File> fileList = dao.fileListByid(auction_id);
				return fileList;
			}

			@Override
			public int addtemp(Auction formdata) {
				int i = dao.addtemp(formdata);
				return i;
			}

			@Override
			public int addmoney(String email, String money) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("email", email);
				map.put("money", money);
				int result = dao.addmoney(map);
				return result;
			}

			@Override
			public ArrayList<Category> maincategory() {
				ArrayList<Category> category_main = dao.maincategory();
				for(int i = 1; i < category_main.size(); ++i) {
					String now = category_main.get(i-1).getCategory_main();
					if(category_main.get(i).getCategory_main().equals(now)){
						category_main.remove(i);
						--i;
					}
				}			
				return category_main;
			}

			@Override
			public ArrayList<Category> subcategory(String main) {
				ArrayList<Category> category_sub = dao.subcategory(main);
				for(int i = 0; i < category_sub.size(); ++i) {
					if(category_sub.get(i).getCategory_sub().equals("")){
						category_sub.remove(i);
						--i;
					}
				}
				return category_sub;
			}			
}
