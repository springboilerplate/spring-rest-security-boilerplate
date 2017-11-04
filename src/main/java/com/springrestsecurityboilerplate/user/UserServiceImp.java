package com.springrestsecurityboilerplate.user;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UsernameExistsException;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public void registerUser(User user) throws EmailExistsException, UsernameExistsException {
		if (isEmailExist(user.getEmail())) {
			// System.out.println("Existed email");
			throw new EmailExistsException(user.getEmail());
		} else if (isUsernameExist(user.getUsername())) {
			throw new UsernameExistsException(user.getUsername());
		}

		else{
			user.setCreationDate(new Date());
			user.setIsActive(false);
			user.setActivationDate(null);
			userRepository.save(user);
			System.out.println("Registered!");
		}
	}

	private boolean isEmailExist(String email) {
		User user = userRepository.findByEmail(email);

		boolean isUserExistByEmail = user != null;
		return isUserExistByEmail;
	}

	private boolean isUsernameExist(String username) {
		User user = userRepository.findByUsername(username);

		boolean isUserExistByUsername = user != null;
		return isUserExistByUsername;
	}

}
