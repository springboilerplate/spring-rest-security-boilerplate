package com.springrestsecurityboilerplate.password;

import java.io.Serializable;

import com.springrestsecurityboilerplate.registration.VerificationToken;
import com.springrestsecurityboilerplate.user.AppUser;

public class PasswordChange implements Serializable {
	//

	private String passwordOne;
	private String passwordTwo;
	private String oldPassword;

	public String getPasswordOne() {
		return passwordOne;
	}

	public void setPasswordOne(String passwordOne) {
		this.passwordOne = passwordOne;
	}

	public String getPasswordTwo() {
		return passwordTwo;
	}

	public void setPasswordTwo(String passwordTwo) {
		this.passwordTwo = passwordTwo;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
