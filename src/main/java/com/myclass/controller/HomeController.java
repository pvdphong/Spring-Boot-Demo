package com.myclass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "home")
public class HomeController {
	
	@GetMapping(value = "")
	public String index() {
		return "home/index"; 
	}
}
