package com.springrestsecurityboilerplate;

import java.io.Serializable;

import com.springrestsecurityboilerplate.user.User;

public class RegistrationToken implements Serializable {

	OnRegistrationCompleteEvent event;
	User user;
	String token;
	public OnRegistrationCompleteEvent getEvent() {
		return event;
	}
	public void setEvent(OnRegistrationCompleteEvent event) {
		this.event = event;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public RegistrationToken(OnRegistrationCompleteEvent event, User user, String token) {
		super();
		this.event = event;
		this.user = user;
		this.token = token;
	}
	
	
}
