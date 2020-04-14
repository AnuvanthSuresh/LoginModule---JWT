package com.stock.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.jparepository.SignupRequestRepository;
import com.stock.requests.*;

@Service
public class UserValidations {

	@Autowired
	SignupRequestRepository repo;

	/*
	 * @author Anuvanth
	 * 
	 * @param String , String
	 * 
	 * username - in plain string onces a UI is built its hashed in there password -
	 * When a UI is built its also hashed or can be uncrypted using Bcrypt
	 *
	 * @Return Map with Response String and Status
	 */
	public Map<String, Boolean> AuthenticateUser(String username, String password) {

		System.out.println("Authenticate User method called");
		Boolean flag = false;
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		ArrayList<SignupRequestModel> userTable = (ArrayList<SignupRequestModel>) repo.findAll();

		for (SignupRequestModel users : userTable) {
			if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
				System.out.println("User authenticated");
				map.put("User authenticated in authenticate user method", true);
				flag = true;
			} else {
				flag = false;
			}
		}

		if (flag) {
			return map;
		} else {
			System.out.println("User authentication failed");
			map.put("Unable to authenticate user in authenticate method", false);
			return map;
		}
	}
}
