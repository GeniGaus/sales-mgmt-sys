package com.tcs.ilp.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tcs.ilp.model.Users;
import com.tcs.ilp.service.EmployeeRoleService;
import com.tcs.ilp.service.EmployeeService;
import com.tcs.ilp.service.UsersService;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		if("logout".equals(action)){
			HttpSession ses = request.getSession(false);
			ses.invalidate();
			RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\login.jsp");
			rd.forward(request, response);
		}
		else if("login".equals(action)){
			RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\login.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		if("login".equals(action)){
			String uname = request.getParameter("username");
		    String pwd = request.getParameter("pwd");
		    UsersService us = new UsersService();
		    HttpSession ses;
		
		    Users u = new Users();
		    u.setUserName(uname);
		    u.setPwd(pwd);
		    Users usr = us.checkLogin(u);
		
		    if(usr != null){
		    	ses = request.getSession(true);
			    ses.setAttribute("uname", usr.getUserName());
			    //ses.setAttribute("empId", usr.getEmpId());
			    EmployeeService es = new EmployeeService();
			    ses.setAttribute("emp", es.getEmployeeById(usr.getEmpId()));
			    EmployeeRoleService ers = new EmployeeRoleService();
			    ses.setAttribute("rId", ers.getRoleById((es.getEmployeeById(usr.getEmpId()).getEmpRoleId())).getEmpRoleName());
			    RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\home.jsp");
			    rd.forward(request, response);
		    }
		    else{
		    	String eMsg = "Invalid Credentials. Please check again.";
			    request.setAttribute("eMsg", eMsg);
			    RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\login.jsp");
			    rd.forward(request, response);
		    }
		}
		else if("query".equals(action)){
			request.setAttribute("result", "Thank you for posting your queries. We will connect with you either via email or mobile. ");
			RequestDispatcher rd = request.getRequestDispatcher("\\jsp\\success.jsp");
			rd.forward(request, response);
		}
	}

}
