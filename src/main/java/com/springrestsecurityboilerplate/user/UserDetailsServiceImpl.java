package com.springrestsecurityboilerplate.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springrestsecurityboilerplate.role.RoleService;
import com.springrestsecurityboilerplate.security.LoginAttemptService;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Service
// @Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;

	private UserRepository applicationUserRepository;
	private RoleService roleService;

	public UserDetailsServiceImpl(UserRepository applicationUserRepository, RoleService roleService) {
		this.applicationUserRepository = applicationUserRepository;
		this.roleService = roleService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		final String ip = getClientIP();
		if (loginAttemptService.isBlocked(ip)) {
			throw new RuntimeException("blocked");
		}

		AppUser applicationUser = applicationUserRepository.findByUsername(username);
		if (applicationUser == null) {
			throw new UsernameNotFoundException(username);
		}

		// //Enum
		// List<GrantedAuthority> authorityList =
		// AuthorityUtils.commaSeparatedStringToAuthorityList(applicationUser.getRoles());
		// return new User(applicationUser.getUsername(), applicationUser.getPassword(),
		// authorityList);

		//
		// List<GrantedAuthority> authorityList =
		// AuthorityUtils.createAuthorityList(applicationUser.getRole().toString());
		// return new User(applicationUser.getUsername(), applicationUser.getPassword(),
		// authorityList);

		// List<GrantedAuthority> authorityList =
		// AuthorityUtils.commaSeparatedStringToAuthorityList(applicationUser.getRoles().toString());
		// return new User(applicationUser.getUsername(), applicationUser.getPassword(),
		// authorityList);

		// Collection<GrantedAuthority> authorityList = (applicationUser.getRoles());

		Collection<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();

		authorityList = (Collection<GrantedAuthority>) roleService.getAuthorities(applicationUser.getRoles());

		return new User(applicationUser.getUsername(), applicationUser.getPassword(), true, true, true, true,
				authorityList);

	}

	private final String getClientIP() {
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}

}
