package com.springrestsecurityboilerplate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.springrestsecurityboilerplate.password.PasswordChange;
import com.springrestsecurityboilerplate.validation.AccountNotFoundException;
import com.springrestsecurityboilerplate.validation.EmailExistsException;
import com.springrestsecurityboilerplate.validation.ExpiredTokenException;
import com.springrestsecurityboilerplate.validation.InvalidTokenException;
import com.springrestsecurityboilerplate.validation.UsernameExistsException;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Object> saveUser(@RequestBody AppUser user, WebRequest request) {

		try {

			userService.registerUser(user, request);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} catch (EmailExistsException e) {

			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
			// System.out.println(e);
		}

		catch (UsernameExistsException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);

		}

	}

	@RequestMapping(value = "/confirm/{token}", method = RequestMethod.GET)
	public ResponseEntity<Object> confirmRegistration(@PathVariable("token") String token) {

		try {
			userService.verifyToken(token);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (InvalidTokenException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (ExpiredTokenException e) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

	}

	@RequestMapping(value = "/resend/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<Object> resendVerificationCode(@PathVariable("email") String email) {

		try {
			userService.resendTokenByEmail(email);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (AccountNotFoundException e) {

			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/resetpassword/{email:.+}", method = RequestMethod.GET)
	public ResponseEntity<Object> resetPassword(@PathVariable("email") String email) {

		try {
			userService.resetPasswordByEmail(email);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@RequestMapping(value = "/resetpasswordform/{token}", method = RequestMethod.POST)
	public ResponseEntity<Object> resetPasswordForm(@RequestBody PasswordChange pswChange,
			@PathVariable("token") String token) {

		System.out.println(pswChange.getPasswordOne());

		try {
			userService.verifyResetPasswordToken(token, pswChange);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (InvalidTokenException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
			// e.printStackTrace();
		} catch (ExpiredTokenException e) {
			e.getMessage();
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@PreAuthorize("hasAuthority('READ_PRIVILEGE')")
	public void testFunction() {

		System.out.println("Test function is executed");

	}

	@RequestMapping(value = "/currentuser", method = RequestMethod.GET)
	public void test() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);

	}

	@RequestMapping(value = "/currentuser2", method = RequestMethod.GET)
	public void test2() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(currentPrincipalName);

		AppUser currentUser = null;
		try {

			currentUser = userRepository.findByUsername(currentPrincipalName);
		} catch (Exception e) {
			// TODO: handle exception
		}

		String foundUsername = "";

		if (currentUser != null) {
			foundUsername = currentUser.getUsername();
		}

		if (foundUsername.equals(currentPrincipalName)) {
			System.out.println("DB contains this user (currentuser2) ");

		} else
			System.out.println("DB does not contain this user (currentuser2) ");

	}

}
