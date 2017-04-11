package com.tcs.ilp.service;

import com.tcs.ilp.dao.EmployeeDAO;
import com.tcs.ilp.model.Employee;

public class EmployeeService {
	
	public Employee getEmployeeById(int eID){
		Employee emp = null;
		
		EmployeeDAO ed = new EmployeeDAO();
		emp = ed.getEmployeeById(eID);
		
		return emp;
	}
}
