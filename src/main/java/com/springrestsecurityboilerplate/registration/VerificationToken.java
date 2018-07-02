package com.springrestsecurityboilerplate.registration;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class VerificationToken implements Serializable {

	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid2")
	private String id;

	private String token;

	@OneToOne(targetEntity = AppUser.class, fetch = FetchType.EAGER, mappedBy = "token")
	// @JoinColumn(nullable = false, name = "id")
	private AppUser user;

	private Date expiryDate;

	public void updateToken(String token, Date expiryDate) {
		this.token = token;
		this.expiryDate = expiryDate;
	}

	public VerificationToken() {
		super();
	}

	public VerificationToken(final String token, Date expiryDate) {
		super();

		this.token = token;
		this.expiryDate = expiryDate;
	}

	public VerificationToken(final AppUser user, final String token, Date expiryDate) {
		super();
		this.user = user;
		this.token = token;
		this.expiryDate = expiryDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}

}
