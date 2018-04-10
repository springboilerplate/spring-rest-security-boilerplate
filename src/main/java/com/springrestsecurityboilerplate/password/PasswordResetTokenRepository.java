package com.springrestsecurityboilerplate.password;

import org.springframework.data.repository.CrudRepository;
import com.springrestsecurityboilerplate.user.AppUser;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

	PasswordResetToken findByPasswordResetToken(String token);

	PasswordResetToken findByUser(AppUser user);

}
