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

import org.hibernate.annotations.GenericGenerator;

import com.springrestsecurityboilerplate.user.AppUser;

@Entity
public class PasswordResetToken implements Serializable {

//	private static final int EXPIRATION = 60 * 24;

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid2")
	private String id;

	private String passwordResetToken;
	@OneToOne(fetch = FetchType.EAGER)
	private AppUser user;

	private Date expiryDate;

	public PasswordResetToken() {
		super();
	}

	public PasswordResetToken(final String token, Date expiryDate) {
		super();

		this.passwordResetToken = token;
		this.expiryDate = expiryDate;
	}

	public PasswordResetToken(final String token, final AppUser user, Date expiryDate) {
		super();

		this.passwordResetToken = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}

	//
	public String getId() {
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

	public void updateToken(final String token, Date expiryDate) {
		this.passwordResetToken = token;
		this.expiryDate = expiryDate;
	}
}
