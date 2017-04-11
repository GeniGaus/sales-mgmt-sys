package com.tcs.ilp.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcs.ilp.model.Store;
import com.tcs.ilp.service.StoreService;

/**
 * Servlet implementation class StoreController
 */
public class StoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		StoreService ss = new StoreService();
		
		if("addStore".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\addStore.jsp");
			rd.forward(request, response);
		}
		else if("manageStore".equals(action)){
			ArrayList<Store> storeList = new ArrayList<Store>();
			storeList = ss.viewStore();
			
			if(storeList != null && storeList.size() > 0){
				request.setAttribute("storeList", storeList);
				RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\viewStore.jsp");
				rd.forward(request, response);
			}
		}
		else if("home".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\home.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		StoreService ss = new StoreService();
		
		if("addStore".equals(action)){
			Store store = new Store();
			store.setStoreName(request.getParameter("storeName"));
			store.setRegion(request.getParameter("region"));
			store.setStatus(request.getParameter("status"));
			
			int id = ss.addStore(store);
			if(id > 0){
				request.setAttribute("Id", id);
				store.setStoreId(id);
				request.setAttribute("storeDetails", store);
				RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\successAddStore.jsp");
				rd.forward(request, response);
			}
			else{
				request.setAttribute("eMsg", "Unable to add store. Please try again.");
				RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\addStore.jsp");
				rd.include(request, response);
			}
		}
		else if("manageStore".equals(action)){
			ArrayList<Store> storeList = new ArrayList<Store>();
			storeList = ss.viewStore();
			
			if(storeList != null && storeList.size() > 0){
				request.setAttribute("storeList", storeList);
				RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\viewStore.jsp");
				rd.forward(request, response);
			}
		}
	}

}
