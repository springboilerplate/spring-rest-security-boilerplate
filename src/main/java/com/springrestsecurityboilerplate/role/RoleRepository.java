package com.springrestsecurityboilerplate.role;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {

	Role findByName(String name);

}