package com.springrestsecurityboilerplate.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<AppUser,String> {
	
	AppUser findByEmail(String email);
	AppUser findByUsername(String username);

}