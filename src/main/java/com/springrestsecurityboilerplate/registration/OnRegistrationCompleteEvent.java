package com.springrestsecurityboilerplate.registration;

import java.io.Serializable;
import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.springrestsecurityboilerplate.user.AppUser;

public class OnRegistrationCompleteEvent extends ApplicationEvent implements Serializable {

	private String appUrl;
	private Locale locale;
	private AppUser user;

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public OnRegistrationCompleteEvent(AppUser user) {
		super(user);
		this.user = user;

	}

	public OnRegistrationCompleteEvent(AppUser user, Locale locale, String appUrl) {
		super(user);

		this.user = user;
		this.locale = locale;
		this.appUrl = appUrl;
	}

}
