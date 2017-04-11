package com.tcs.ilp.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tcs.ilp.model.Category;
import com.tcs.ilp.model.ProductDTO;
import com.tcs.ilp.model.Store;
import com.tcs.ilp.service.CategoryService;
import com.tcs.ilp.service.ProductService;
import com.tcs.ilp.service.StoreService;

/**
 * Servlet implementation class ProductController
 */
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd=null;
		ProductService prodService=new ProductService();
		String action = request.getParameter("action");
		
		if("addProduct".equalsIgnoreCase(action)){
			ArrayList<Category> catList = new ArrayList<Category>();
			CategoryService cs = new CategoryService();
			catList = cs.viewCategory();
			request.setAttribute("catList", catList);
			rd=request.getRequestDispatcher("/jsp/addProduct.jsp");
			rd.forward(request, response);
		}
		else if("manageProduct".equalsIgnoreCase(action)){
			request.setAttribute("store", request.getParameter("manageProduct"));
			Store s = (Store)(request.getAttribute("store"));
			int sId = Integer.parseInt(request.getParameter("manageProduct"));
			ArrayList<ProductDTO> productList = prodService.getProductList(sId);
			request.setAttribute("productList", productList);
			System.out.println("prodList size: "+productList.size());
			rd=request.getRequestDispatcher("/jsp/ManageProduct.jsp");
			rd.forward(request, response);
		}
		else if("updateProduct".equalsIgnoreCase(action)){
			//request.setAttribute("prodId", request.getParameter("prodId"));
			System.out.println(request.getAttribute("prodId").toString());
			rd=request.getRequestDispatcher("/jsp/updateProductList.jsp");
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd=null;
		String action = request.getParameter("action");
		String addProduct = request.getParameter("addProduct");
		String manageProduct = request.getParameter("manageProduct");
		String updateProduct = request.getParameter("updateProduct");
		String deleteProduct = request.getParameter("deleteProduct");
		String addExistingProduct = request.getParameter("addExistingProduct");
		String addNewProduct = request.getParameter("addNewProduct");
		
		if("addProduct".equalsIgnoreCase(action)){
			String productName=request.getParameter("productName");
			String productDesc=request.getParameter("prodDesc");
			int productCategory=Integer.parseInt(request.getParameter("catId"));
			double price=Double.parseDouble(request.getParameter("price"));
			String status=request.getParameter("status");
			int sId = Integer.parseInt(request.getParameter("store"));
			int qty=Integer.parseInt(request.getParameter("quantity"));
		
			ProductDTO product=new ProductDTO();
			product.setProductName(productName);
			product.setDescription(productDesc);
			product.setCategoryId(productCategory);
			product.setPrice(price);
			product.setStatus(status);
		
			ProductService ps=new ProductService();
			int stat=ps.addProduct(product,sId,qty);
			if(stat==1){
				request.setAttribute("Id", stat);
				ProductDTO p = ps.getProductById(stat);
				request.setAttribute("prod", p);
				CategoryService cs = new CategoryService();
				request.setAttribute("cat", cs.getCatById(p.getCategoryId()));
				rd=request.getRequestDispatcher("/jsp/successAddProduct.jsp");
				rd.forward(request, response);
			}
			else{
				request.setAttribute("eMsg", "Product already exists ");
				rd=request.getRequestDispatcher("/jsp/addProduct.jsp");
				rd.include(request, response);
			}
		}
		else if(addProduct != null){
			ArrayList<Category> catList = new ArrayList<Category>();
			ProductService ps=new ProductService();
			request.setAttribute("store", request.getParameter("addProduct"));
			int sId = Integer.parseInt(request.getParameter("addProduct"));
			ArrayList<ProductDTO> productList = ps.viewProductList(sId);
			request.setAttribute("productList", productList);
			request.setAttribute("store", sId);
			rd=request.getRequestDispatcher("/jsp/addProdOpt.jsp");
			rd.forward(request, response);
		}
		else if(manageProduct != null){
			ProductService ps=new ProductService();
			request.setAttribute("store", request.getParameter("manageProduct"));
			int sId = Integer.parseInt(request.getParameter("manageProduct"));
			ArrayList<ProductDTO> productList = ps.getProductList(sId);
			request.setAttribute("productList", productList);
			rd=request.getRequestDispatcher("/jsp/ManageProduct.jsp");
			rd.forward(request, response);
		}
		else if(updateProduct != null){
			int sId = Integer.parseInt(request.getParameter("store").toString());
			int pId = Integer.parseInt(request.getParameter("updateProduct").toString());
			
			StoreService ss = new StoreService();
			ProductService ps = new ProductService();
			request.setAttribute("prod", ps.getProductById(pId));
			request.setAttribute("store", ss.getStoreById(sId));
			rd=request.getRequestDispatcher("/jsp/updateProductList.jsp");
			rd.forward(request, response);
		}
		else if(deleteProduct != null){
			int sId = Integer.parseInt(request.getParameter("store").toString());
			int pId = Integer.parseInt(request.getParameter("deleteProduct").toString());
			int status = 0;
			
			StoreService ss = new StoreService();
			ProductService ps = new ProductService();
			
			//if(status > 0){
				request.setAttribute("Id", pId);
				ProductDTO p = ps.getProductById(pId);
				request.setAttribute("prod", p);
				CategoryService cs = new CategoryService();
				request.setAttribute("cat", cs.getCatById(p.getCategoryId()));
				request.setAttribute("store", ss.getStoreById(sId));
				rd=request.getRequestDispatcher("/jsp/delCnf.jsp");
				rd.forward(request, response);
			/*}
			else{
				request.setAttribute("eMsg", "Product couldn't be deleted. Please try again. ");
				rd=request.getRequestDispatcher("/jsp/ManageProduct.jsp");
				rd.include(request, response);
			}*/
		}
		else if("delProd".equalsIgnoreCase(action)){
			//Store s = (Store)(request.getAttribute("store"));
			int sId = Integer.parseInt(request.getParameter("store"));
			//ProductDTO p = (ProductDTO)(request.getAttribute("prod"));
			int pId = Integer.parseInt(request.getParameter("Id"));
			int status = 0;
			
			StoreService ss = new StoreService();
			ProductService ps = new ProductService();
			
			if(request.getParameter("submit") != null && "Confirm".equalsIgnoreCase((request.getParameter("submit")).toString())){
				status = ps.deleteProduct(pId, sId);
				
				if(status > 0){
					request.setAttribute("Id", pId);
					ProductDTO p = ps.getProductById(pId);
					request.setAttribute("prod", p);
					CategoryService cs = new CategoryService();
					request.setAttribute("cat", cs.getCatById(p.getCategoryId()));
					request.setAttribute("store", ss.getStoreById(sId));
					rd=request.getRequestDispatcher("/jsp/successProdDeletion.jsp");
					rd.forward(request, response);
				}
				else{
					request.setAttribute("eMsg", "Product couldn't be deleted. Please try again. ");
					rd=request.getRequestDispatcher("/jsp/ManageProduct.jsp");
					rd.include(request, response);
				}
			}
			else if(request.getParameter("cancel") != null && "Cancel".equalsIgnoreCase((request.getParameter("cancel")).toString())){
				request.setAttribute("eMsg", "Deletion Cancelled. ");
				rd=request.getRequestDispatcher("/jsp/ManageProduct.jsp");
				rd.include(request, response);
			}
		}
		else if("updateProduct".equalsIgnoreCase(action)){
			StoreService ss = new StoreService();
			if((request.getParameter("newPrice") != null || (request.getParameter("newPrice")).length() > 0) && (request.getParameter("newQty") != null || (request.getParameter("newQty")).length() > 0)){
			int productId=Integer.parseInt(request.getParameter("productId"));
			double price=Double.parseDouble(request.getParameter("newPrice"));
			int qty=Integer.parseInt(request.getParameter("newQty"));
			int sId = Integer.parseInt(request.getParameter("store"));
			
			ProductService ps=new ProductService();
			int stat=ps.updateProductList(productId,price,sId,qty,true,true);
			if(stat==1){
				request.setAttribute("Id", productId);
				ProductDTO p = ps.getProductById(productId);
				request.setAttribute("prod", p);
				CategoryService cs = new CategoryService();
				request.setAttribute("cat", cs.getCatById(p.getCategoryId()));
				request.setAttribute("store", ss.getStoreById(sId));
				request.setAttribute("qty", qty);
				request.setAttribute("price", price);
				rd=request.getRequestDispatcher("/jsp/successProdUpdation.jsp");
				rd.forward(request, response);
			}
			else{
				request.setAttribute("eMsg", "Product couldn't be updated. Please try again. ");
				rd=request.getRequestDispatcher("/jsp/updateProductList.jsp");
				rd.include(request, response);
			}
			}
			else if((request.getParameter("newPrice") == null || (request.getParameter("newPrice")).length() == 0) && (request.getParameter("newQty") != null || (request.getParameter("newQty")).length() > 0)){
				int productId=Integer.parseInt(request.getParameter("productId"));
				//double price=Double.parseDouble(request.getParameter("newPrice"));
				int qty=Integer.parseInt(request.getParameter("newQty"));
				int sId = Integer.parseInt(request.getParameter("store"));
				
				ProductService ps=new ProductService();
				int stat=ps.updateProductList(productId,0,sId,qty,false,true);
				if(stat==1){
					request.setAttribute("Id", productId);
					ProductDTO p = ps.getProductById(productId);
					request.setAttribute("prod", p);
					CategoryService cs = new CategoryService();
					request.setAttribute("cat", cs.getCatById(p.getCategoryId()));
					request.setAttribute("store", ss.getStoreById(sId));
					request.setAttribute("qty", qty);
					request.setAttribute("price", 0);
					rd=request.getRequestDispatcher("/jsp/successProdUpdation.jsp");
					rd.forward(request, response);
				}
				else{
					request.setAttribute("eMsg", "Product couldn't be updated. Please try again. ");
					rd=request.getRequestDispatcher("/jsp/updateProductList.jsp");
					rd.include(request, response);
				}
				}
			else if((request.getParameter("newPrice") != null || (request.getParameter("newPrice")).length() > 0) && (request.getParameter("newQty") == null || (request.getParameter("newQty")).length() == 0)){
				int productId=Integer.parseInt(request.getParameter("productId"));
				double price=Double.parseDouble(request.getParameter("newPrice"));
				//int qty=Integer.parseInt(request.getParameter("newQty"));
				int sId = Integer.parseInt(request.getParameter("store"));
				
				ProductService ps=new ProductService();
				int stat=ps.updateProductList(productId,price,sId,0,true,false);
				if(stat==1){
					request.setAttribute("Id", productId);
					ProductDTO p = ps.getProductById(productId);
					request.setAttribute("prod", p);
					CategoryService cs = new CategoryService();
					request.setAttribute("cat", cs.getCatById(p.getCategoryId()));
					request.setAttribute("store", ss.getStoreById(sId));
					request.setAttribute("qty", -1);
					request.setAttribute("price", price);
					rd=request.getRequestDispatcher("/jsp/successProdUpdation.jsp");
					rd.forward(request, response);
				}
				else{
					request.setAttribute("eMsg", "Product couldn't be updated. Please try again. ");
					rd=request.getRequestDispatcher("/jsp/updateProductList.jsp");
					rd.include(request, response);
				}
				}
			else{
				request.setAttribute("eMsg", "Please enter some field to update. ");
				rd=request.getRequestDispatcher("/jsp/updateProductList.jsp");
				rd.include(request, response);
			}
		}
		else if("addExistingProd".equalsIgnoreCase(action)){
			if(request.getParameter("qty") != null && request.getParameter("addExistingProduct") != null && !("".equalsIgnoreCase(request.getParameter("qty")))){
				int qty = Integer.parseInt(request.getParameter("qty"));
				int sId = Integer.parseInt(request.getParameter("store"));
				int pId = Integer.parseInt(request.getParameter("addExistingProduct"));
				int status = 0;
				
				System.out.println(pId);
				ProductService ps = new ProductService();
				status = ps.addExistingProduct(pId, sId, qty);
				if(status==1){System.out.println(pId);
					request.setAttribute("Id", pId);
					ProductDTO p = ps.getProductById(pId);
					request.setAttribute("prod", p);
					CategoryService cs = new CategoryService();
					request.setAttribute("cat", cs.getCatById(p.getCategoryId()));
					System.out.println(cs.getCatById(p.getCategoryId()).getCategoryId()+cs.getCatById(p.getCategoryId()).getCategoryName());
					rd=request.getRequestDispatcher("/jsp/successAddProduct.jsp");
					rd.forward(request, response);
				}
				else{
					request.setAttribute("eMsg", "Product already exists ");
					rd=request.getRequestDispatcher("/jsp/addProdOpt.jsp");
					rd.include(request, response);
				}
			}
		}
		else if(addNewProduct != null){
			if(request.getParameter("store").toString() != null){
			int sId = Integer.parseInt(request.getParameter("store").toString());
			ArrayList<Category> catList = new ArrayList<Category>();
			CategoryService cs = new CategoryService();
			catList = cs.viewCategory();
			request.setAttribute("catList", catList);
			request.setAttribute("store", sId);
			rd = request.getRequestDispatcher("/jsp/addProduct.jsp");
			rd.forward(request, response);
			}
			else{
				request.setAttribute("eMsg", "Please enter valid details ");
				rd=request.getRequestDispatcher("/jsp/addProdOpt.jsp");
				rd.include(request, response);
			}
		}
		else{
			request.setAttribute("eMsg", "Please fill all mandatory fields ");
			rd=request.getRequestDispatcher("/jsp/error.jsp");
			rd.forward(request, response);
		}
		
	}

}
