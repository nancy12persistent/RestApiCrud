package com.newproject.springbootbackend.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.newproject.springbootbackend.model.Employee;
import com.newproject.springbootbackend.repository.EmployeeRepository;
import com.newproject.springbootbackend.service.EmployeeService;

@Service
public class EmployeeServiceimpl implements EmployeeService {
	private EmployeeRepository employeeRepository;
	
	public EmployeeServiceimpl(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}
    @Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
    @Override
	public Employee getEmployeeByID(long id) {
    	return employeeRepository.findById(id).get();
    }
	@Override
	public Employee updateEmployee(Employee employee,long id) {
		Employee existingemployee= employeeRepository.findById(id).get();
		
		existingemployee.setFirst_name(employee.getFirst_name());
		existingemployee.setLast_name(employee.getLast_name());
		existingemployee.setDepartment(employee.getDepartment());
		
		return employeeRepository.save(existingemployee);
	}
	
	@Override
	public String deleteEmployee(long id) {
		employeeRepository.deleteById(id);
		return "Employee(" + id + ")" + " has been deleted!";
		
	}
	@Override
	public boolean isAvailable(long id) {
		Employee emp=employeeRepository.findById(id).get();	
		if(emp.getFirst_name()!=null) {
			return true;
		}
		else {
			return false;
		}
		
			
	 }


}
