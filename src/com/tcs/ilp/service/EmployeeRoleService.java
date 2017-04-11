package com.tcs.ilp.service;

import com.tcs.ilp.dao.EmployeeRoleDAO;
import com.tcs.ilp.model.EmployeeRole;

public class EmployeeRoleService {
	
	public EmployeeRole getRoleById(int rId){
		EmployeeRole er = null;
		
		EmployeeRoleDAO erd = new EmployeeRoleDAO();
		er = erd.getRoleById(rId);
		
		return er;
	}
}
