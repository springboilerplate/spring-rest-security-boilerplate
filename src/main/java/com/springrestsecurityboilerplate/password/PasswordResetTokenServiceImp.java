package com.springrestsecurityboilerplate.password;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenServiceImp implements PasswordResetTokenService {

	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Override
	public Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	@Override
	public void savePasswordResetToken(PasswordResetToken passwordResetToken) {
		passwordResetTokenRepository.save(passwordResetToken);

	}

	@Override
	public PasswordResetToken findPasswordResetTokenByToken(String passwordResetToken) {
		return passwordResetTokenRepository.findByPasswordResetToken(passwordResetToken);
	}

	@Override
	public void deleteResetPasswordToken(PasswordResetToken passwordResetToken) {
		passwordResetTokenRepository.delete(passwordResetToken);

	}

}
