package com.tcs.ilp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcs.ilp.model.Users;
import com.tcs.ilp.util.DBUtil;

public class UsersDAO {
	
	public Users searchUser(Users u){
		int status = 0;
		Users usr = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_users where v_user_name = ? and v_pwd = ?");
			ps.setString(1, u.getUserName());
			ps.setString(2, u.getPwd());
			
			rs = ps.executeQuery();
			if(rs.next()){
				status = 1;
				usr = new Users();
				usr.setEmpId(rs.getInt("n_emp_id"));
				usr.setUserName(rs.getString("v_user_name"));
				usr.setPwd(rs.getString("v_pwd"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(ps);
			DBUtil.closeConnection(con);
		}
		
		return usr;
	}
}

