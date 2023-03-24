package com.anabada.service.admin;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.anabada.domain.Admin_board;
import com.anabada.domain.UserDTO;

@Service
public interface AdminService {

	ArrayList<UserDTO> findalluser();

	ArrayList<Admin_board> findboard(String user_email);

	String statusChange(String select, Integer index);

	UserDTO usermodify(String selectn, String selects, Integer index);

	int[][] getdata(int[] monthnum);

	int salesamount();

}
