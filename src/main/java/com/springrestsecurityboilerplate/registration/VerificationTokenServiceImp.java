package com.springrestsecurityboilerplate.registration;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImp implements VerificationTokenService {

	@Autowired
	VerificationTokenRepository verificationTokenRepository;

	@Override
	public void saveVerificationToken(VerificationToken verificationToken) {
		verificationTokenRepository.save(verificationToken);
	}

	@Override
	public VerificationToken findVerificationTokenByToken(String verificationToken) {
		return verificationTokenRepository.findByToken(verificationToken);
	}

	@Override
	public void deleteVerificationToken(VerificationToken verificationToken) {
		verificationTokenRepository.delete(verificationToken);
	}

	@Override
	public Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

}
