package com.springrestsecurityboilerplate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UserNameExistsException;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public void registerUser(User user) throws EmailExistsException, UserNameExistsException {
		if (emailExist(user.getEmail())) {
			// System.out.println("Existed email");
			throw new EmailExistsException("There is an account with that email address:" + user.getEmail());
		} else if (usernameExist(user.getUsername())) {
			throw new UserNameExistsException("There is an account with that username:" + user.getUsername());
		}

		else{
			userRepository.save(user);
			System.out.println("Registered!");
		}
	}

	private boolean emailExist(String email) {
		User user = userRepository.findByEmail(email);

		if (user != null) {
			return true;
		}

		return false;
	}

	private boolean usernameExist(String username) {
		User user = userRepository.findByUsername(username);

		if (user != null) {
			return true;
		}

		return false;
	}

}
