package com.newproject.springbootbackend.controller;

import java.util.List;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.newproject.springbootbackend.model.Employee;
import com.newproject.springbootbackend.service.EmployeeService;
import com.newproject.springbootbackened.Response.ResponseHandler;
import com.newproject.springbootbackened.ResponseHandler2.ResponseHandler2;

@RestController
public class EmployeeController {

	RestTemplate restTemplate = new RestTemplate();

	static final String baseUrl = "http://localhost:8082/employees/";
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	// Build create employee REST API
	@PostMapping(value = "MSAVE")
	public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee) {
		boolean result = employeeService.matchRegex(employee);
		if (result) {
			employeeService.saveEmployee(employee);
			employeeService.getnameEmployeePost(employee);
			return ResponseHandler2.generateResponse("Successfully data saved!", HttpStatus.OK);
		} else {
			return ResponseHandler2.generateResponse("Enter the correct format", HttpStatus.MULTI_STATUS);
		}
	}

	// Build get all employee REST API
	@GetMapping(value = "MGET_ALL_EMP")
	public ResponseEntity<Object> getAllEmployees() {
		try {
			List<Employee> result = employeeService.getAllEmployees();
			return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	// Build Get Employee by ID REST API
	@GetMapping("MGET_EMP_ID/{id}")
	public ResponseEntity<Object> getEmployeeByID(@PathVariable("id") long id) {
		try {
			Employee result = employeeService.getEmployeeByID(id);
			return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	// Build Update Employee REST API
	@PutMapping("MUPDATE_EMP/{id}")
	public ResponseEntity<Object> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
		Employee emp = employeeService.getEmployeeByID(id);
		String name = emp.getFirst_name() + " " + emp.getLast_name();
		boolean Isstatus = restTemplate.exchange(baseUrl + "status/" + name, HttpMethod.GET, null, boolean.class)
				.getBody();
		if (Isstatus) {
			Employee result = employeeService.updateEmployee(employee, id);
			employeeService.getnameEmployeePut(result, name);
			return ResponseHandler2.generateResponse("Updated!", HttpStatus.OK);
		} else {
			return ResponseHandler2.generateResponse("Not Updated", HttpStatus.MULTI_STATUS);
		}
	}

	// Build Delete Employee BY REST API
	@DeleteMapping("MDELETING_EMP/{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable("id") long id) {
		try {
			boolean Isexist = employeeService.isAvailable(id);
			if (Isexist) {
				Employee emp = employeeService.getEmployeeByID(id);
				employeeService.deleteEmployee(id);
				employeeService.getnameEmployeeDelete(emp);
				return ResponseHandler2.generateResponse("Deleted! and Status of Employee is changed", HttpStatus.OK);
			} else {
				return ResponseHandler2.generateResponse("NO data with this id found", HttpStatus.MULTI_STATUS);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
		}
	}

	@GetMapping("MEMP_STATUS")
	public String getAllEmployeesStatus() {
		return restTemplate.exchange(baseUrl + "getemployee", HttpMethod.GET, null, String.class).getBody();
	}

}
