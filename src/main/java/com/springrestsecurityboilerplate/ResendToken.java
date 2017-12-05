package com.springrestsecurityboilerplate;

import java.io.Serializable;

import com.springrestsecurityboilerplate.user.User;

public class ResendToken implements Serializable {
	
	private User user;
	private VerificationToken oldToken;
	
	
	
	public ResendToken(User user, VerificationToken oldToken) {
		super();
		this.user = user;
		this.oldToken = oldToken;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public VerificationToken getOldToken() {
		return oldToken;
	}
	public void setOldToken(VerificationToken oldToken) {
		this.oldToken = oldToken;
	}
	
	
	

}
