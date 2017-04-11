package com.tcs.ilp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tcs.ilp.model.Store;
import com.tcs.ilp.util.DBUtil;

public class StoreDAO {
	
	public int addStore(Store store){
		int status = 0, id = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("insert into table_groupb_store values(sequence_groupb_store.nextval,?,?,?)");
			ps.setString(1, store.getStoreName());
			ps.setString(2, store.getRegion());
			ps.setString(3, store.getStatus());
			
			status = ps.executeUpdate();
			con.commit();
			if(status == 1){
				ps = con.prepareStatement("select sequence_groupb_store.currval from dual");
				rs = ps.executeQuery();
				if(rs.next()){
					id = rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeResultSet(rs);
			DBUtil.closeStatement(ps);
			DBUtil.closeConnection(con);
		}
		
		return id;
	}
	
	public ArrayList<Store> viewStore(){
		ArrayList<Store> storeList = new ArrayList<Store>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_store order by n_store_id");
			
			rs = ps.executeQuery();
			while(rs.next()){
				Store s = new Store();
				s.setStoreId(rs.getInt("n_store_id"));
				s.setStoreName(rs.getString("v_store_name"));
				s.setRegion(rs.getString("v_region"));
				s.setStatus((rs.getString("v_status")));
				
				storeList.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return storeList;
	}
	
	public Store getStoreById(int sId){
		Store s = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_store where n_store_id = ?");
			ps.setInt(1, sId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				s = new Store();
				s.setStoreId(rs.getInt("n_store_id"));
				s.setStoreName(rs.getString("v_store_name"));
				s.setRegion(rs.getString("v_region"));
				s.setStatus(rs.getString("v_status"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
