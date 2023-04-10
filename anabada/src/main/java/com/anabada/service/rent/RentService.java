package com.anabada.service.rent;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;

import com.anabada.domain.Rental;
import com.anabada.domain.UserDTO;
import com.anabada.util.PageNavigator;

@Service
public interface RentService {

	int purchase(Rental_detail rd);

	Rental findOneRental(String rental_id);

	UserDTO findUser(String user_email);

	int usemoney(String user_email, int user_account);
	
	ArrayList<Rental> recommendList(int startRecord, int countPerPage, String type, String searchWord);

	ArrayList<File> fileListAll(String rental_id);
	
	//렌트 글 목록 출력
	public ArrayList<Rental>rentalBoard(int start, int count, String type, String searchWord, String check, String email);
	public ArrayList<File> fileList();
	
	//페이지 정보 객체 생성
	public PageNavigator getPageNavigator(int pagePerGroup, int countPerPage, int page, String type, String searchWord, String check);
	
	//파는 글 저장
	public String rentalWrite(Rental rental);
		
	//파는 글 목록 한개 출력
	public Rental rentalBoardRead(String rental_id);
	
	//파는 글 삭제
	public int rentalBoardDelete(Rental rental);
	
	//사진 저장
	public int insertFile(File file);
	
	//파는 글 수정
	public String rentalBoardUpdate(Rental rental);

	ArrayList<File> fileListByid(String rental_id);

	int addtemp(Rental formdata);

	int addmoney(String email, String money);

	ArrayList<Category> maincategory();

	ArrayList<Category> subcategory(String main);
	
}
