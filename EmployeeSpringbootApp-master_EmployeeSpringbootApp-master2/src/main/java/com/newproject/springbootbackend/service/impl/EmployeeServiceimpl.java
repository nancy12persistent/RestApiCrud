package com.newproject.springbootbackend.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.newproject.springbootbackend.model.Employee;
import com.newproject.springbootbackend.repository.EmployeeRepository;
import com.newproject.springbootbackend.service.EmployeeService;

@Service
public class EmployeeServiceimpl implements EmployeeService {

	RestTemplate restTemplate=new RestTemplate();
	
	static final String baseUrl="http://localhost:8082/employees/";
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
	@Override
	public void getnameEmployeePost(Employee employee) {
		String name=employee.getFirst_name()+" "+employee.getLast_name();
		Map<String, String> m=new HashMap<String,String>();
		m.put("name", name);
		restTemplate.postForEntity(baseUrl+"save", m,void.class);
	}

	@Override
	public void getnameEmployeePut(Employee result, String name) {
		String newname=result.getFirst_name()+" "+result.getLast_name();
		Map<String, String> m=new HashMap<String,String>();
		m.put("name", newname);
	    //new project
		restTemplate.put(baseUrl+"update/"+name,m,void.class);	
	}
	
	@Override
	public void getnameEmployeeDelete(Employee emp) {
		String name= emp.getFirst_name()+" "+emp.getLast_name();	
        //new project, just change status		   
	    restTemplate.exchange(baseUrl+"updateStatus/"+name,HttpMethod.PUT,null,void.class);
	}
	


}
