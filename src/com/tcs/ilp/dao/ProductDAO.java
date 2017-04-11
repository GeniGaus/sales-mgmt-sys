package com.tcs.ilp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.tcs.ilp.model.ProductDTO;
import com.tcs.ilp.model.Store;
import com.tcs.ilp.util.DBUtil;


public class ProductDAO {
	
	Connection con=null;
	PreparedStatement ps=null;
	PreparedStatement ps1=null;
	PreparedStatement ps2=null;
	ArrayList<ProductDTO> productList=new ArrayList<>();
	
	public int addProduct(ProductDTO product,int storeId,int quantity){
		int rowsAffected=0;
		int rowsAffected1=0;
		int pId=0;
		con=DBUtil.getConnection();
		try {
				
				ps2=con.prepareStatement("select sequence_groupb_product.nextval as pId from dual");
				ResultSet rs=ps2.executeQuery();
				if(rs.next()){
					pId=rs.getInt("pId");
				}
				System.out.println(pId);
				
				ps=con.prepareStatement("insert into table_groupb_product values(?,?,?,?,?,?)");
			
				ps.setInt(1, pId);
				ps.setString(2,product.getProductName() );
				ps.setString(3,product.getDescription() );
				ps.setInt(4,product.getCategoryId());
				ps.setDouble(5, product.getPrice());
				ps.setString(6, product.getStatus());
			
				rowsAffected=ps.executeUpdate();
				/*to add through controller*/
				if(rowsAffected==1){
					System.out.println("in store rel tbl");
					ps1=con.prepareStatement("insert into table_groupb_store_prod_rel(n_store_id,n_product_id,n_qty) values(?,?,?)");
					ps1.setInt(1, storeId);
					ps1.setInt(2, pId);
					ps1.setInt(3, quantity);
					
					rowsAffected1=ps1.executeUpdate();
					System.out.println(rowsAffected1);
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
			DBUtil.closeStatement(ps);
			DBUtil.closeStatement(ps1);
		}
		
		return rowsAffected;
	}
	
	public int addExistingProduct(int pId,int storeId,int quantity){
		int rowsAffected=0;
		con=DBUtil.getConnection();
		try {
					ps1=con.prepareStatement("insert into table_groupb_store_prod_rel(n_store_id,n_product_id,n_qty) values(?,?,?)");
					ps1.setInt(1, storeId);
					ps1.setInt(2, pId);
					ps1.setInt(3, quantity);
					
					rowsAffected=ps1.executeUpdate();
					System.out.println(rowsAffected);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
			DBUtil.closeStatement(ps1);
		}
		
		return rowsAffected;
	}
	
	public ArrayList<ProductDTO> viewProduct(int sId){
		con=DBUtil.getConnection();
		ArrayList<ProductDTO> productList=new ArrayList<>();
		try {
			ps=con.prepareStatement("select * from table_groupb_product,table_groupb_store_prod_rel where table_groupb_product.n_product_id = table_groupb_store_prod_rel.n_product_id and n_store_id=? order by table_groupb_product.n_product_id");
			ps.setInt(1, sId);
			//System.out.println(sId);
			ResultSet rs=ps.executeQuery();
			//System.out.println(sId);
			while(rs.next()){
				int productId=rs.getInt("n_product_id");
				 String productName=rs.getString("v_product_name");
				 String description=rs.getString("v_desc");
				 int categoryId=rs.getInt("n_category_id");
				 double price=rs.getDouble("n_price_per_qty");
				 int quantity=rs.getInt("n_qty");
				 String status=rs.getString("v_status");
				 
				 ProductDTO product=new ProductDTO(productId, productName, description, categoryId, price, status);
				 product.setQty(quantity);
				 productList.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
			DBUtil.closeStatement(ps);
		}
		return productList;
	}
	
	public ArrayList<ProductDTO> viewAllProduct(int sId){
		con=DBUtil.getConnection();
		ArrayList<ProductDTO> productList=new ArrayList<>();
		try {
			ps=con.prepareStatement("select * from table_groupb_product where table_groupb_product.n_product_id not in(select table_groupb_store_prod_rel.n_product_id from table_groupb_store_prod_rel where n_store_id=?) order by table_groupb_product.n_product_id");
			ps.setInt(1, sId);
			//System.out.println(sId);
			ResultSet rs=ps.executeQuery();
			//System.out.println(sId);
			while(rs.next()){
				//System.out.println(sId);
				int productId=rs.getInt("n_product_id");
				 String productName=rs.getString("v_product_name");
				 String description=rs.getString("v_desc");
				 int categoryId=rs.getInt("n_category_id");
				 double price=rs.getDouble("n_price_per_qty");
				 String status=rs.getString("v_status");
				 
				 ProductDTO product=new ProductDTO(productId, productName, description, categoryId, price, status);
				 productList.add(product);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
			DBUtil.closeStatement(ps);
		}
		return productList;
	}
	
	public int updateProductList(int productId,double price,int sId,int qty,boolean flag1,boolean flag2){
		con=DBUtil.getConnection();
		int s1=0,s2=0,status=1;
		try {
			if(flag1){
			ps=con.prepareStatement("update table_groupb_product set n_price_per_qty=? where n_product_id=?");
			ps.setDouble(1, price);
			ps.setInt(2, productId);
			
			//ps.setString(2, product.getProductName());
			//ps.setString(3, product.getDescription());
			//ps.setInt(4, product.getCategoryId());
			//ps.setString(6, product.getStatus());
			s1=ps.executeUpdate();
			}
			if(flag2){
			ps=con.prepareStatement("update table_groupb_store_prod_rel set n_qty=? where n_product_id=? and n_store_id=?");
			ps.setInt(1, qty);
			ps.setInt(2, productId);
			ps.setInt(3, sId);
			
			s2 = ps.executeUpdate();
			}
			con.commit();
			
			if(flag1 && s1 != 1){
				status = 0;
			}
			if(flag2 && s2 != 1){
				status = 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
			DBUtil.closeStatement(ps);
		}
		return status;
	}
	public int deleteProductList(ProductDTO product, Store store){
		con=DBUtil.getConnection();
		int row=0;
		try {
			
			ps=con.prepareStatement("DELETE FROM table_groupb_store_prod_rel WHERE n_product_id=? and n_store_id=?");
			ps.setInt(1, product.getProductId());
			ps.setInt(2, store.getStoreId());
			row=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
			DBUtil.closeStatement(ps);
		}
		return row;
	}
	public ProductDTO getProductById(int pId){
		ProductDTO p = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		con = DBUtil.getConnection();
		try {
			ps = con.prepareStatement("select * from table_groupb_product where n_product_id = ?");
			ps.setInt(1, pId);
			
			rs = ps.executeQuery();
			if(rs.next()){
				p = new ProductDTO();
				p.setProductId(rs.getInt("n_product_id"));
				p.setProductName(rs.getString("v_product_name"));
				p.setDescription(rs.getString("v_desc"));
				p.setCategoryId(rs.getInt("n_category_id"));
				p.setPrice(rs.getDouble("n_price_per_qty"));
				p.setStatus(rs.getString("v_status"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
	public int deleteProduct(int pId, int sId){
		con=DBUtil.getConnection();
		int row=0;
		try {
			
			ps=con.prepareStatement("DELETE FROM table_groupb_store_prod_rel WHERE n_product_id=? and n_store_id=?");
			ps.setInt(1, pId);
			ps.setInt(2, sId);
			row=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.closeConnection(con);
			DBUtil.closeStatement(ps);
		}
		return row;
	}
}
