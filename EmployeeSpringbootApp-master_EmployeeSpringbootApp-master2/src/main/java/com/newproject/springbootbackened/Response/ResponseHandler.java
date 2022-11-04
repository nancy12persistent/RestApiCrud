package com.newproject.springbootbackened.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
	public static ResponseEntity<Object> generateResponse(String Message, HttpStatus status, Object responseObj){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("message", Message);
        map.put("status", status.value());
        map.put("data", responseObj);
		

        return new ResponseEntity<Object>(map,status);
		
	}

}

