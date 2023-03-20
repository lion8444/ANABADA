package com.anabada.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

/**
 * 파일 관련 유틸 업로드한 파일의 저장 & 서버에 저장된 파일 삭제 등의 기능
 */
@Slf4j
public class FileService {
	
	public static String saveFile(MultipartFile mfile, String uploadPath) {
		//업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
		if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
			return null;
		}
		
		//저장 폴더가 없으면 생성
		File path = new File(uploadPath);
		if (!path.isDirectory()) {	// 해당 업로드파일의 경로가 생성되어 있지 않으면,
			path.mkdirs();			// 해당경로를 만드세요
		}
		
		//원본 파일명
		String originalFilename = mfile.getOriginalFilename();
		
		//원본 파일의 확장자
		int lastIndex = originalFilename.lastIndexOf('.');
		String ext = lastIndex == -1 ? "" : originalFilename.substring(lastIndex);

		//저장할 파일명을 오늘 날짜의 년월일로 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String savedFilename = sdf.format(new Date());
		
		//저장할 전체 경로와 파일명을 포함한 File 객체
		File serverFile = null;
		
		//같은 이름의 파일이 있는 경우의 처리
		while (true) {
			serverFile = new File(uploadPath + "/" + savedFilename + ext);
			//같은 이름의 파일이 없으면 반복 종료.
			if (!serverFile.isFile()) break;	// isFile 같은 파일이 있냐없냐
			//같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임.
			savedFilename += new Date().getTime();
		}		
		
		//파일 저장
		try {
			mfile.transferTo(serverFile);	// 별도의 과정없이 바로 저장해주는 기능
		} catch (Exception e) {
			savedFilename = null;
			e.printStackTrace();
		}
		
		//저장된 파일명 리턴
		return savedFilename + ext;
	}
	
//
//	/**
//	 * 업로드 된 파일을 지정된 경로에 저장하고, 저장된 파일명을 리턴
//	 * 
//	 * @param upload     업로드된 파일
//	 * @param uploadPath 저장할 경로
//	 * @return 저장된 파일명
//	 */
//	public static String saveFile(MultipartFile upload, String uploadPath) {
//		// 업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
//		if (upload == null || upload.isEmpty() || upload.getSize() == 0) {
//			return null;
//		}
//
//		// 저장 폴더가 없으면 생성
//		File path = new File(uploadPath);
//		if (!path.isDirectory()) { // 해당 업로드파일의 경로가 생성되어 있지 않으면,
//			path.mkdirs(); // 해당경로를 만드세요
//		}
//
//		// 원본 파일명
////		ArrayList<MultipartFile> originalFilename = upload.getOriginalFilename();
//		List<String> originalFilename = new ArrayList<>();
//		List<Integer> lastIndex = new ArrayList<>();
//		List<String> ext = new ArrayList<>();
//
//		for (MultipartFile mfile : upload) {
//			originalFilename.add(mfile.getOriginalFilename());
//		}
//
//		// 원본 파일의 확장자
//		for (String ofilename : originalFilename) {
//			lastIndex.add(ofilename.lastIndexOf('.'));
//		}
//
//		for (int i = 0; i < lastIndex.size(); i++) {
//			log.debug("lastIndex 테스트 : {}", lastIndex.get(i));
//			log.debug("ofilename 테스트 : {}", originalFilename.get(i));
//			int index = lastIndex.get(i);
//			String origin = originalFilename.get(i);
//			if (index == -1) {
//				ext.add("");
//			} else {
//				log.debug("ext.add 테스트 : {}", origin.substring(index));
//				ext.add(origin.substring(index));
//			}
//		}
//
//		// 저장할 파일명을 오늘 날짜의 년월일로 생성
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String savedFilename = sdf.format(new Date());
//
//		// 저장할 전체 경로와 파일명을 포함한 File 객체
//		File serverFile = null;
//
//		int i = 0;
//		// 같은 이름의 파일이 있는 경우의 처리
//		while (true) {
//			serverFile = new File(uploadPath + "/" + savedFilename + ext.get(i++));
//			// 같은 이름의 파일이 없으면 반복 종료.
//			if (!serverFile.isFile() || ext.get(i) == null)
//				break; // isFile 같은 파일이 있냐없냐
//			// 같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임.
//			savedFilename += new Date().getTime();
//
//		}
//
//		// 파일 저장
//		try {
//			((MultipartFile) upload).transferTo(serverFile); // 별도의 과정없이 바로 저장해주는 기능
//		} catch (Exception e) {
//			savedFilename = null;
//			e.printStackTrace();
//		}
//
//		// 저장된 파일명 리턴
//		return savedFilename + ext;
//	}

//	public static String saveFile(ArrayList<MultipartFile> upload, String uploadPath) {
//		// 업로드된 파일이 없거나 크기가 0이면 저장하지 않고 null을 리턴
//		if (upload == null || upload.isEmpty() || upload.size() == 0) {
//			return null;
//		}
//
//		// 저장 폴더가 없으면 생성
//		File path = new File(uploadPath);
//		if (!path.isDirectory()) { // 해당 업로드파일의 경로가 생성되어 있지 않으면,
//			path.mkdirs(); // 해당경로를 만드세요
//		}
//
//		// 원본 파일명
////		ArrayList<MultipartFile> originalFilename = upload.getOriginalFilename();
//		List<String> originalFilename = new ArrayList<>();
//		List<Integer> lastIndex = new ArrayList<>();
//		List<String> ext = new ArrayList<>();
//
//		for (MultipartFile mfile : upload) {
//			originalFilename.add(mfile.getOriginalFilename());
//		}
//
//		// 원본 파일의 확장자
//		for (String ofilename : originalFilename) {
//			lastIndex.add(ofilename.lastIndexOf('.'));
//		}
//
//		for (int i = 0; i < lastIndex.size(); i++) {
//			log.debug("lastIndex 테스트 : {}", lastIndex.get(i));
//			log.debug("ofilename 테스트 : {}", originalFilename.get(i));
//			int index = lastIndex.get(i);
//			String origin = originalFilename.get(i);
//			if (index == -1) {
//				ext.add("");
//			} else {
//				log.debug("ext.add 테스트 : {}", origin.substring(index));
//				ext.add(origin.substring(index));
//			}
//		}
//
//		// 저장할 파일명을 오늘 날짜의 년월일로 생성
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String savedFilename = sdf.format(new Date());
//
//		// 저장할 전체 경로와 파일명을 포함한 File 객체
//		File serverFile = null;
//
//		int i = 0;
//		// 같은 이름의 파일이 있는 경우의 처리
//		while (true) {
//			serverFile = new File(uploadPath + "/" + savedFilename + ext.get(i++));
//			// 같은 이름의 파일이 없으면 반복 종료.
//			if (!serverFile.isFile() || ext.get(i) == null)
//				break; // isFile 같은 파일이 있냐없냐
//			// 같은 이름의 파일이 있으면 이름 뒤에 long 타입의 시간정보를 덧붙임.
//			savedFilename += new Date().getTime();
//
//		}
//
//		// 파일 저장
//		try {
//			((MultipartFile) upload).transferTo(serverFile); // 별도의 과정없이 바로 저장해주는 기능
//		} catch (Exception e) {
//			savedFilename = null;
//			e.printStackTrace();
//		}
//
//		// 저장된 파일명 리턴
//		return savedFilename + ext;
//	}
	/**
	 * 서버에 저장된 파일의 전체 경로를 전달받아, 해당 파일을 삭제
	 * 
	 * @param fullpath 삭제할 파일의 경로
	 * @return 삭제 여부
	 */
	public static boolean deleteFile(String fullPath) {
		// 전달된 전체 경로로 File객체 생성
		File delFile = new File(fullPath);

		// 해당 파일이 존재하면 삭제하고 true 리턴
		if (delFile.isFile()) {
			delFile.delete();
			return true;
		}
		return false;
	}
}
