package com.anabada.controller.mypage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.anabada.service.mypage.MyPageService;

import lombok.extern.slf4j.Slf4j;

/**
 * 마이페이지 컨트롤러
 * @author 박용우
 * 230317
 */
@Slf4j
@RequestMapping({"mypage"})
@Controller
public class MyPageController {

	@Autowired
	MyPageService service;

	/**
	 * 마이페이지 포워딩
	 * @return 마이페이지
	 */
	@GetMapping("/mypage")
	public String myPage(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {

		//		if(!user_email.equals(user.getUsername())) {
		//			return "redirect:/";
		//		}

		user_email = user.getUsername();

		// 현재 로그인한 유저의 개인 정보
		UserDTO userDTO = service.selectUserById(user_email);

		List<CharacterDTO> characterDTO = service.selectUserDama(user_email);

		log.debug("정보 : {}", userDTO);
		log.debug("다마정보 : {}", characterDTO);

		model.addAttribute("user", userDTO);
		model.addAttribute("dama", characterDTO);

		return "mypage/mypage";
	}

	/**
	 * 나의 문의 내역 포워딩
	 * @param user 유저의 이메일
	 * @return 나의 문의 내역페이지
	 */
	@GetMapping("/myinquirelist")
	public String myInquireList(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {

		log.debug("나의 문의 내역 페이지 진입");

		// 로그인한 아이디와 user_email이 같이 않으면 메인으로 이동
//				if(!user_email.equals(user.getUsername())) {
//					return "redirect:/";
//				}
//
//				log.debug("유저이메일:{}", user_email);

		String email = user.getUsername();

		UserDTO userdto = service.selectUserById(email);

		List<Inquiry> inquiry = service.selectInquiryList(userdto.getUser_email());

		model.addAttribute("user", userdto);
		model.addAttribute("inquiry", inquiry);

		return "mypage/my_inquireList";
	}

	/**
	 * 나의 신고 내역 페이지 포워딩
	 * @param user	스프링 로그인 정보
	 * @param user_email	받아오는 유저의 이메일
	 * @param model	모델
	 * @return	마이페이지 - 나의 신고 내역 페이지
	 */
	@GetMapping("/myreportlist")
	public String myReportList(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {

//				if(!user_email.equals(user.getUsername())) {
//					log.debug("아이디 일치 X");
//					return "redirect:/";
//				}

		user_email = user.getUsername();

		List<Report> list = service.selectReportList(user_email);

		model.addAttribute("report", list);

		return "mypage/my_reportList";
	}
	
	/**
	 * 나의 거래 내역 페이지 포워딩 (전체)
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 나의 거래 내역 페이지(전체)
	 */
	@GetMapping("/mytransactionlistall")
	public String myTradeList(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		log.debug("user_email : {}", user_email);
		log.debug("스프링 user : {}", user.getUsername());
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<UsedAndFile> list = service.selectUsedListAllById(user_email);
		UserDTO us = service.selectUserById(user_email);
			
		model.addAttribute("usedListAll", list);
		model.addAttribute("us", us);
		
//		log.debug("사진1: {}", list.get(0).getFile_saved());
		log.debug("user_email : {}", user_email);
		log.debug("All 이즈엠티 : {}", list.isEmpty());
		log.debug("All 자체 : {}", list == null);
		log.debug(list.get(0).getUsed_id());
		log.debug(list.get(1).getUsed_id());
		log.debug(list.get(2).getUsed_id());
		
		log.debug(list.get(0).getUsed_status());
		log.debug(list.get(1).getUsed_status());
		log.debug(list.get(2).getUsed_status());
		
		log.debug(list.get(0).getUDetail_status());
		log.debug(list.get(1).getUDetail_status());
		log.debug(list.get(2).getUDetail_status());
		
		return "mypage/my_transaction";
	}
	
	/**
	 * 나의 거래 내역 페이지 포워딩(구매 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 나의 거래 내역 페이지(구매 내역)
	 */
	@GetMapping("/mytransactionlistbuy")
	public String myTradeListBuy(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		log.debug("user_email : {}", user_email);
		log.debug("스프링 user : {}", user.getUsername());
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<UsedAndFile> list = service.selectUsedBuyListById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("usedListBuy", list);
		model.addAttribute("us", us);
		
		log.debug("but: {}", list);
		log.debug("buy 이즈엠티 : {}", list.isEmpty());
		log.debug("buy 자체 : {}", list == null);
		
		return "mypage/my_transaction";
	}
	
	/**
	 * 나의 거래 내역 페이지 포워딩(판매 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 나의 거래 내역 페이지(판매 내역)
	 */
	@GetMapping("/mytransactionlistsell")
	public String myTradeListSell(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		log.debug("user_email : {}", user_email);
		log.debug("스프링 user : {}", user.getUsername());
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<UsedAndFile> list = service.selectUsedSellListById(user_email);
		UserDTO us = service.selectUserById(user_email);
			
		model.addAttribute("usedListSell", list);
		model.addAttribute("us", us);

		log.debug("sell 이즈엠티 : {}", list.isEmpty());
		log.debug("sell 자체 : {}", list == null);
		
		return "mypage/my_transaction";
	}
	
	/**
	 * 나의 모든 렌탈 내역 포워딩 (전체)
	 * @param user 스프링 시큐리티
	 * @param user_email 유저 이메일
	 * @param model 모델
	 * @return 렌탈내역 페이지
	 */
	@GetMapping("/myrentallistall")
	public String myRentalListAll(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<RentalAndFile> list = service.selectRentalListAllById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("us", us);
		model.addAttribute("rentalListAll", list);
		
		return "mypage/my_rental";
	}
	
	/**
	 * 나의 모든 렌탈 빌린 내역 포워딩 (빌린 내역)
	 * @param user 스프링 시큐리티
	 * @param user_email 유저 이메일
	 * @param model 모델
	 * @return 렌탈 내역 페이지 (빌린 내역)
	 */
	@GetMapping("/myrentallistbuy")
	public String myRentalListBuy(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<RentalAndFile> list = service.selectRentalListBuyById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("us", us);
		model.addAttribute("rentalListBuy", list);
		
		return "mypage/my_rental";
	}
	
	/**
	 * 나의 모든 렌탈 빌려준 내역 포워딩 (빌려준 내역)
	 * @param user 스프링 시큐리티
	 * @param user_email 유저 이메일
	 * @param model 모델
	 * @return 렌탈 내역 페이지 (빌려준 내역)
	 */
	@GetMapping("/myrentallistsell")
	public String myRentalListSell(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<RentalAndFile> list = service.selectRentalListSellById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("us", us);
		model.addAttribute("rentalListSell", list);
		
		return "mypage/my_rental";
	}
	
	/**
	 * 나의 모든 경매 내역 포워딩 (전체)
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 경매 내역 페이지 (전체)
	 */
	@GetMapping("/myauctionlistall")
	public String myAuctionListAll(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<AuctionAndFile> list = service.selectAuctionListAllById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("auctionListAll", list);
		model.addAttribute("us", us);
		
		log.debug("리스트 : {}", list);
		
		return "mypage/my_auction";
	}
	
	/**
	 * 나의 경매 내역 포워딩 (경매 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 경매 내역 페이지 (경매 내역)
	 */
	@GetMapping("/myauctionlistsell")
	public String myAuctionListSell(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<AuctionAndFile> list = service.selectAuctionListSellById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("auctionListSell", list);
		model.addAttribute("us", us);
		
		log.debug("리스트 : {}", list);
		
		return "mypage/my_auction";
	}
	
	/**
	 * 나의 입찰 내역 포워딩 (입찰 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 경매 내역 페이지 (입찰 내역)
	 */
	@GetMapping("/myauctionlistbid")
	public String myAuctionListBid(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<AuctionAndFile> list = service.selectAuctionListBidById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("auctionListBid", list);
		model.addAttribute("us", us);
		
		log.debug("리스트 : {}", list);
		
		return "mypage/my_auction";
	}
	
	/**
	 * 중고 거래 취소하기
	 * @param user 스프링 시큐리티
	 * @param usedAndFile 글번호, 유저이메일, 구매자이메일, 글상태 
	 * @return 일단 메인
	 */
	@PostMapping("/cancleUsed")
	public String cancleUsed(
			@AuthenticationPrincipal UserDetails user
			, UsedAndFile usedAndFile) {
		
		log.debug("user_email : {}", usedAndFile.getUser_email());
		log.debug("buyer_email : {}", usedAndFile.getBuyer_email());
		log.debug("used_id : {}", usedAndFile.getUsed_id());
		
		UsedAndFile usedAndDetailInfo = service.selectUsedAndDetailInfo(usedAndFile.getUsed_id()); 

		log.debug("usedAndDetailInfo : {}", usedAndDetailInfo);
			
		if(!usedAndFile.getUser_email().equals(user.getUsername()) && !usedAndFile.getBuyer_email().equals(user.getUsername())) {
			log.debug("해당 중고 거래의 구매자나 판매자가 아님");
			return "redirect:/";
		}
		
		if(usedAndDetailInfo.getUsed_status().contains("중지") || usedAndDetailInfo.getUDetail_status().contains("중지")) {
			log.debug("이미 취소된 거래");
			return "redirect:/";
		}
		
		service.cancleUsedDetail(usedAndDetailInfo);
			
		return "redirect:/";
	}
	
	/**
	 * 렌탈 거래 취소하기
	 * @param user 스프링 시큐리티
	 * @param rentalAndFile 글번호, 유저이메일, 빌린사람이메일, 글상태
	 * @return 일단 메인
	 */
	@PostMapping("/cancleRental")
	public String cancleRental(
			@AuthenticationPrincipal UserDetails user
			, RentalAndFile rentalAndFile) {
		
		log.debug("시큐리티 로그인 아이디 {}", user.getUsername());
		log.debug("user_email : {}", rentalAndFile.getUser_email());
		log.debug("buyer_email : {}", rentalAndFile.getBuyer_email());
		log.debug("rental_id : {}", rentalAndFile.getRental_id());
		
		RentalAndFile rentalAndDetailInfo = service.selectRentalAndDetailInfo(rentalAndFile.getRental_id()); 

		log.debug("rentalAndDetailInfo : {}", rentalAndDetailInfo);
		log.debug("rentalAndDetailInfo.user_email : {}", rentalAndDetailInfo.getUser_email());
		log.debug("rentalAndDetailInfo.buyer_email : {}", rentalAndDetailInfo.getBuyer_email());
		
			
		if(!rentalAndDetailInfo.getUser_email().equals(user.getUsername()) && !rentalAndDetailInfo.getBuyer_email().equals(user.getUsername())) {
			log.debug("해당 렌탈 거래의 구매자나 판매자가 아님");
			return "redirect:/";
		}
		
		if(rentalAndDetailInfo.getRental_status().contains("중지") || rentalAndDetailInfo.getRDetail_status().contains("중지")) {
			log.debug("이미 취소된 거래");
			return "redirect:/";
		}
		
		// 날짜 계산
		LocalDateTime now = LocalDateTime.now();
		String rDetail_sDate = rentalAndDetailInfo.getRDetail_sDate();
		
		LocalDateTime rDetailDate = LocalDateTime.parse(rDetail_sDate + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);	
		
		log.debug("현재 날짜: {}", now);
		log.debug("DB 저장 날짜: {}", rDetailDate);
	
		// 3일 이상 남았을 때
		if (!rDetailDate.isBefore(now.plusDays(3))) {
			service.cancleRentalDetail(rentalAndDetailInfo);
		} else {
			log.debug("3일 이상 남지않음");
			return "redirect:/";
		}
			
		return "redirect:/";
	}
	
	/**
	 * 경매 취소
	 * @param user 스프핑 시큐리티
	 * @param auctionAndFile 글번호, 경매자 아이디
	 * @return 일단 메인
	 */
	@PostMapping("/cancleAuction")
	public String cancleAuction(
			@AuthenticationPrincipal UserDetails user
			, AuctionAndFile auctionAndFile) {
		
		log.debug("user_email : {}", auctionAndFile.getUser_email());
		log.debug("buyer_email : {}", auctionAndFile.getBuyer_email());
		log.debug("used_id : {}", auctionAndFile.getAuction_id());
		
		AuctionAndFile auctionAndDetailInfo = service.selectAuctionAndDetailInfo(auctionAndFile.getAuction_id()); 

		log.debug("usedAndDetailInfo : {}", auctionAndDetailInfo);
			
		if(!auctionAndDetailInfo.getUser_email().equals(user.getUsername())) {
			log.debug("경매자가 아님");
			return "redirect:/";
		}
		
		if(auctionAndDetailInfo.getAuction_status().contains("중지") || auctionAndDetailInfo.getADetail_status().contains("중지")) {
			log.debug("이미 취소된 거래");
			return "redirect:/";
		}
		
		service.cancleAuctionDetail(auctionAndDetailInfo);
			
		return "redirect:/";
		
	}
	
	/**
	 * 입찰 취소
	 * @param user	스프링 시큐리티
	 * @param auctionAndFile 글번호, 입찰자아이디
	 * @return 일단 메인
	 */
	@PostMapping("/cancleBid")
	public String cancleBid(
			@AuthenticationPrincipal UserDetails user
			, AuctionAndFile auctionAndFile) {
		
		log.debug("user_email : {}", auctionAndFile.getUser_email());
		log.debug("buyer_email : {}", auctionAndFile.getBuyer_email());
		log.debug("auction_id : {}", auctionAndFile.getAuction_id());
		
		AuctionAndFile auctionAndDetailInfo = service.selectAuctionAndDetailInfo(auctionAndFile.getAuction_id()); 

		log.debug("auctionAndDetailInfo : {}", auctionAndDetailInfo);
		log.debug("auctionAndDetailInfo.바이어 : {}", auctionAndDetailInfo.getBuyer_email());
			
		if(!auctionAndDetailInfo.getBuyer_email().equals(user.getUsername())) {
			log.debug("입찰자가 아님");
			return "redirect:/";
		}
		
		if(auctionAndDetailInfo.getAuction_status().contains("중지") || auctionAndDetailInfo.getADetail_status().contains("중지")) {
			log.debug("이미 취소된 거래");
			return "redirect:/";
		}
		
		service.cancleBidDetail(auctionAndDetailInfo);
			
		return "redirect:/";		
	}
	
	/**
	 * 반납 확인 처리
	 * @param user 스프링 시큐리티 
	 * @param rentalAndFile 렌탈데이터
	 * @return 일단 메인
	 */
	@PostMapping("/returnCheck")
	public String returnCheck(
			@AuthenticationPrincipal UserDetails user
			, RentalAndFile rentalAndFile) {
		
		RentalAndFile rentalAndDetailInfo = service.selectRentalAndDetailInfo(rentalAndFile.getRental_id());
		
		log.debug("렌탈디테일 :{}", rentalAndDetailInfo);
		log.debug("렌탈디테일상태 :{}", rentalAndDetailInfo.getRDetail_status());
		log.debug("렌탈디테일 아이디 :{}", rentalAndDetailInfo.getUser_email());
		log.debug("렌탈디테일 구매자 :{}", rentalAndDetailInfo.getBuyer_email());
		log.debug("렌탈디테일 아이디 :{}", rentalAndDetailInfo.getRental_id());
		
		if(!rentalAndDetailInfo.getRDetail_status().equals("거래 중")) {
			 log.debug("이미 취소 또는 완료된 거래");
			 return "redirect:/";
		}
		
		if(!rentalAndDetailInfo.getUser_email().equals(user.getUsername())) {
			log.debug("유저가 경매자가 아님");
			return "redirect:/";
		}
		
		service.returncheck(rentalAndDetailInfo);
		
		return "redirect:/";
	}
	
	@PostMapping("/extendCheck")
	public String test(
			RentalAndFile rentalAndFile
			, Model model) {
		
		log.debug("올라온 데이터 : {}", rentalAndFile);
		
		RentalAndFile rs = service.selectRentalAndDetailInfo(rentalAndFile.getRental_id());
		
		log.debug("rs : {}", rs);
		
		model.addAttribute("rent", rs);
		
		return "mypage/extendRental";
	}
	
	/**
	 * 찜 목록 - 중고 거래 찜 리스트
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 찜 목록 페이지 (중고 거래 찜)
	 */
	@GetMapping("/mywantlistused")
	public String myWantListUsed(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<WishAndFile> list = service.selectWishListUsedById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("myWantListUsed", list);
		model.addAttribute("us", us);
		
		return "mypage/my_wantList";
	}
	
	/**
	 * 찜 목록 - 렌탈 거래 찜 리스트
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 찜 목록 페이지 (렌탈 거래 찜)
	 */
	@GetMapping("/mywantlistrental")
	public String myWantListRental(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<WishAndFile> list = service.selectWishListRentalById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("myWantListRental", list);
		model.addAttribute("us", us);
		
		return "mypage/my_wantList";
	}
	
	/**
	 * 찜 목록 - 경매 거래 찜 리스트
	 * @param user 스프링 시큐리티 객체
	 * @param user_email 유저의 이메일
	 * @param model 모델
	 * @return 찜 목록 페이지 (경매 거래 찜)
	 */
	@GetMapping("/mywantlistauction")
	public String myWantListAuction(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		List<WishAndFile> list = service.selectWishListAuctionById(user_email);
		UserDTO us = service.selectUserById(user_email);
		
		model.addAttribute("myWantListAuction", list);
		model.addAttribute("us", us);
		
		return "mypage/my_wantList";
	}
	
	/**
	 * 나의 다마고치 페이지 포워딩
	 * @param user 스프링 스큐리티 객체
	 * @param user_email 로그인한 유저의 이메일
	 * @param model 모델
	 * @return 마이다마고치 페이지
	 */
	@GetMapping("/mydamagochi")
	public String MyDamagochi(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("id가 같지 않음");
			return "redirect:/";
		}
		
		Damagochi dama = service.selectMyDamaInfoById(user_email);
		List<Damagochi> list = service.selectMyDamaListById(user_email);
		
		model.addAttribute("dama", dama);
		model.addAttribute("damaList", list);
		
		return "mypage/my_damagochi";
	}
	
	@GetMapping("/damagochishop")
	public String damagochiShop() {
		
		return "mypage/my_damagochiShop";
	}
	
	
	

	/**
	 * 마이페이지 -> 회원정보 변경 -> 회원정보 확인 페이지 이동(이메일, 비밀번호 한번 더 입력)
	 * @param user	스프링 로그인 객체
	 * @param user_email	받아오는 유저의 이메일
	 * @return
	 */
//	@GetMapping("/checkPw")
//	public String myCheckPw() {
//			@AuthenticationPrincipal UserDetails user
//			, String user_email
//			, Model model) {
//
//		// 로그인한 아이디와 user_email이 같지 않으면 메인으로 이동
//		if(!user_email.equals(user.getUsername())) {
//			return "redirect:/";
//		}
//		
//		model.addAttribute("user_email", user_email);
//
//		return "/mypage/my_checkPwForm";
//	}
	
//	@PostMapping("/checkPw")
//	public String myCheckPw(
//			@AuthenticationPrincipal UserDetails user
//			, String user_email
//			, String user_pwd
//			, Model model) {
//		
//		if(!user_email.equals(user.getUsername())) {
//			log.debug("로그인 한 ID가 아님");
//			return "redirect:/";
//		}
//		
//		int result = service.checkIdAndPw(user_email, user_pwd);
//		
//		if(result == 1) {
//			UserDTO us = service.selectUserById(user_email);
//			model.addAttribute("user", us);
//			return "mypage/my_modifyInfoForm";
//		} 
//		
//		return "redirect:/";
//	}

	
}
