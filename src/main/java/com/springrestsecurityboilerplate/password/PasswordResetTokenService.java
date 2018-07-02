package com.springrestsecurityboilerplate.password;

import java.util.Date;

public interface PasswordResetTokenService {

	Date calculateExpiryDate(int expiryTimeInMinutes);

	void savePasswordResetToken(PasswordResetToken passwordResetToken);

	PasswordResetToken findPasswordResetTokenByToken(String passwordResetToken);

	void deleteResetPasswordToken(PasswordResetToken passwordResetToken);

}
