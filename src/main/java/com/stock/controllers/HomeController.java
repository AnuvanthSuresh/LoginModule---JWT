package com.stock.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.utils.TokenProperties;

@RestController
public class HomeController {

	@Autowired
	TokenProperties tokenProps;
	
	@GetMapping("/home")
	public String GetAll(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("Token");
		System.out.println("Username from method : "+tokenProps.GetParameterFromToken(token, "username"));
		System.out.println("Expiration status : "+tokenProps.ValidateToken(token));
		return "Home method";
	}
}
