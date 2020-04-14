package com.stock.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stock.jparepository.SignupRequestRepository;
import com.stock.requests.SignupRequestModel;
import com.stock.utils.TokenProperties;
import com.stock.utils.UserValidations;

@RestController
public class UserManagementController {

	@Autowired
	UserValidations authentication;

	@Autowired
	TokenProperties tokenProps;

	@Autowired
	SignupRequestRepository repo;

	/*
	 * @author Anuvanth
	 * 
	 * @param SignupRequestModel accepts SignupRequestModel which has
	 * Username,password,email,phone,ssn
	 * 
	 * @return String of the response message
	 */
	@PostMapping("/signin")
	public String SignInUser(@RequestBody SignupRequestModel signupRequestModel, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Boolean> authResponse = authentication.AuthenticateUser(signupRequestModel.getUsername(),
				signupRequestModel.getPassword());
		String responseMsg = "";
		for (Entry<String, Boolean> map : authResponse.entrySet()) {
			if (map.getValue() == true) {
				responseMsg = map.getKey();
				// Sending in an empty map of user role acess can be implemented later
				Map<String, Object> claims = new HashMap<String, Object>();
				claims.put("User Role", "Enabled");
				claims.put("Admin Role", "Disabled");
				response.setHeader("JSONToken", tokenProps.GenerateToken(signupRequestModel.getUsername(), claims));
			} else if (map.getValue() == false) {
				responseMsg = map.getKey();
			}
		}
		return responseMsg;
	}

	/*
	 * @author Anuvanth
	 * 
	 * Responds with removed Header
	 * 
	 */
	@PostMapping("/signout")
	public String SignOutUser(HttpServletRequest request, HttpServletResponse response) {
		if (tokenProps.ValidateToken(request.getHeader("Token"))) {
			response.setHeader("JSONToken", "User Logged out Token removed");
			System.out.println("User logged out");
			return "Logged in - removed header from response";
		} else {
			System.out.println("User logged out");
			response.setHeader("JSONToken", "User Logged out Token removed");
			return "User logged out token removed from header";
		}
	}

	/*
	 * @author Anuvanth
	 * 
	 * Signup User adds user data to the data base
	 */
	@PostMapping("/signup")
	public String SignUpUser(@RequestBody SignupRequestModel signupRequestModel, HttpServletRequest request,
			HttpServletResponse response) {

		ArrayList<SignupRequestModel> userTable = (ArrayList<SignupRequestModel>) repo.findAll();

		for (SignupRequestModel users : userTable) {
			if (users.getUsername().equals(signupRequestModel.getUsername())) {
				return "Username already exisits";
			}
		}
		repo.save(signupRequestModel);
		// Sending in an empty map of user role acess can be implemented later
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("User Role", "Enabled");
		claims.put("Admin Role", "Disabled");
		response.setHeader("JSONToken",tokenProps.GenerateToken(signupRequestModel.getUsername(), claims));
		return "User data added to repo token generated to login";
	}
}
