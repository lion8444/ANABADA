package com.anabada.controller.mypage;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.anabada.util.PageNavigator;

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
	
	// 설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입(from application.properites)
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;
	
	// 페이지 당 글 수
	@Value("${user.board.page}")
	int countPerPage;

	// 페이지당 이동링크 수
	@Value("${user.board.group}")
	int pagePerGroup;

	/**
	 * 마이페이지 포워딩
	 * @return 마이페이지
	 */
	@GetMapping("/mypage")
	public String myPage(
			@AuthenticationPrincipal UserDetails user
			, Model model) {

		// 현재 로그인한 유저의 개인 정보
		UserDTO userDTO = service.selectUserById(user.getUsername());

//		List<CharacterDTO> characterDTO = service.selectUserDama(user.getUsername());
		
		Damagochi selectDama = service.selectMyDamaInfoById(user.getUsername());
		
		log.debug("정보 : {}", userDTO);
//		log.debug("다마정보 : {}", characterDTO);
		log.debug("셀렉트다마 : ", selectDama);
		
		model.addAttribute("user", userDTO);
//		model.addAttribute("dama", characterDTO);
		model.addAttribute("selectDama", selectDama);

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
			, Model model) {

		log.debug("나의 문의 내역 페이지 진입");

		String email = user.getUsername();

		UserDTO userdto = service.selectUserById(email);

		List<Inquiry> inquiry = service.selectInquiryList(user.getUsername());

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
			, Model model) {

		List<Report> list = service.selectReportList(user.getUsername());

		model.addAttribute("report", list);

		return "mypage/my_reportList";
	}
		
	/**
	 * 나의 거래 내역 페이지 포워딩(구매 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param model 모델
	 * @return 나의 거래 내역 페이지(구매 내역)
	 */
	@GetMapping("/mytransactionlistbuy")
	public String myTradeListBuy(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
		
		log.debug("스프링 user : {}", user.getUsername());
		
		
		List<UsedAndFile> list = service.selectUsedBuyListById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
		
		model.addAttribute("usedListBuy", list);
		model.addAttribute("us", us);
		
		return "mypage/my_transaction";
	}
	
	/**
	 * 나의 거래 내역 페이지 포워딩(판매 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param model 모델
	 * @return 나의 거래 내역 페이지(판매 내역)
	 */
	@GetMapping("/mytransactionlistsell")
	public String myTradeListSell(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
		
		log.debug("스프링 user : {}", user.getUsername());
		
		List<UsedAndFile> list = service.selectUsedSellListById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
			
		model.addAttribute("usedListSell", list);
		model.addAttribute("us", us);

		log.debug("sell 이즈엠티 : {}", list.isEmpty());
		log.debug("sell 자체 : {}", list == null);
		
		return "mypage/my_transaction";
	}
	
	/**
	 * 나의 모든 렌탈 빌린 내역 포워딩 (빌린 내역)
	 * @param user 스프링 시큐리티
	 * @param model 모델
	 * @return 렌탈 내역 페이지 (빌린 내역)
	 */
	@GetMapping("/myrentallistbuy")
	public String myRentalListBuy(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
		
		List<RentalAndFile> list = service.selectRentalListBuyById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
		
		model.addAttribute("us", us);
		model.addAttribute("rentalListBuy", list);
		
		return "mypage/my_rental";
	}
	
	/**
	 * 나의 모든 렌탈 빌려준 내역 포워딩 (빌려준 내역)
	 * @param user 스프링 시큐리티
	 * @param model 모델
	 * @return 렌탈 내역 페이지 (빌려준 내역)
	 */
	@GetMapping("/myrentallistsell")
	public String myRentalListSell(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
			
		List<RentalAndFile> list = service.selectRentalListSellById(user.getUsername());
		
		UserDTO us = service.selectUserById(user.getUsername());
		
		// 현재날짜와 sDate를 비교 작거나같으면 -> 거래 완료 처리
		List<RentalAndFile> listAll = service.selectRentalListAll();
		int result = service.updateRentalStatus();
		log.debug("렌탈일로 업데이트 된 개수 : {}", result);
		
		int rTradeResult = service.insertRTrade(listAll);
		
		model.addAttribute("us", us);
		model.addAttribute("rentalListSell", list);
		
		return "mypage/my_rental";
	}
	
	/**
	 * 나의 경매 내역 포워딩 (경매 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param model 모델
	 * @return 경매 내역 페이지 (경매 내역)
	 */
	@GetMapping("/myauctionlistsell")
	public String myAuctionListSell(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
		
		List<AuctionAndFile> listAll = service.selectAuctionListAll();
		List<AuctionAndFile> list = service.selectAuctionListSellById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
		
		// 페이지 들어갈 때 경매 시간이 지나면 거래 완료로 변경하기 
		int result = service.updateAuctionStatus();
		log.debug("경매완료로 업데이트된 개수 : {}", result);
		
		// 페이지 들어갈 때 거래 완료인거 aTrade에 넣기
		int aDetailResult = service.insertATrade(listAll);
//		log.debug("aTrade에 추가된 개수 : {}", aDetailResult);
		
		model.addAttribute("auctionListSell", list);
		model.addAttribute("us", us);
		
		log.debug("리스트 : {}", list);
		
		return "mypage/my_auction";
	}
	
	/**
	 * 나의 입찰 내역 포워딩 (입찰 내역)
	 * @param user 스프링 시큐리티 객체
	 * @param model 모델
	 * @return 경매 내역 페이지 (입찰 내역)
	 */
	@GetMapping("/myauctionlistbid")
	public String myAuctionListBid(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
			
		List<AuctionAndFile> list = service.selectAuctionListBidById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
		
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
		
		log.debug("usedAndFile : {}", usedAndFile);
		log.debug("user_email : {}", usedAndFile.getUser_email());
		log.debug("buyer_email : {}", usedAndFile.getBuyer_email());
		log.debug("used_id : {}", usedAndFile.getUsed_id());
			
		if(!usedAndFile.getUser_email().equals(user.getUsername()) && !usedAndFile.getBuyer_email().equals(user.getUsername())) {
			log.debug("해당 중고 거래의 구매자나 판매자가 아님");
			return "redirect:/mypage/mypage";
		}
		
		service.cancleUsedDetail(usedAndFile);
			
		return "redirect:/mypage/mytransactionlistsell";
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
//		LocalDateTime now = LocalDateTime.now();
		String rDetail_sDate = rentalAndDetailInfo.getRDetail_sDate();
//		
//		LocalDateTime rDetailDate = LocalDateTime.parse(rDetail_sDate + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);	
//		
//		log.debug("파싱저장 날짜: {}", rDetailDate);
//		
//		// 3일 이상 남았을 때
//		if (!rDetailDate.isBefore(now.plusDays(3))) {
//			service.cancleRentalDetail(rentalAndDetailInfo);
//		} else {
//			log.debug("3일 이상 남지않음");
//			return "redirect:/";
//		}
		
		// GPT
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime rDetailDate = LocalDateTime.parse(rDetail_sDate, formatter);
		LocalDateTime now = LocalDateTime.now();
		
		log.debug("현재 날짜: {}", now);
		log.debug("파싱저장 날짜: {}", rDetailDate);

		if (!rDetailDate.isBefore(now.plusDays(3))) {
		    service.cancleRentalDetail(rentalAndDetailInfo);
		} else {
		    log.debug("3일 이상 남지않음");
		    return "redirect:/";
		}
			
		return "redirect:/mypage/myrentallistbuy";
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
		
		if(!auctionAndFile.getUser_email().equals(user.getUsername())) {
			log.debug("경매자가 아님");
		}
				
		service.cancleAuctionDetail(auctionAndFile);
			
		return "redirect:/mypage/myauctionlistsell";
		
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
		
		if(!auctionAndFile.getBuyer_email().equals(user.getUsername())) {
			log.debug("입찰자가 아님");
		}
		
		service.cancleBidDetail(auctionAndFile);
			
		return "redirect:/mypage/myauctionlistbid";	
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
		
		return "redirect:/mypage/myrentallistsell";
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
			, Model model) {
				
		List<WishAndFile> list = service.selectWishListUsedById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
		
		model.addAttribute("myWantListUsed", list);
		model.addAttribute("us", us);
		
		return "mypage/my_wantList";
	}
	
	/**
	 * 찜 목록 - 렌탈 거래 찜 리스트
	 * @param user 스프링 시큐리티 객체
	 * @param model 모델
	 * @return 찜 목록 페이지 (렌탈 거래 찜)
	 */
	@GetMapping("/mywantlistrental")
	public String myWantListRental(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
			
		List<WishAndFile> list = service.selectWishListRentalById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
		
		model.addAttribute("myWantListRental", list);
		model.addAttribute("us", us);
		
		return "mypage/my_wantList";
	}
	
	/**
	 * 찜 목록 - 경매 거래 찜 리스트
	 * @param user 스프링 시큐리티 객체
	 * @param model 모델
	 * @return 찜 목록 페이지 (경매 거래 찜)
	 */
	@GetMapping("/mywantlistauction")
	public String myWantListAuction(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
			
		List<WishAndFile> list = service.selectWishListAuctionById(user.getUsername());
		UserDTO us = service.selectUserById(user.getUsername());
		
		model.addAttribute("myWantListAuction", list);
		model.addAttribute("us", us);
		
		return "mypage/my_wantList";
	}
	
	/**
	 * 나의 다마고치 페이지 포워딩
	 * @param user 스프링 스큐리티 객체
	 * @param model 모델
	 * @return 마이다마고치 페이지
	 */
	@GetMapping("/mydamagochi")
	public String MyDamagochi(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
		
		Damagochi dama = service.selectMyDamaInfoById(user.getUsername());
		
		List<Damagochi> list = service.selectMyDamaListById(user.getUsername());
		
		log.debug("dama : {}", dama);
		log.debug("damaList : {}", list);
		
		model.addAttribute("dama", dama);
		model.addAttribute("damaList", list);
		
		return "mypage/my_damagochi";
	}
	
	/**
	 * 대표 캐릭터 설정하기
	 * @param user 스프링 시쿠리티 객체
	 * @param damagochi	uChar_id
	 * @param model 모델
	 * @return
	 */
	@PostMapping("/setDama")
	public String setMyDama(
			@AuthenticationPrincipal UserDetails user
			, Damagochi damagochi
			, Model model) {
		
		damagochi.setUser_email(user.getUsername());
		
		// 현재 설정된 대표 다마고치 해제
		service.updateCharSelectedZero(damagochi);
		
		// 현재 가져오는 정보로 대표 다마고치 설정
		service.updateCharSelectedOne(damagochi);
		
		Damagochi dama = service.selectMyDamaInfoById(user.getUsername());
		
		List<Damagochi> list = service.selectMyDamaListById(user.getUsername());
		
		log.debug("다마고치 : {}", damagochi);
		
		model.addAttribute("dama", dama);
		model.addAttribute("damaList", list);

		
		return "redirect:/mypage/mydamagochi";
	}
	
	// 나의 다마고치 상점
	@GetMapping("/damagochishop")
	public String damagochiShop(
			@AuthenticationPrincipal UserDetails user) {
		
		return "mypage/my_damagochiShop";
	}
	
	// 자주 묻는 질문 페이지 포워딩
	@GetMapping("/faqs")
	public String faqs() {	
		return "mypage/my_faqs";
	}
	
	/**
	 * 마이페이지 -> 회원정보 변경 -> 회원정보 확인 페이지 이동(이메일, 비밀번호 한번 더 입력)
	 * @param user	스프링 로그인 객체
	 * @param user_email	받아오는 유저의 이메일
	 * @return
	 */
	@GetMapping("/checkPw")
	public String myCheckPw(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, Model model) {
		
		log.debug("유저이메일: {}", user_email);
			 
		// 로그인한 아이디와 user_email이 같지 않으면 메인으로 이동
		if(!user_email.equals(user.getUsername())) {
			return "redirect:/";
		}
		
		model.addAttribute("user_email", user_email);

		return "/mypage/my_checkPwForm";
	}
	
	@PostMapping("/checkPw")
	public String myCheckPw(
			@AuthenticationPrincipal UserDetails user
			, String user_email
			, String user_pwd
			, Model model) {
		
		if(!user_email.equals(user.getUsername())) {
			log.debug("로그인 한 ID가 아님");
			return "redirect:/";
		}
		
		int result = service.checkIdAndPw(user_email, user_pwd);
		
		if(result == 1) {
			UserDTO us = service.selectUserById(user_email);
			model.addAttribute("user", us);
			return "mypage/my_modifyInfoForm";
		} 
		
		return "redirect:/";
	}
	
	/**
	 * 이미지 불러오기 - 사진 출력
	 * @param response
	 * @param used_id
	 * @param index
	 * @return
	 */
	@GetMapping({"/imgshowone"})
	public String download(HttpServletResponse response, String used_id, String rental_id, String auction_id) {
		//		log.info(index+"");

		if(used_id != null) {
			ArrayList <File> fileList = service.fileListByid(used_id);
			String file = uploadPath + "/" + fileList.get(0).getFile_saved();

			FileInputStream in = null;		
			ServletOutputStream out = null;

			try {	
				response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileList.get(0).getFile_origin(), "UTF-8"));
				in = new FileInputStream(file);
				out = response.getOutputStream();

				FileCopyUtils.copy(in, out);

				in.close();
				out.close();
			} catch (Exception e) {
				return "redirect:/";
			}
		}
		
		if(rental_id != null) {
			ArrayList <File> fileList = service.fileListByid(rental_id);
			String file = uploadPath + "/" + fileList.get(0).getFile_saved();

			FileInputStream in = null;		
			ServletOutputStream out = null;

			try {	
				response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileList.get(0).getFile_origin(), "UTF-8"));
				in = new FileInputStream(file);
				out = response.getOutputStream();

				FileCopyUtils.copy(in, out);

				in.close();
				out.close();
			} catch (Exception e) {
				return "redirect:/";
			}
		}
		
		if(auction_id != null) {
			ArrayList <File> fileList = service.fileListByid(auction_id);
			String file = uploadPath + "/" + fileList.get(0).getFile_saved();

			FileInputStream in = null;		
			ServletOutputStream out = null;

			try {	
				response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileList.get(0).getFile_origin(), "UTF-8"));
				in = new FileInputStream(file);
				out = response.getOutputStream();

				FileCopyUtils.copy(in, out);

				in.close();
				out.close();
			} catch (Exception e) {
				return "redirect:/";
			}
		}
		return "redirect:/";
	}
	
	/**
	 * 회원 탈퇴 페이지 진입
	 * @param user 스프링 시큐리티
	 * @param model 모델
	 * @return
	 */
	@GetMapping("/goToUnregister")
	public String goToUnregister(
			@AuthenticationPrincipal UserDetails user
			, Model model) {
		
		log.debug("진입");
		
		UserDTO us = service.selectUserById(user.getUsername());
		
		model.addAttribute("user", user.getUsername());
		model.addAttribute("user", us);
		
		return "mypage/my_unregisterForm";
	}

	@GetMapping("charge")
	public String charge(String user_nick) {
		return "mypage/charge.html";
	}
	
	/**
	 * 중고_거래완료처리 
	 * @param user
	 * @param usedData
	 * @return
	 */
	@PostMapping("/comfirmUsed")
	public String comfirmUsed(
			@AuthenticationPrincipal UserDetails user
			, UsedAndFile usedData) {
		
		log.debug("거래 확인 진입");
		log.debug("올라온 데이터 : {}", usedData);
		
		int result = service.confirmUsed(usedData);
		
		log.debug("거래확인 으로 수정 개수 : {}", result);
		
		return "redirect:/mypage/mytransactionlistbuy";
	}
}
