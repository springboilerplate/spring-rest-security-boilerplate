package com.springrestsecurityboilerplate.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PrivilegeRepository extends CrudRepository<Privilege, String> {
	
	Privilege findByName(String name);

}