package com.springrestsecurityboilerplate.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.transaction.annotation.Transactional;

import com.springrestsecurityboilerplate.password.PasswordResetToken;
import com.springrestsecurityboilerplate.registration.VerificationToken;
import com.springrestsecurityboilerplate.role.Role;
import com.springrestsecurityboilerplate.validation.ValidEmail;

@Entity
@Table(name = "user")
public class AppUser implements Serializable {

	@Id
	@GeneratedValue(generator = "hibernate-uuid")
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid2")
	// @GeneratedValue(strategy = GenerationType.AUTO)
	// @Column(columnDefinition = "CHAR(64)")
	private String id;
	private String name;
	private String surname;
	private String phone;
	@NotNull
	private String username;
	@NotNull
	private String password;
	@ValidEmail // @Email
	@NotEmpty
	@NotNull
	private String email;
	private String address;
	@Temporal(TemporalType.DATE)
	private Date creationDate;
	@Temporal(TemporalType.DATE)
	private Date activationDate;
	private Boolean isActive;
	// private String roles;

	@OneToOne(fetch = FetchType.EAGER)
	private VerificationToken token;

	@OneToOne(targetEntity = PasswordResetToken.class, fetch = FetchType.EAGER)
	// @JoinColumn(nullable = false, name = "appusder_id")
	private PasswordResetToken passwordResetToken;

	// @OneToOne(fetch = FetchType.EAGER)
	// private Rolev2 role;

	@ManyToMany(fetch = FetchType.EAGER)
	// @LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "appuser_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	// public Rolev2 getRole() {
	// return role;
	// }

	// public void setRole(Rolev2 role) {
	// this.role = role;
	// }

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public VerificationToken getToken() {
		return token;
	}

	public void setToken(VerificationToken token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public PasswordResetToken getPasswordResetToken() {
		return passwordResetToken;
	}

	public void setPasswordResetToken(PasswordResetToken passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}
	// public String getRoles() {
	// return roles;
	// }
	// public void setRoles(String roles) {
	// this.roles = roles;
	// }

}