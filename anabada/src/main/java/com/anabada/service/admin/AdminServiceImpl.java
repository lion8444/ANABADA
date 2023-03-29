package com.anabada.service.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anabada.dao.AdminDAO;
import com.anabada.domain.Admin_board;
import com.anabada.domain.DetailData;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.UserDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private AdminDAO dao;

	/**
	 * 유저 전체 찾기
	 */
	@Override
	public ArrayList<UserDTO> findalluser() {
		ArrayList<UserDTO> user = dao.findalluser();
		return user;
	}
	/**
	 * 게시글 전체 찾기 (혹시 email 함께 보내면 해당 email 게시글만 반환)
	 */
	@Override
	public ArrayList<Admin_board> findboard(String user_email) {
		ArrayList<Admin_board> board = dao.findboard(user_email);
		return board;
	}

	/**
	 * 게시글 상태 변경
	 */
	@Override
	public String statusChange(String select, Integer index) {
		if (select.equals("post")) {
			select = "게시";
		}
		if (select.equals("unpost")) {
			select = "중지";
		}		
		ArrayList<Admin_board> board = dao.findboard();
		String id = board.get(index).getId();
		HashMap<String, Object> map = new HashMap<>();
		map.put("select", select);
		map.put("id", id);
		
		
		int i = dao.statusUpdate(map);

		ArrayList<Admin_board> newboard = dao.findboard();

		
		return newboard.get(index).getStatus();
	}

	/**
	 * 유저 상태, 닉네임 변경
	 */
	@Override
	public UserDTO usermodify(String selectn, String selects, Integer index) {
		ArrayList<UserDTO> user = dao.findalluser();
		String email = user.get(index).getUser_email();
		if(selects.equals("True")) {
			selects = "1";
		}
		if(selects.equals("False")) {
			selects = "0";
		}		
		HashMap<String, Object> map = new HashMap<>();
		map.put("selects", selects);
		map.put("selectn", selectn);
		map.put("email", email);			
		
		int i = dao.usermodify(map);
		UserDTO newuser = dao.findalluser().get(index);
		
		return newuser;
	}


	@Override
	public int[][] getdata(int[] monthnum) {
		int[ ][ ] num = new int[3][6];  

			for (int i = 0; i < 6; ++i) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("monthnum", monthnum[i]);
				map.put("string", "used");
				num[0][i] = dao.count(map);
			}
	
			for (int i = 0; i < monthnum.length; ++i) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("monthnum", monthnum[i]);
				map.put("string", "rental");
				num[1][i] = dao.count(map);
			}
			
			for (int i = 0; i < monthnum.length; ++i) {
				HashMap<String, Object> map = new HashMap<>();
				map.put("monthnum", monthnum[i]);
				map.put("string", "auction");
				num[2][i] = dao.count(map);
			}
		

				
		return num;
	}
	@Override
	public int salesamount() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int sum = dao.salesamount(month);
		return sum;
	}
	@Override
	public int joinamount() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		int join = dao.joinamount(month);		

		return join;
	}
	@Override
	public ArrayList<Inquiry> getinquiry() {
		ArrayList<Inquiry> inquiry = dao.getinquiry();
		return inquiry;
	}

	@Override
	public int updateanswer(String answer, int find) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("answer", answer);
		map.put("find", find);

		int i = dao.updateanswer(map);
		
		return i;
	}
	@Override
	public ArrayList<Report> getclaim() {
		ArrayList<Report> claim = dao.getclaim();
		return claim;
	}
	
	@Override
	public int updateclaimanswer(String claimanswer, int find) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("claimanswer", claimanswer);
		map.put("find", find);

		int i = dao.updateclaimanswer(map);
		
		return i;
	}
	
	@Override
	public ArrayList<Inquiry> getallinquiry() {
		ArrayList<Inquiry> inquiry = dao.getallinquiry();
		return inquiry;
	}
	
	@Override
	public ArrayList<Report> getallreport() {
		ArrayList<Report> Report = dao.getallreport();
		return Report;
	}
	
	@Override
	public ArrayList<DetailData> getdetaildata(String used, String rental, String auction, String number, String amount,
			String nkorea, String njapan, String lkorea, String ljapan, String visitor, String join, String sdate,
			String edate) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("nkorea", nkorea);
		map.put("njapan", njapan);
		map.put("lkorea", lkorea);
		map.put("ljapan", ljapan);
		map.put("sdate", sdate);
		map.put("edate", edate);

		ArrayList<DetailData> alldata = new ArrayList<DetailData>();
				
		if(used != null) {
			alldata.addAll(dao.getuseddata(map));
		}
		
		if(rental != null) {
			alldata.addAll(dao.getrentaldata(map));
		}
		
		if(auction != null) {
			alldata.addAll(dao.getauctiondata(map));
		}
		
		if(alldata.size() == 0) {
		}
		else {
		}
		

		
//		if(visitor != null) {
//			alldata.addAll(dao.getvisitor(map));
//		}		
		
		if(join != null) {
			alldata.addAll(dao.getjoin(map));
			
		}
		
		if(alldata.size() == 0) {
			return null;
		}
		
		return alldata;
	}

}
