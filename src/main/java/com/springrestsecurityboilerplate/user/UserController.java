package com.springrestsecurityboilerplate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UserNameExistsException;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void saveUser(@RequestBody User user) {
		
		try{
			userService.registerUser(user);
		}
		catch(EmailExistsException e){
			System.out.println(e.getMessage());
//			System.out.println(e);
		}
		
		catch(UserNameExistsException e)
		{
			System.out.println(e.getMessage());
		}

	}

}
