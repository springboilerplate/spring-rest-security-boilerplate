package com.springrestsecurityboilerplate.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent e) {
        // final WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
        // if (auth != null) {
        // loginAttemptService.loginFailed(auth.getRemoteAddress());
        // }
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            loginAttemptService.loginFailed(request.getRemoteAddr());
            System.out.println("AuthenticationFailureListener null");
        } else {
            loginAttemptService.loginFailed(xfHeader.split(",")[0]);
            System.out.println("AuthenticationFailureListener xfHeader.split(\",\")[0] null");
        }
    }

}