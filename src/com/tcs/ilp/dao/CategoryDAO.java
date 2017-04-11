package com.tcs.ilp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tcs.ilp.model.Category;
import com.tcs.ilp.model.EmployeeRole;
import com.tcs.ilp.util.DBUtil;

public class CategoryDAO {
	
	public ArrayList<Category> viewCategory(){
		ArrayList<Category> catList = new ArrayList<Category>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_category order by n_category_id");
			rs = ps.executeQuery();
			
			while(rs.next()){
				Category cat = new Category();
				cat.setCategoryId(rs.getInt("n_category_id"));
				cat.setCategoryName(rs.getString("v_category_name"));
				
				catList.add(cat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(ps);
			DBUtil.closeConnection(con);
		}
		
		return catList;
	}
	
	public Category getCatById(int cId){
		Category cat = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_category where n_category_id = ?");
			ps.setInt(1, cId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				cat = new Category();
				cat.setCategoryId(cId);
				cat.setCategoryName(rs.getString("v_category_name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cat;
	}
}
