package com.anabada.service.rent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anabada.dao.RentalDAO;
import com.anabada.domain.File;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.UserDTO;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class RentServiceImpl implements RentService {
	@Autowired
	private RentalDAO dao;

	/**
	 * Rental_detail 내용 데이터베이스에 저장
	 */
	@Override
	public int purchase(Rental_detail rd) {
		if(rd.getRDetail_person() == null || rd.getRDetail_person().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_sDate() == null || rd.getRDetail_sDate().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_eDate() == null || rd.getRDetail_eDate().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_price() < 0) {
			return 0;
		}
		if(rd.getRDetail_phone() == null || rd.getRDetail_phone().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_post() == null || rd.getRDetail_post().length() ==0) {
			return 0;
		}
		if(rd.getRDetail_addr1() == null || rd.getRDetail_addr1().length() ==0) {
			return 0;
		}
		
		int i = dao.purchase(rd);
		return i;
	}
	
	/**
	 * rental_id에 맞는 렌탈 한개 행 불러오기
	 */
	@Override
	public Rental findOneRental(String rental_id) {
		Rental rental = dao.findOneRental(rental_id);
		return rental;
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
		public String rentalWrite(Rental rental) {
			int res = dao.rentalWrite(rental);
			return rental.getRental_id();
		}
		
	@Override
	public int insertFile(File file) {
		int n = dao.insertFile(file);
		return n;
	}
		
	//파는 글 출력
		@Override
		public ArrayList<Rental> rentalBoard(int start, int count, String type, String searchWord, String check, String email) {
			//검색 대상과 검색어
			HashMap<String, String> map = new HashMap<>();
			if(type != null) {
			if(type.equals("제목")) {
			map.put("type", "rental_title");
			}
			if(type.equals("본문")) {
			map.put("type", "rental_content");
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
			}
			map.put("searchWord", searchWord);
			map.put("check", check);
			//조회 결과 중 위치, 개수 지정
			RowBounds rb = new RowBounds(start, count);
			ArrayList<Rental>rentalList = dao.rentalBoard(map, rb);
			
			return rentalList;
		}

		//사진 출력
		@Override
		public ArrayList<File> fileList() {
			ArrayList<File> list = dao.fileList();
			return list;
		}
		
		//파는 글 하나 출력
		@Override
		public Rental rentalBoardRead(String rental_id) {
			Rental rental = dao.rentalBoardRead(rental_id);
			return rental;
		}
		
		//파는 글 하나 삭제
		@Override
		public int rentalBoardDelete(Rental rental) {
			int n = dao.rentalBoardDelete(rental);
			return n;
		}
		
		//파는 글 수정
		@Override
		public String rentalBoardUpdate(Rental rental) {
			int n = dao.rentalBoardUpdate(rental);
			Rental one = dao.rentalBoardRead(rental.getRental_id());
			String user_id = one.getRental_id();
			return user_id;
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

//		@Override
//		public ArrayList<Rental> recommendList(int startRecord, int countPerPage, String type, String searchWord) {
//			//검색 대상과 검색어
//			
//	        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//	        ArrayList<String> miningList = dao.gettitle();
//	        
//	        for (String mining : miningList) {
//	        KomoranResult analyzeResultList = komoran.analyze(mining);
//	        	
//	        System.out.println(analyzeResultList.getPlainText());
//
//	        List<Token> tokenList = analyzeResultList.getTokenList();
//	        for (Token token : tokenList) {
//	            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
//	        }        
//	        
//	        }
//	        
//	        
//	        
//			HashMap<String, String> map = new HashMap<>();
//			map.put("type", type);
//			map.put("searchWord", searchWord);
//			//조회 결과 중 위치, 개수 지정
//			RowBounds rb = new RowBounds(startRecord, countPerPage);
//			ArrayList<Rental> recommendList = dao.recommendList(map, rb);
//			
//			return recommendList;
//		}

		@Override
		public ArrayList<File> fileListAll(String rental_id) {
			ArrayList<File> list = dao.fileListAll(rental_id);
			return list;
		}

		@Override
		public ArrayList<Rental> recommendList(int startRecord, int countPerPage, String type, String searchWord) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList<File> fileListByid(String rental_id) {
			ArrayList <File> fileList = dao.fileListByid(rental_id);
			return fileList;
		}

		@Override
		public int addtemp(Rental formdata) {
			int i = dao.addtemp(formdata);
			return i;
		}

		public void rentalStart() {
			
		}
}
