package com.springrestsecurityboilerplate.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import com.springrestsecurityboilerplate.user.AppUser;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.springrestsecurityboilerplate.security.SecurityConstants.EXPIRATION_TIME;
import static com.springrestsecurityboilerplate.security.SecurityConstants.HEADER_STRING;
import static com.springrestsecurityboilerplate.security.SecurityConstants.SECRET;
import static com.springrestsecurityboilerplate.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			AppUser creds = new ObjectMapper().readValue(req.getInputStream(), AppUser.class);

			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		
		User user = (User) auth.getPrincipal();
		
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME));
//		claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));

		
		String token = Jwts.builder()
				.setClaims(claims)
				.signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
				.compact();
		
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
		
		
//		String token = Jwts.builder().setSubject(((User) auth.getPrincipal()).getUsername())
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
//		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
		
		

		System.out.println("Bearer token is: " + token);
	}
}
