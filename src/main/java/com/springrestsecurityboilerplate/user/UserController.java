package com.springrestsecurityboilerplate.user;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.springrestsecurityboilerplate.VerificationToken;
import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.UsernameExistsException;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void saveUser(@RequestBody User user, WebRequest request) {

		try {

			userService.registerUser(user, request);
		} catch (EmailExistsException e) {
			System.out.println(e.getMessage());
			// System.out.println(e);
		}

		catch (UsernameExistsException e) {
			System.out.println(e.getMessage());
		}

	}

	@RequestMapping(value = "/confirm/{token}", method = RequestMethod.GET)
	public void confirmRegistration(@PathVariable("token") String token) {

		userService.verifyToken(token);

	}

	@RequestMapping(value = "/resend/{email:.+}", method = RequestMethod.GET)
	public void resendVerificationCode(@PathVariable("email") String email) {

		userService.resendTokenByEmail(email);
	}

}
