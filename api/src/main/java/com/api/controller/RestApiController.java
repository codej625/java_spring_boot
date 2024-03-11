package com.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiController {
	
	@PostMapping(value = "/test")
	public String testApi() throws Exception {
		
		return "test";
	}

}
