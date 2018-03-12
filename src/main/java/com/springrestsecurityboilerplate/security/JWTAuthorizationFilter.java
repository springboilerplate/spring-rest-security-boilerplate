package com.springrestsecurityboilerplate.security;

import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.springrestsecurityboilerplate.user.AppUser;
import com.springrestsecurityboilerplate.user.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.springrestsecurityboilerplate.security.SecurityConstants.HEADER_STRING;
import static com.springrestsecurityboilerplate.security.SecurityConstants.SECRET;
import static com.springrestsecurityboilerplate.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	private UserRepository userRepository;

	public JWTAuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);
		System.out.println("doFilterInternal");
		if (header == null || !header.startsWith(TOKEN_PREFIX)) {
			System.out.println("if (header == null || !header.startsWith(TOKEN_PREFIX))");
			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	private void checkUser(String username) {
		AppUser currentUser = null;
		try {

			currentUser = userRepository.findByUsername(username);
		} catch (Exception e) {
			System.out.println(e);
		}

		String foundUsername = "";

		if (currentUser != null) {
			foundUsername = currentUser.getUsername();
		}
		System.out.println("AUTH foundUsername= " + foundUsername);
		if (foundUsername.equals(username)) {
			System.out.println("DB de kayıt var (JWTAuthorizationFilter) ");

		} else
			System.out.println("DB de kayıt yok (JWTAuthorizationFilter) ");
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			String user = Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject();

			// checkUser(user);

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
}