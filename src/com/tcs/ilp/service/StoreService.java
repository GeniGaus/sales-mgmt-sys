package com.tcs.ilp.service;

import java.util.ArrayList;

import com.tcs.ilp.dao.StoreDAO;
import com.tcs.ilp.model.Store;

public class StoreService {
	
	public int addStore(Store store){
		int status = 0, id = 0;
		
		StoreDAO sd = new StoreDAO();
		id = sd.addStore(store);
		
		return id;
	}
	
	public ArrayList<Store> viewStore(){
		ArrayList<Store> storeList = new ArrayList<Store>();
		
		StoreDAO sd = new StoreDAO();
		storeList = sd.viewStore();
		
		return storeList;
	}
	
	public Store getStoreById(int sId){
		Store s = null;
		
		StoreDAO sd = new StoreDAO();
		s = sd.getStoreById(sId);
		
		return s;
	}
}
