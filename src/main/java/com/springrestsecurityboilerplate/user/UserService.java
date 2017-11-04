package com.springrestsecurityboilerplate.user;

import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UserNameExistsException;

public interface UserService {
	
	void registerUser(User user) throws EmailExistsException,UserNameExistsException;

}
