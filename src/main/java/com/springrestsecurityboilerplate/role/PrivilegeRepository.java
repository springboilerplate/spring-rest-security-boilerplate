package com.springrestsecurityboilerplate.role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
	
	Privilege findByName(String name);

}
