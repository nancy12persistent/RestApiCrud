package com.newproject.springbootbackend.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.newproject.springbootbackend.model.Employee;


public interface EmployeeRepository extends JpaRepository<Employee , Long>{
	
	

}
