package com.tcs.ilp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.tcs.ilp.model.Employee;
import com.tcs.ilp.util.DBUtil;

public class EmployeeDAO {
	
	public Employee getEmployeeById(int eID){
		int status = 0;
		Employee emp = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_employee where n_emp_id = ?");
			ps.setInt(1, eID);
			
			rs = ps.executeQuery();
			if(rs.next()){
				emp = new Employee();
				emp.setEmpId(eID);
				emp.setEmpName(rs.getString("v_emp_name"));
				emp.setDOB(rs.getDate("d_dob"));
				emp.setEmpRoleId(rs.getInt("n_emp_role_id"));
				emp.setPhoneNo(rs.getInt("n_phone_no"));
				emp.setGender(rs.getString("c_gender"));
				emp.setAddress(rs.getString("v_address"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return emp;
	}
}
