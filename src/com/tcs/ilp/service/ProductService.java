package com.tcs.ilp.service;

import java.util.ArrayList;

import com.tcs.ilp.dao.ProductDAO;
import com.tcs.ilp.dao.StoreDAO;
import com.tcs.ilp.model.ProductDTO;
import com.tcs.ilp.model.Store;

public class ProductService {
	ProductDAO pdao=new ProductDAO();
	public int addProduct(ProductDTO p,int storeId,int quantity){
		return pdao.addProduct(p,storeId,quantity);
	}
	public int addExistingProduct(int pId,int storeId,int quantity){
		return pdao.addExistingProduct(pId, storeId, quantity);
	}
	public ArrayList<ProductDTO> getProductList(int sId){
		return pdao.viewProduct(sId);
	}
	public ArrayList<ProductDTO> viewProductList(int sId){
		return pdao.viewAllProduct(sId);
	}
	public int updateProductList(int productId,double price,int sId,int qty,boolean flag1,boolean flag2){
		return pdao.updateProductList(productId,price,sId,qty,flag1,flag2);
	}
	public int deleteProductList(ProductDTO p, Store s){
		return pdao.deleteProductList(p,s);
	}
	public ProductDTO getProductById(int pId){
		ProductDTO p = null;
		
		ProductDAO pd = new ProductDAO();
		p = pd.getProductById(pId);
		
		return p;
	}
	public int deleteProduct(int pId, int sId){
		int row=0;
		
		ProductDAO pd = new ProductDAO();
		row = pd.deleteProduct(pId, sId);
		
		return row;
	}
}
