package com.springrestsecurityboilerplate.registration;

import java.io.Serializable;

import com.springrestsecurityboilerplate.user.AppUser;

public class RegistrationToken implements Serializable {

	OnRegistrationCompleteEvent event;
	AppUser user;
	String token;

	public OnRegistrationCompleteEvent getEvent() {
		return event;
	}

	public void setEvent(OnRegistrationCompleteEvent event) {
		this.event = event;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public RegistrationToken(OnRegistrationCompleteEvent event, AppUser user, String token) {
		super();
		this.event = event;
		this.user = user;
		this.token = token;
	}

}
