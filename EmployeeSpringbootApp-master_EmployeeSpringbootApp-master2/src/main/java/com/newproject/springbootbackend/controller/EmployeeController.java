package com.newproject.springbootbackend.controller;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.newproject.springbootbackend.model.Employee;
import com.newproject.springbootbackend.service.EmployeeService;
import com.newproject.springbootbackened.Response.ResponseHandler;
import com.newproject.springbootbackened.ResponseHandler2.ResponseHandler2;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
	
	RestTemplate restTemplate=new RestTemplate();
	
	static final String baseUrl="http://localhost:8082/employees/";
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	//Build create employee REST API
	@PostMapping
	public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee) {
			if(employee.getFirst_name().matches("[a-zA-Z]+") && employee.getLast_name().matches("[a-zA-Z]+") &&  employee.getDepartment().matches("[a-zA-Z]+")) {
			employeeService.saveEmployee(employee);
			String name=employee.getFirst_name()+" "+employee.getLast_name();
			Map<String, String> m=new HashMap<String,String>();
			m.put("name", name);
			restTemplate.postForEntity(baseUrl+"save", m,void.class);
            return ResponseHandler2.generateResponse("Successfully data saved!", HttpStatus.OK);
		}
			else{
		    return ResponseHandler2.generateResponse("Enter the correct format", HttpStatus.MULTI_STATUS);		
		}		
	}
	
	//Build get all employee REST API
	@GetMapping(value="/getemp")
		public ResponseEntity<Object> getAllEmployees() {
	        try {
	            List<Employee> result = employeeService.getAllEmployees();
	            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
	        } catch (Exception e) {
	            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }
	    }
		
	
	
	//Build Get Employee by ID REST API
	@GetMapping("{id}")
	public ResponseEntity<Object> getEmployeeByID(@PathVariable("id") long id){
		 try {
	            Employee result = employeeService.getEmployeeByID(id);
	            return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
	        } catch (Exception e) {
	            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
	        }	
	}
	
	//Build Update Employee REST API
	@PutMapping("{id}")
	public  ResponseEntity<Object> updateEmployee(@PathVariable("id") long id,@RequestBody Employee employee) {
			//call new project here and check the status first	
			Employee emp = employeeService.getEmployeeByID(id);
			String name = emp.getFirst_name()+" "+emp.getLast_name();		
			boolean Isstatus=restTemplate.exchange(baseUrl+"status/"+name,HttpMethod.GET,null,boolean.class).getBody();	

			if(Isstatus) {
				Employee result = employeeService.updateEmployee(employee,id);	
				String newname=result.getFirst_name()+" "+result.getLast_name();
				Map<String, String> m=new HashMap<String,String>();
				m.put("name", newname);
			    //new project
				restTemplate.put(baseUrl+"update/"+name,m,void.class);
			    return ResponseHandler.generateResponse("Updated!", HttpStatus.OK, result);
			}
		  else {
			return ResponseHandler.generateResponse("Not Updated", HttpStatus.MULTI_STATUS,null);}
		 
	}
	
	//Build Delete Employee BY REST API
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteEmployee(@PathVariable("id") long id) {	
		try {
			//check , if exist or not
		    boolean Isexist = employeeService.isAvailable(id);
			if(Isexist) {
				Employee emp = employeeService.getEmployeeByID(id);
				String name= emp.getFirst_name()+" "+emp.getLast_name();
				String result = employeeService.deleteEmployee(id);	
                //new project, just change status		   
			    ResponseEntity<Void> responseEntity=restTemplate.exchange(baseUrl+"updateStatus/"+name,HttpMethod.PUT,null,void.class);
			    System.out.println("Status code: "+responseEntity.getStatusCodeValue());
                return ResponseHandler.generateResponse("Deleted! and Status of Employee is changed", HttpStatus.OK, result);
			}
			else{  
				return ResponseHandler.generateResponse("NO data with this id found", HttpStatus.MULTI_STATUS,null); 
		}}
		catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS,null); 
		}
	}
	
	@GetMapping("/status")
	public String getAllEmployeesStatus() {
		return restTemplate.exchange(baseUrl+"getemployee",HttpMethod.GET,null,String.class).getBody();
	}
	
}

