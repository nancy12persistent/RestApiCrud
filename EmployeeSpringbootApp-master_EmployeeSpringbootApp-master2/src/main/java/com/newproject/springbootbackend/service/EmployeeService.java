package com.newproject.springbootbackend.service;

import java.util.List;



import com.newproject.springbootbackend.model.Employee;

public interface EmployeeService {
	
	Employee saveEmployee(Employee employee);
	
	List<Employee> getAllEmployees();
	
	Employee getEmployeeByID(long id);
	
	Employee updateEmployee(Employee employee,long id);
	
	String deleteEmployee(long id);
	
	boolean isAvailable(long id);
	
	

}
