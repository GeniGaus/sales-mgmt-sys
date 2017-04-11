package com.tcs.ilp.service;

import java.util.ArrayList;

import com.tcs.ilp.dao.CategoryDAO;
import com.tcs.ilp.model.Category;

public class CategoryService {
	
	public ArrayList<Category> viewCategory(){
		ArrayList<Category> catList = new ArrayList<Category>();
		
		CategoryDAO cd = new CategoryDAO();
		catList = cd.viewCategory();
		
		return catList;
	}
	
	public Category getCatById(int cId){
		Category cat = null;
		
		CategoryDAO cd = new CategoryDAO();
		cat = cd.getCatById(cId);
		
		return cat;
	}
	
}
