package com.springrestsecurityboilerplate.registration;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springrestsecurityboilerplate.user.AppUser;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {

	VerificationToken findByToken(String token);

	VerificationToken findByUser(AppUser user);
}
