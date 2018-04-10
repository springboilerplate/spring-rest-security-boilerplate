package com.springrestsecurityboilerplate.password;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.springrestsecurityboilerplate.user.AppUser;

@Entity
public class PasswordResetToken implements Serializable {

	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String passwordResetToken;
	@OneToOne(fetch = FetchType.EAGER)
	private AppUser user;

	private Date expiryDate;

	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(final String token) {
		super();

		this.passwordResetToken = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public PasswordResetToken(final String token, final AppUser user) {
		super();

		this.passwordResetToken = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	//
	public Long getId() {
		return id;
	}

	public String getToken() {
		return passwordResetToken;
	}

	public void setToken(final String token) {
		this.passwordResetToken = token;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(final AppUser user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(final Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	public void updateToken(final String token) {
		this.passwordResetToken = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
}
