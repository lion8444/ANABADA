package com.anabada.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.anabada.domain.AuctionAndFile;
import com.anabada.domain.CharacterDTO;
import com.anabada.domain.Damagochi;
import com.anabada.domain.Inquiry;
import com.anabada.domain.RentalAndFile;
import com.anabada.domain.Report;
import com.anabada.domain.UsedAndFile;
import com.anabada.domain.UserDTO;
import com.anabada.domain.WishAndFile;

@Mapper
public interface MyPageDAO {

	/**
	 * 받아온 이메일로 유저의 정보 검색
	 * @param user_email 유저의 이메일(아이디)
	 * @return 유저의 정보 객체
	 */
	public UserDTO selectUserById(String user_email);

	/**
	 * 받아온 이메일로 유저의 다마고치 검색
	 * @param user_email 유저의 이메일(아이디)
	 * @return 유저의 다마고치 정보 객체
	 */
	public List<CharacterDTO> selectUserDama(String user_email);

	/**
	 * 받아온 이메일로 유저의 문의 내역 리스트 검색
	 * @param user_email 유저의 이메일
	 * @return 유저의 문의내역 리스트
	 */
	public List<Inquiry> selectInquiryList(String user_email);

	/**
	 * 받아온 이메일로 유저의 신고 내역 리스트 검색
	 * @param user_email 유저의 이메일
	 * @return 유저의 신고내역 리스트
	 */
	public List<Report> selectReportList(String user_email);

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
	 * 중고 거래 글 정보와 디테일 정보
	 * @param used_id 중고글 ID
	 * @return	UsedAndFile객체
	 */
	public UsedAndFile selectUsedAndDetailInfo(String used_id);

	/**
	 * 중고 거래 취소하기
	 * @param usedAndDetailInfo UsedAndFile타입의 객체 정보
	 * @return 취소 된 개수
	 */
	public int cancleUsedDetail(UsedAndFile usedAndDetailInfo);

	/**
	 * 렌탈 거래 글 정보와 디테일 정보
	 * @param rental_id 렌탈글 ID
	 * @return RentalAndFile객체
	 */
	public RentalAndFile selectRentalAndDetailInfo(String rental_id);

	/**
	 * 렌탈 취소하기 
	 * @param rentalAndDetailInfo RentalAndFile타입의 객체 정보 
	 * @return 취소된 개수
	 */
	public int cancleRentalDetail(RentalAndFile rentalAndDetailInfo);

	/**
	 * 경매 거래 글 정보와 디테일 정보
	 * @param auction_id 경매글 ID
	 * @return AuctionAndFile객체
	 */
	public AuctionAndFile selectAuctionAndDetailInfo(String auction_id);

	/**
	 * 경매 취소하기
	 * @param auctionAndDetailInfo AuctionAndFil타입의 객체 정보
	 * @return 취소된 개수
	 */
	public int cancleAuctionDetail(AuctionAndFile auctionAndDetailInfo);

	/**
	 * 입찰 취소하기
	 * @param auctionAndDetailInfo AuctionAndFile타입의 객체 정보
	 * @return 취소된 개수
	 */
	public int cancleBidDetail(AuctionAndFile auctionAndDetailInfo);

	/**
	 * 반납 확인하기
	 * @param rentalAndDetailInfo AuctionAndFile타입의 객체 정보
	 * @return 반납확인 한 개수
	 */
	public int returncheck(RentalAndFile rentalAndDetailInfo);

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

	/**
	 * 나의 다마고치 정보 검색
	 * @param user_email 로그인한 유저의 이메일
	 * @return 다마고치객체 - 새로 만든 도메인
	 */
	public Damagochi selectMyDamaInfoById(String user_email);

	/**
	 * 내가 보유한 다마고치 리스트
	 * @param user_email 로그인한 유저의 이메일
	 * @return 유저가 보유한 다마고치 리스트
	 */
	public List<Damagochi> selectMyDamaListById(String user_email);

}
