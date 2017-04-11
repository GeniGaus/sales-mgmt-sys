package com.tcs.ilp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcs.ilp.model.EmployeeRole;
import com.tcs.ilp.util.DBUtil;

public class EmployeeRoleDAO {
	
	public EmployeeRole getRoleById(int rId){
		EmployeeRole er = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_emp_role where n_emp_role_id = ?");
			ps.setInt(1, rId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				er = new EmployeeRole();
				er.setEmpRoleId(rId);
				er.setEmpRoleName(rs.getString("v_emp_role_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return er;
	}
}
