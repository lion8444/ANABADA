package com.anabada.service.used;

import java.util.ArrayList;

import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;

import com.anabada.util.PageNavigator;

import java.util.ArrayList;

import com.anabada.domain.File;
import com.anabada.domain.Used;
import com.anabada.domain.Used_buy;

import com.anabada.util.PageNavigator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.domain.Used;
import com.anabada.domain.Used_detail;
import com.anabada.domain.UserDTO;

@Service
public interface UsedService {

	UserDTO findUser(String user_email);

	Used findOneUsed(String used_id);

	Used_detail findOneUseddetail(String used_id);

	int purchase(Used_detail used_detail);

	int usemoney(String user_email, int user_account);

	
	//파는 글 저장
	public String usedSellWrite(Used used, MultipartFile uploadOne);
	
	//파는 글 목록 출력
	public ArrayList<Used>usedSellBoard(int start, int count, String type, String searchWord, String check, String email);
	public ArrayList<File> fileList();
	
	//페이지 정보 객체 생성
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord, String check);
	
	//파는 글 목록 한개 출력
	public Used usedSellBoardRead(String used_id);
	
	//파는 글 삭제
	public int usedSellBoardDelete(Used used);
	
	//사진 저장
	public int insertFile(File file);
	
	//파는 글 수정
	public String usedSellBoardUpdate(Used used);
	
	
	//사는 글 저장
	public int usedBuyWrite(Used_buy used_buy);
	
	//사는 글 목록 출력
	public ArrayList<Used_buy>usedBuyBoard();
	
	//사는 글 목록 한개 출력
	public Used_buy usedBuyBoardRead(String num);
	
	//사는 글 삭제
	public int usedBuyBoardDelete(Used_buy used_buy);
	
	//사는 글 수정
	public int usedBuyBoardUpdate(Used_buy used_buy);
	

	ArrayList<Used> recommendList(int startRecord, int countPerPage, String type, String searchWord);

	ArrayList<File> fileListAll(String used_id);

	ArrayList<File> fileListByid(String used_id);

	int addtemp(Used formdata);

	int addmoney(String email, String money);

	ArrayList<Category> maincategory();

	ArrayList<Category> subcategory(String main);


	
}
