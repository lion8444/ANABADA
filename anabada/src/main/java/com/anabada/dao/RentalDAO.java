package com.anabada.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.anabada.domain.File;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.Rental;
import com.anabada.domain.UserDTO;

@Mapper
public interface RentalDAO {

	int purchase(Rental_detail rd);

	Rental findOneRental(String rental_id);

	UserDTO findUser(String user_email);

	int usemoney(HashMap<String, Object> map);
	
	Rental_detail findOneRentaldetail(String rental_id);
	
	List<UserDTO> allUser();
	
	/**
	 * 렌탈 글 목록 출력*/
	public ArrayList<Rental> rentalBoard(HashMap<String, String> map, RowBounds r);
	public ArrayList<File> fileList();
	
	/**
	 * 글 갯수*/
	public int total(HashMap<String, String> map);
	
	/**
	 * 렌탈 글 저장*/
	public int rentalWrite(Rental rental);
	
	/**
	 * 렌탈 글 사진저장*/
	int insertFile(File file);
	
	/**
	 * 렌탈 글 한개 출력*/
	public Rental rentalBoardRead(String rental_id);
	
	/**
	 * 렌탈 글 삭제*/
	public int rentalBoardDelete(Rental rental);
	
	/**
	 * 중고거래 파는 글 수정*/
	public int rentalBoardUpdate(Rental rental);
	
	
	
	ArrayList<Rental> recommendList(HashMap<String, String> map, RowBounds rb);

	ArrayList<String> gettitle();

	ArrayList<File> fileListAll(String rental_id);
}
