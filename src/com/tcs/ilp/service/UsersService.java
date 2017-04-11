package com.tcs.ilp.service;

import com.tcs.ilp.dao.UsersDAO;
import com.tcs.ilp.model.Users;

public class UsersService {
	
	public Users checkLogin(Users u){
		int status = 0;
		Users usr = null;
		
		UsersDAO ud = new UsersDAO();
		usr = ud.searchUser(u);
		
		return usr;
	}
}
