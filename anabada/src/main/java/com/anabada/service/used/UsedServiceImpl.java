package com.anabada.service.used;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.dao.UsedDAO;
import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.RentalAndFile;
import com.anabada.domain.User_character;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;
@Transactional
@Slf4j
@Service
public class UsedServiceImpl implements UsedService {

	@Autowired
	private UsedDAO dao;
	

	/**
	 * user_email로 user 한개 행 불러오기
	 */
	@Override
	public UserDTO findUser(String user_email) {
		UserDTO user = dao.findUser(user_email);
		return user;
	}


	@Override
	public Used findOneUsed(String used_id) {
		Used used = dao.findOneUsed(used_id);
		return used;
	}


	@Override
	public Used_detail findOneUseddetail(String used_id) {
		Used_detail used_detail= dao.findOneUseddetail(used_id);
		return used_detail;
	}


	@Override
	public int purchase(Used_detail used_detail) {
		if(used_detail.getUsed_id() == null || used_detail.getUsed_id().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_person() == null || used_detail.getUDetail_person().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_price() < 0) {
			return 0;
		}
		if(used_detail.getUDetail_phone() == null || used_detail.getUDetail_phone().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_post() == null || used_detail.getUDetail_post().length() ==0) {
			return 0;
		}
		if(used_detail.getUDetail_addr1() == null || used_detail.getUDetail_addr1().length() ==0) {
			return 0;
		}
		
		int k = dao.purchase(used_detail);
		if (k == 1) {
			Used_detail d_id = dao.findOneUseddetail(used_detail.getUsed_id());
			dao.purchaseupdate(used_detail.getUsed_id());
			HashMap<String, Object> map = new HashMap<>();
			map.put("used_id", used_detail.getUsed_id());
			map.put("uDetail_id", d_id.getUDetail_id());
			dao.uTradeinsert(map);
			Used used = dao.usedSellBoardRead(used_detail.getUsed_id());
			
			int upb = dao.expup(used_detail.getUser_email());
			int upu = dao.expup(used.getUser_email());
			
			User_character buyer_character = dao.finduserchar(used_detail.getUser_email());
			if(buyer_character.getChar_exp() == 100) {
				int lresult = dao.levelup(buyer_character.getUser_email());
			}
			User_character user_character = dao.finduserchar(used.getUser_email());
			if(user_character.getChar_exp() == 100) {
				int lresult = dao.levelup(user_character.getUser_email());
			}
		}
		
		
		return k;
	}


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
	
	//파는 글 저장
	@Override
	public String usedSellWrite(Used used,  MultipartFile uploadOne) {
		//log.debug("중고 거래 게시판 아이디 : {}",used.getUsed_id());
		if(used.getUsed_title() == null || used.getUsed_title().length() ==0) {
			return "0";
		}

		if(used.getUsed_price() < 0) {
			return "0";
		}
		if(used.getUsed_content() == null || used.getUsed_content().length() <= 50) {
			return "0";
		}
		if(uploadOne.isEmpty()) {
			return "0";
		}		
		
		int res = dao.usedSellWrite(used);
		return used.getUsed_id();
	}
	
	//파는 글 출력
	@Override
	public ArrayList<Used> usedSellBoard(int start, int count, String type, String searchWord, String check, String email) {
		//검색 대상과 검색어
				HashMap<String, String> map = new HashMap<>();
				if(type != null) {
				if(type.equals("제목")) {
				map.put("type", "used_title");
				}
				if(type.equals("본문")) {
				map.put("type", "used_content");
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
	
//	@Override
//	public int usedSellBoardUpdate(Used used) {
//		int result = dao.usedSellBoardUpdate(used);
//		return result;
//	}
	
	//검색
	@Override
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type,
			String searchWord, String check) {
		HashMap<String, String> map = new HashMap<>();
		map.put("type", type);
		map.put("searchWord", searchWord);
		map.put("check", check);
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


//	@Override
//	public ArrayList<Used> recommendList(int startRecord, int countPerPage, String type, String searchWord) {
//		//검색 대상과 검색어
//		
//        Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
//        ArrayList<String> miningList = dao.gettitle();
//        
//        for (String mining : miningList) {
//        KomoranResult analyzeResultList = komoran.analyze(mining);
//        	
//        System.out.println(analyzeResultList.getPlainText());
//
//        List<Token> tokenList = analyzeResultList.getTokenList();
//        for (Token token : tokenList) {
//            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
//        }        
//        
//        }
//        
//        
//        
//		HashMap<String, String> map = new HashMap<>();
//		map.put("type", type);
//		map.put("searchWord", searchWord);
//		//조회 결과 중 위치, 개수 지정
//		RowBounds rb = new RowBounds(startRecord, countPerPage);
//		ArrayList<Used> recommendList = dao.recommendList(map, rb);
//		
//		return recommendList;
//	}
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


	@Override
	public ArrayList<File> fileListAll(String used_id) {
		ArrayList<File> list = dao.fileListAll(used_id);
		return list;
	}

	@Override
	public ArrayList<Used> recommendList(int startRecord, int countPerPage, String type, String searchWord) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ArrayList<File> fileListByid(String used_id) {
		ArrayList <File> fileList = dao.fileListByid(used_id);
		return fileList;
	}


	@Override
	public int addtemp(Used formdata) {
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
