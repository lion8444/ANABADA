package com.anabada.controller.rent;

import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.anabada.domain.Category;
import com.anabada.domain.File;
import com.anabada.domain.Rental;
import com.anabada.domain.Rental_detail;
import com.anabada.domain.UserDTO;
import com.anabada.service.login.LoginService;
import com.anabada.service.rent.RentService;
import com.anabada.util.FileService;
import com.anabada.util.PageNavigator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping({ "rental" })
@Controller
public class RentController {

	@Autowired
	private RentService service;
	@Autowired
	private LoginService lservice;

	// 설정파일에 정의된 업로드할 경로를 읽어서 아래 변수에 대입(from application.properites)
	@Value("${spring.servlet.multipart.location}")
	String uploadPath;

	// 페이지 당 글 수
	@Value("${user.board.page}")
	int countPerPage;

	// 페이지당 이동링크 수
	@Value("${user.board.group}")
	int pagePerGroup;

	@GetMapping({ "purchase" })
	public String purchase(@AuthenticationPrincipal UserDetails user, String rental_id, Model model) {
		Rental rental = service.findOneRental(rental_id);
		String user_email = user.getUsername();
		UserDTO userone = service.findUser(user_email);
		ArrayList<File> fileList = service.fileListByid(rental_id);

		model.addAttribute("rental", rental);
		model.addAttribute("user", userone);
		model.addAttribute("fileList", fileList);

		return "rental/rentalPurchase(RBRP).html";
	}

	@PostMapping({ "purchase" })
	public String purchase(
			@AuthenticationPrincipal UserDetails user, String rental_id, String rDetail_sDate, String rDetail_eDate,
			int rDetail_price, String rDetail_person, String rDetail_phone, String rDetail_post, String rDetail_addr1,
			String rDetail_addr2, String rDetail_memo, int user_account) {

		String user_email = user.getUsername();

		Rental_detail rd = new Rental_detail(null, rental_id, user_email, null, rDetail_person, rDetail_phone,
				rDetail_memo, rDetail_post, rDetail_addr1, rDetail_addr2, rDetail_price, null, rDetail_sDate,
				rDetail_eDate);
		int j = service.purchase(rd);

		int i = service.usemoney(user_email, user_account);

		if (j == 0 || i == 0) {
			return "redirect:/rental/purchase?rental_id=" + rental_id;
		}

		return "redirect:/mypage/myrentallistall";
	}

	/**
	 * 렌탈 게시판으로 이동(다 보여줌)
	 **/
	@GetMapping("/rentalBoard")
	public String rentalBoard(
			@AuthenticationPrincipal UserDetails userDetails
			,@RequestParam(name="page", defaultValue="1") int page
			, String type
			, String searchWord
			, String check
			, Model model) {
		
		PageNavigator navi = 
			service.getPageNavigator(pagePerGroup, countPerPage, page, type, searchWord, check);
		
		String email = null;
		
		if(userDetails != null) {
		email = userDetails.getUsername();
		}
		
		ArrayList <Rental> rentalLists = service.rentalBoard(
				navi.getStartRecord(),countPerPage, type, searchWord, check, email);
		
		ArrayList <Rental> recommendList = service.recommendList(
				navi.getStartRecord(),countPerPage, type, searchWord);
		
		//0408 추가 
		ArrayList <Rental> rentalList = new ArrayList<>();
		for (Rental rental : rentalLists) {
			UserDTO target = lservice.findUser(rental.getUser_email());
			rental.setUser_nick(target.getUser_nick());
			rentalList.add(rental);
		}
		
		UserDTO user = lservice.findUser(userDetails.getUsername());
		
		model.addAttribute("rentalList",rentalList);
		model.addAttribute("navi",navi);
		model.addAttribute("type",type);
		model.addAttribute("searchWord",searchWord);
		model.addAttribute("user", user);
		model.addAttribute("check",check);

		return "rental/rentalBoard(RB)";
	}

	/**
	 * 렌탈 상세 게시판으로 이동(한개 보여줌)
	 * 조회수
	 **/
	@GetMapping("rentalBoardRead")
	public String rentalBoardRead(
			@AuthenticationPrincipal UserDetails userDetails
			,@RequestParam(name="rental_id",defaultValue="0") String rental_id
			,Model model
			,@RequestParam(name="page", defaultValue="1") int page
			) {
		UserDTO user = lservice.findUser(userDetails.getUsername());
		model.addAttribute("user", user);	
					
		PageNavigator navi = 
				service.getPageNavigator(pagePerGroup, countPerPage, page, null, null, null);
		
		ArrayList <Rental> rentalList = service.rentalBoard(
				navi.getStartRecord(),countPerPage, null, null, null, null);
		ArrayList <File> fileList2 = service.fileList();
		
		
		
		for(int i=0 ; i < fileList2.size(); ++i) {
			if(!fileList2.get(i).getBoard_status().equals("중고 거래")) {
				fileList2.remove(i);
				--i;
			}
		}
		log.debug("filelist {}: ", fileList2);
		model.addAttribute("rentalList", rentalList);
		model.addAttribute("fileList2", fileList2);

		Rental rental_sell = service.rentalBoardRead(rental_id);
		ArrayList<File> fileList = service.fileListByid(rental_id);
		model.addAttribute("fileList", fileList);
		model.addAttribute("rental_sell", rental_sell);
		UserDTO target = lservice.findUser(rental_sell.getUser_email());
		model.addAttribute("target", target);
		return "rental/rentalBoardRead(RBR)";
	}

	/**
	 * 렌탈 글 삭제
	 **/

	@GetMapping("rentalBoardDelete")
	public String rentalBoardDelete(
			@RequestParam(name = "rental_id", defaultValue = "0") String rental_id,
			@RequestParam(name = "file_saved", defaultValue = "") String file_saved,
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());

		model.addAttribute("user", user);

		Rental rental = service.rentalBoardRead(rental_id);
		// 해당번호의 글이 있는지 확인. 없으면 글목록으로
		if (rental == null)
			return "redirect:/";
		// 로그인한 본인의 글이 맞는지 확인. 아니면 글목록으로
		if (!rental.getUser_email().equals(userDetails.getUsername()))
			return "redirect:/";

		// 첨부된 파일이 있으면 파일삭제
		if (!file_saved.isEmpty()) {
			FileService.deleteFile(uploadPath + "/" + file_saved);
		}
		// 실제 글 DB에서 삭제
		service.rentalBoardDelete(rental);
		// 글 목록으로 리다이렉트
		return "redirect:/";
	}

	/**
	 * 렌탈 글쓰기 게시판으로 이동
	 **/
	@GetMapping("/rentalWrite")
	public String rentalWrite(
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());
		ArrayList<Category> category_main = service.maincategory();
		model.addAttribute("user", user);
		model.addAttribute("category_main", category_main);
		return "rental/rentalWrite(RBW)";
	}

	/**
	 * 렌탈 글쓰기 처리
	 **/
	@PostMapping("/rentalWrite")
	public String rentalWrite(
			@AuthenticationPrincipal UserDetails user,
			@ModelAttribute Rental rental,
			@RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne) {

		// 로그인한 아이디 읽어서 board객체에 추가
		rental.setUser_email(user.getUsername());

		String rental_id = service.rentalWrite(rental);

		if (uploadOne.isEmpty()) {
			log.debug("이미지 X");
			return "redirect:/";
		}

		// 추가된 사진 처리
		if (!uploadOne.isEmpty()) {
			String filename = FileService.saveFile(uploadOne, uploadPath);
			File savedFile = new File();
			savedFile.setFile_origin(uploadOne.getOriginalFilename());
			savedFile.setFile_saved(filename);
			savedFile.setBoard_no(rental_id);
			savedFile.setBoard_status("렌탈 거래");
			service.insertFile(savedFile);
		}

		if (!upload.get(0).isEmpty()) {
			for (int i = 0; i < upload.size(); ++i) {
				MultipartFile file = upload.get(i);
				String filename = FileService.saveFile(file, uploadPath);
				File savedFile = new File();
				savedFile.setFile_origin(file.getOriginalFilename());
				savedFile.setFile_saved(filename);
				savedFile.setBoard_no(rental_id);
				savedFile.setBoard_status("렌탈 거래");
				service.insertFile(savedFile);
			}
		}

		return "redirect:/";
	}

	/**
	 * 렌탈 글 수정
	 **/
	@GetMapping("rentalBoardUpdate")
	public String rentalBoardUpdate(
			@RequestParam(name = "rental_id", defaultValue = "0") String rental_id,
			@AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());

		model.addAttribute("user", user);

		// 전달된 아이디의 글정보 읽기
		Rental rental = service.rentalBoardRead(rental_id);

		// 본인 글인지 확인, 아니면 글목록으로 이동
		if (!rental.getUser_email().equals(userDetails.getUsername()))
			return "redirect:/";

		// 글정보를 모델에 저장
		model.addAttribute("rental", rental);

		// 수정을 html로 포워딩
		return "rental/rentalBoardUpdate.html";
	}

	// 수정폼에서 보낸 내용 처리
	@PostMapping("rentalBoardUpdate")
	public String rentalBoardUpdate(
			@ModelAttribute Rental rental, @RequestParam(name = "upload") ArrayList<MultipartFile> upload,
			@RequestParam(name = "uploadOne") MultipartFile uploadOne, @AuthenticationPrincipal UserDetails user) {
		String rental_id = service.rentalBoardUpdate(rental);

		// 로그인한 사용자의 아이디를 읽음
		String id = user.getUsername();

		if (rental_id == null) {
			return "redirect:/";
		}
		// 처음에 해당 글에 file있는지 여부 확인해서 isempty 그래서 있으면
		// 걍 싹 다 지우고 다시 저장하는데 순서가 맨 처음에 uploadOne 을 넣고 그다음에
		// 배열 돌리기

		// 추가된 사진 처리
		if (!uploadOne.isEmpty()) {
			ArrayList<File> files = service.fileListAll(rental_id);

			if (!files.isEmpty()) {
				// service.deleteFile
			}

			String filename = FileService.saveFile(uploadOne, uploadPath);
			File savedFile = new File();
			savedFile.setFile_origin(uploadOne.getOriginalFilename());
			savedFile.setFile_saved(filename);
			savedFile.setBoard_no(rental_id);
			savedFile.setBoard_status("렌탈 거래");
			service.insertFile(savedFile);
		}

		for (int i = 0; i < upload.size(); ++i) {
			MultipartFile file = upload.get(i);
			if (!file.isEmpty()) {
				String filename = FileService.saveFile(file, uploadPath);
				File savedFile = new File();
				savedFile.setFile_origin(file.getOriginalFilename());
				savedFile.setFile_saved(filename);
				savedFile.setBoard_no(rental_id);
				savedFile.setBoard_status("렌탈 거래");
				service.insertFile(savedFile);
			}
		}
		return "redirect:/";
	}

	@GetMapping({ "extension" })
	public String extension(
			String rental_id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		UserDTO user = lservice.findUser(userDetails.getUsername());

		model.addAttribute("user", user);
		Rental rental = service.findOneRental(rental_id);

		model.addAttribute("rental", rental);

		return "rental/rentalExtension.html";
	}

	@PostMapping({ "extension" })
	public String purchase(
			@AuthenticationPrincipal UserDetails user, String rental_id, String rDetail_sDate, String rDetail_eDate,
			int rDetail_price, int user_account, String rDetail_id) {

		String user_email = user.getUsername();

		// Rental_detail rd = new Rental_detail(null, rental_id, user_email, null,
		// rDetail_person, rDetail_phone, rDetail_memo, rDetail_post, rDetail_addr1,
		// rDetail_addr2, rDetail_price, null, rDetail_sDate, rDetail_eDate);
		// int j = service.purchase(rd);

		int i = service.usemoney(user_email, user_account);

		// if(j == 0 || i ==0) {
		// return "redirect:/rental/purchase?rental_id=" + rental_id;
		// }

		return "rental/rentalThanks.html";
	}

	@GetMapping({ "/imgshow" })
	public String download(HttpServletResponse response, String rental_id) {
		ArrayList<File> fileList = service.fileList();

		for (int i = 0; i < fileList.size(); ++i) {
			if (!fileList.get(i).getBoard_status().equals("렌탈 거래")) {
				fileList.remove(i);
				--i;
			}
		}

		int index = 0;

		for (int i = 0; i < fileList.size(); ++i) {
			if (rental_id.equals(fileList.get(i).getBoard_no())) {
				index = i;
			}
		}

		String file = uploadPath + "/" + fileList.get(index).getFile_saved();

		FileInputStream in = null;
		ServletOutputStream out = null;

		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(fileList.get(index).getFile_origin(), "UTF-8"));
			in = new FileInputStream(file);
			out = response.getOutputStream();

			FileCopyUtils.copy(in, out);

			in.close();
			out.close();
		} catch (Exception e) {
			return "redirect:/";
		}
		return "redirect:/";
	}

	@GetMapping({ "/imgshowone" })
	public String download(HttpServletResponse response, String rental_id, int index) {
		log.info(index + "");

		ArrayList<File> fileList = service.fileListByid(rental_id);
		String file = uploadPath + "/" + fileList.get(index).getFile_saved();

		FileInputStream in = null;
		ServletOutputStream out = null;

		try {
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(fileList.get(index).getFile_origin(), "UTF-8"));
			in = new FileInputStream(file);
			out = response.getOutputStream();

			FileCopyUtils.copy(in, out);

			in.close();
			out.close();
		} catch (Exception e) {
			return "redirect:/";

		}
		return "redirect:/";
	}
	
	@GetMapping("charge")
	public String charge() {
		return "/mypage/charge.html";
	}
}
