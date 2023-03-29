package com.anabada.service.admin;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.anabada.domain.Admin_board;
import com.anabada.domain.DetailData;
import com.anabada.domain.Inquiry;
import com.anabada.domain.Report;
import com.anabada.domain.UserDTO;

@Service
public interface AdminService {

	ArrayList<UserDTO> findalluser();

	ArrayList<Admin_board> findboard(String user_email);

	String statusChange(String select, Integer index);

	UserDTO usermodify(String selectn, String selects, Integer index);

	int[][] getdata(int[] monthnum);

	int salesamount();

	int joinamount();

	ArrayList<Inquiry> getinquiry();

	int updateanswer(String answer, int find);

	ArrayList<Report> getclaim();

	int updateclaimanswer(String claimanswer, int find);

	ArrayList<Inquiry> getallinquiry();

	ArrayList<Report> getallreport();

	ArrayList<DetailData> getdetaildata(String used, String rental, String auction, String number, String amount,
			String nkorea, String njapan, String lkorea, String ljapan, String visitor, String join, String sdate,
			String edate);

}
