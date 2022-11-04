package com.newproject.springbootbackened.ResponseHandler2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.HashMap;
import java.util.Map;


public class ResponseHandler2 {

	public static ResponseEntity<Object> generateResponse(String Message, HttpStatus status){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("message", Message);
        map.put("status", status.value());
     
		

        return new ResponseEntity<Object>(map,status);
		
	}

}

