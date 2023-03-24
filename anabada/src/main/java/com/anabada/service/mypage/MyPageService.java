package com.anabada.service.mypage;

import java.util.List;

import com.anabada.domain.AuctionAndFile;
import com.anabada.domain.CharacterDTO;
import com.anabada.domain.File;
import com.anabada.domain.Inquiry;
import com.anabada.domain.RentalAndFile;
import com.anabada.domain.Report;
import com.anabada.domain.Used;
import com.anabada.domain.UsedAndFile;
import com.anabada.domain.UserDTO;
import com.anabada.domain.WishAndFile;

public interface MyPageService {

	/**
	 * 이메일로 유저의 정보 검색
	 * @param user_email 유저의 이메일
	 * @return user타입 객체
	 */
	public UserDTO selectUserById(String user_email);

	/**
	 * 이메일로 유저의 다마고치 정보 검색
	 * @param user_email 유저의 이메일
	 * @return 다마고치 정보 객체
	 */
	public CharacterDTO selectUserDama(String user_email);

	/**
	 * 이메일로 유저의 모든 문의 내역 검색
	 * @param user_email 유저의 이메일
	 * @return 유저가 했던 모든 문의내역 리스트
	 */
	public List<Inquiry> selectInquiryList(String user_email);

	/**
	 * 이메일로 유저의 모든 신고 내역 검색
	 * @param user_email 유저의 이메일
	 * @return 유저가 했던 모든 신고내역 리스트
	 */
	public List<Report> selectReportList(String user_email);

	/**
	 * 회원정보 수정 전 id와 pw 확인
	 * @param user_email 유저의 이메일
	 * @param user_pwd	유저의 비밀번호
	 * @return
	 */
	public int checkIdAndPw(String user_email, String user_pwd);
	
	/**
	 * 유저의 모든 중고 거래내역 리스트 검색 (중고 + 사진까지)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 모든 중고 거래내역 리스트
	 */
	public List<UsedAndFile> selectUsedListAllById(String user_email);

	/**
	 * 유저의 중고 거래 - 구매내역 리스트 검색 (중고 + 사진까지)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 중고 거래 - 구매내역 리스트
	 */
	public List<UsedAndFile> selectUsedBuyListById(String user_email);

	/**
	 * 유저의 중고 거래 - 판매내역 리스트 검색 (중고 + 사진까지)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 중고 거래 - 판매내역 리스트
	 */
	public List<UsedAndFile> selectUsedSellListById(String user_email);

	/**
	 * 나의 모든 렌탈 내역 리스트 (전체)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 렌탈내역 리스트 (전체)
	 */
	public List<RentalAndFile> selectRentalListAllById(String user_email);

	/**
	 * 나의 모든 렌탈 빌린 내역 리스트 (빌린 내역)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 렌탈내역 리스트 (빌린)
	 */
	public List<RentalAndFile> selectRentalListBuyById(String user_email);

	/**
	 * 나의 모든 렌탈 빌린 내역 리스트 (빌려준 내역)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 렌탈내역 리스트 (빌려준)
	 */
	public List<RentalAndFile> selectRentalListSellById(String user_email);

	/**
	 * 나의 모든 경매 내역 리스트 (전체)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 경매내역 리스트 (전체)
	 */
	public List<AuctionAndFile> selectAuctionListAllById(String user_email);

	/**
	 * 나의 경매 내역 포워딩 (경매 내역)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 경매내역 리스트 (경매 내역)
	 */
	public List<AuctionAndFile> selectAuctionListSellById(String user_email);

	/**
	 * 나의 경매 내역 포워딩 (입찰 내역)
	 * @param user_email 로그인한 유저의 이메일
	 * @return 경매내역 리스트 (입찰 내역)
	 */
	public List<AuctionAndFile> selectAuctionListBidById(String user_email);

	/**
	 * 찜 목록 - 중고 거래 찜 리스트
	 * @param user_email 로그인한 유저의 이메일
	 * @return 찜 목록 리스트 (중고 거래 찜)
	 */
	public List<WishAndFile> selectWishListUsedById(String user_email);

	/**
	 * 찜 목록 - 렌탈 거래 찜 리스트
	 * @param user_email 로그인한 유저의 이메일
	 * @return 찜 목록 리스트 (렌탈 거래 찜)
	 */
	public List<WishAndFile> selectWishListRentalById(String user_email);

	/**
	 * 찜 목록 - 경매 거래 찜 리스트
	 * @param user_email 로그인한 유저의 이메일
	 * @return 찜 목록 리스트 (경매 거래 찜)
	 */
	public List<WishAndFile> selectWishListAuctionById(String user_email);
	

}
