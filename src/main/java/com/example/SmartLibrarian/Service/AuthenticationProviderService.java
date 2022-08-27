package com.example.SmartLibrarian.Service;

import com.example.SmartLibrarian.Model.Attempts;
import com.example.SmartLibrarian.Model.UserMaster;
import com.example.SmartLibrarian.Repository.AttemptsRepository;
import com.example.SmartLibrarian.Repository.UserMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component public class AuthenticationProviderService implements AuthenticationProvider {
    private static final int ATTEMPTS_LIMIT = 3;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AttemptsRepository attemptsRepository;
    @Autowired private UserMasterRepository userMasterRepository;
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getName();
        Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(username);
        if (!userAttempts.isPresent()) {
            Attempts attempts = new Attempts();
            attempts.setAttempts(0);
            attempts.setUsername(username);
            attemptsRepository.save(attempts);
        }
        
        String password = authentication.getCredentials().toString();
        return checkPassword(username, password, authentication);
    }

    private Authentication checkPassword(String username, String password, Authentication authentication) {
        UserMaster user = userMasterRepository.findByUsername(username);

        if(passwordEncoder.matches(password, user.getPassword())) {
        	updateUserDetails(user);
            return new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
        } else {
            processFailedAttempts(username, user);
            throw new BadCredentialsException("UserName Password Mismatch Exception");
        }

    }

    private void updateUserDetails(UserMaster user) {
		
    	user.setLastLoginTime(LocalDateTime.now());
    	userMasterRepository.save(user);
    	
    	Optional<Attempts> userAttempts = attemptsRepository.findAttemptsByUsername(user.getUsername());
    	if(userAttempts.isPresent()) {
    		Attempts a = userAttempts.get();
    		a.setAttempts(0);
    		attemptsRepository.save(a);
    	}
		
	}

	private void processFailedAttempts(String username, UserMaster user) {
        Optional<Attempts>
                userAttempts = attemptsRepository.findAttemptsByUsername(username);
        if (!userAttempts.isPresent()) {
            Attempts attempts = new Attempts();
            attempts.setUsername(username);
            attempts.setAttempts(1);
            attemptsRepository.save(attempts);
        } else {
            Attempts attempts = userAttempts.get();
            attempts.setAttempts(attempts.getAttempts() + 1);
            attemptsRepository.save(attempts);

            if (attempts.getAttempts() + 1 > ATTEMPTS_LIMIT) {
                user.setAccountNonLocked(false);
                userMasterRepository.save(user);
                throw new LockedException("Too many invalid attempts. Account is locked!!");
            }
        }
    }
    
    @Override public boolean supports(Class<?> authentication) {

        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}