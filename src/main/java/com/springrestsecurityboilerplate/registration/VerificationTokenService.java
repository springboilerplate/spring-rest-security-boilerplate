package com.springrestsecurityboilerplate.registration;

import java.util.Date;

public interface VerificationTokenService {

	Date calculateExpiryDate(int expiryTimeInMinutes);

	void saveVerificationToken(VerificationToken verificationToken);

	VerificationToken findVerificationTokenByToken(String verificationToken);

	void deleteVerificationToken(VerificationToken verificationToken);
}
