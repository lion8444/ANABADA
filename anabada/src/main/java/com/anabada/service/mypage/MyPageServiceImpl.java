package com.anabada.service.mypage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anabada.dao.MyPageDAO;
import com.anabada.domain.AuctionAndFile;
import com.anabada.domain.CharacterDTO;
import com.anabada.domain.Damagochi;
import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.RentalAndFile;
import com.anabada.domain.Report;
import com.anabada.domain.Used;
import com.anabada.domain.UsedAndFile;
import com.anabada.domain.UserDTO;
import com.anabada.domain.WishAndFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	MyPageDAO dao;
	
	@Autowired
	PasswordEncoder encoder;
	
	/**
	 * 유저 이메일로 유저 정보 검색
	 */
	@Override
	public UserDTO selectUserById(String user_email) {
		UserDTO UserDTO = dao.selectUserById(user_email);
		return UserDTO;
	}

	/**
	 * 유저 이메일로 유저의 다마고치 정보 검색
	 */
	@Override
	public List<CharacterDTO> selectUserDama(String user_email) {
		List<CharacterDTO> list = dao.selectUserDama(user_email);
		return list;
	}

	/**
	 * 유저 이메일로 유저의 모든 문의 내역 리스트 검색
	 */
	@Override
	public List<Inquiry> selectInquiryList(String user_email) {
		List<Inquiry> list = dao.selectInquiryList(user_email);
		return list;
	}

	/**
	 * 유저 이메일로 유저의 모든 신고 내역 리스트 검색
	 */
	@Override
	public List<Report> selectReportList(String user_email) {
		List<Report> list = dao.selectReportList(user_email);
		return list;
	}
	
	/**
	 * 회원정보 수정 전 id와 pw 확인
	 */
	@Override
	public int checkIdAndPw(String user_email, String user_pwd) {
		
		log.debug("이메일: {}, 비번: {}", user_email, user_pwd);
		
		String pw = user_pwd;
		UserDTO user = dao.selectUserById(user_email);
		String codedPw = user.getPassword();
		
		boolean match = encoder.matches(pw, codedPw);
		
		if(match) {
			return 1;
		} else {
			return 0;
		}	
	}

	// 유저의 모든 중고 거래내역 리스트 검색 (중고 + 사진까지)
	@Override
	public List<UsedAndFile> selectUsedListAllById(String user_email) {
		List<UsedAndFile> list = dao.selectUsedListAllById(user_email);
		return list;
	}

	// 유저의 중고 거래 - 구매내역 리스트 검색 (중고 + 사진까지)
	@Override
	public List<UsedAndFile> selectUsedBuyListById(String user_email) {
		List<UsedAndFile> list = dao.selectUsedBuyListById(user_email);
		return list;
	}

	// 유저의 중고 거래 - 판매내역 리스트 검색 (중고 + 사진까지)
	@Override
	public List<UsedAndFile> selectUsedSellListById(String user_email) {
		List<UsedAndFile> list = dao.selectUsedSellListById(user_email);
		return list;
	}

	// 나의 모든 렌탈 내역 리스트 (전체)
	@Override
	public List<RentalAndFile> selectRentalListAllById(String user_email) {
		List<RentalAndFile> list = dao.selectRentalListAllById(user_email);
		return list;
	}

	// 나의 모든 렌탈 빌린 내역 리스트 (빌린 내역)
	@Override
	public List<RentalAndFile> selectRentalListBuyById(String user_email) {
		List<RentalAndFile> list = dao.selectRentalListBuyById(user_email);
		return list;
	}

	// 나의 모든 렌탈 빌린 내역 리스트 (빌려준 내역)
	@Override
	public List<RentalAndFile> selectRentalListSellById(String user_email) {
		List<RentalAndFile> list = dao.selectRentalListSellById(user_email);
		return list;
	}

	// 나의 모든 경매 내역 리스트 (전체)
	@Override
	public List<AuctionAndFile> selectAuctionListAllById(String user_email) {
		List<AuctionAndFile> list = dao.selectAuctionListAllById(user_email);
		return list;
	}

	// 나의 경매 리스트 조회 (경매)
	@Override
	public List<AuctionAndFile> selectAuctionListSellById(String user_email) {
		List<AuctionAndFile> list = dao.selectAuctionListSellById(user_email);
		return list;
	}

	// 나의 경매 리스트 조회 (입찰)
	@Override
	public List<AuctionAndFile> selectAuctionListBidById(String user_email) {
		List<AuctionAndFile> list = dao.selectAuctionListBidById(user_email);
		return list;
	}
	
	/**
	 * 중고 거래 글 정보 및 디테일 정보 검색
	 */
	@Override
	public UsedAndFile selectUsedAndDetailInfo(String used_id) {
		UsedAndFile u = dao.selectUsedAndDetailInfo(used_id);
		return u;
	}

	/**
	 * 중고 거래 취소 하기
	 */
	@Override
	public int cancleUsedDetail(UsedAndFile usedAndDetailInfo) {
		int result = dao.cancleUsedDetail(usedAndDetailInfo);
		return result;
	}

	/**
	 * 렌탈 거래 글 및 렌탈 디테일 정보 검색
	 */
	@Override
	public RentalAndFile selectRentalAndDetailInfo(String rental_id) {
		RentalAndFile rentalAndFile = dao.selectRentalAndDetailInfo(rental_id);
		return rentalAndFile;
	}

	/**
	 * 렌탈 취소 하기
	 */
	@Override
	public int cancleRentalDetail(RentalAndFile rentalAndDetailInfo) {
		int result = dao.cancleRentalDetail(rentalAndDetailInfo);
		return result;
	}

	/**
	 * 경매 및 경매 디테일 정보 검색
	 */
	@Override
	public AuctionAndFile selectAuctionAndDetailInfo(AuctionAndFile auctionAndFile) {
		AuctionAndFile a = dao.selectAuctionAndDetailInfo(auctionAndFile);
		return a;
	}

	/**
	 * 경매 취소하기
	 */
	@Override
	public int cancleAuctionDetail(AuctionAndFile auctionAndDetailInfo) {
		int result = dao.cancleAuctionDetail(auctionAndDetailInfo);
		return result;
	}

	/**
	 * 입찰 취소하기
	 */
	@Override
	public int cancleBidDetail(AuctionAndFile auctionAndDetailInfo) {
		int result = dao.cancleBidDetail(auctionAndDetailInfo);
		return result;
	}

	/**
	 * 렌탈 - 반납 확인 처리
	 */
	@Override
	public int returncheck(RentalAndFile rentalAndDetailInfo) {
		int result = dao.returncheck(rentalAndDetailInfo);
		return result;
	}

	// 찜 목록 - 중고 거래 찜 리스트
	@Override
	public List<WishAndFile> selectWishListUsedById(String user_email) {
		List<WishAndFile> list = dao.selectWishListUsedById(user_email);
		return list;
	}
	
	// 찜 목록 - 렌탈 거래 찜 리스트
	@Override
	public List<WishAndFile> selectWishListRentalById(String user_email) {
		List<WishAndFile> list = dao.selectWishListRentalById(user_email);
		return list;
	}
	
	// 찜 목록 - 경매 거래 찜 리스트
	@Override
	public List<WishAndFile> selectWishListAuctionById(String user_email) {
		List<WishAndFile> list =  dao.selectWishListAuctionById(user_email);
		return list;
	}

	// 나의 다마고치 정보 검색
	@Override
	public Damagochi selectMyDamaInfoById(String user_email) {
		Damagochi dama = dao.selectMyDamaInfoById(user_email);
		return dama;
	}

	// 유저가 보유한 다마고치 리스트
	@Override
	public List<Damagochi> selectMyDamaListById(String user_email) {
		List<Damagochi> list = dao.selectMyDamaListById(user_email);
		return list;
	}
	
	
	
	
	
}
