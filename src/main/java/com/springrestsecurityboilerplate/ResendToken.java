package com.springrestsecurityboilerplate;

import java.io.Serializable;

import com.springrestsecurityboilerplate.user.AppUser;

public class ResendToken implements Serializable {

	private AppUser user;
	private VerificationToken oldToken;

	public ResendToken(AppUser user, VerificationToken oldToken) {
		super();
		this.user = user;
		this.oldToken = oldToken;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public VerificationToken getOldToken() {
		return oldToken;
	}

	public void setOldToken(VerificationToken oldToken) {
		this.oldToken = oldToken;
	}

}
