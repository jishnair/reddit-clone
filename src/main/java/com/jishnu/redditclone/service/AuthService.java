package com.jishnu.redditclone.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.jishnu.redditclone.dto.AuthenticationResponse;
import com.jishnu.redditclone.dto.LoginRequest;
import com.jishnu.redditclone.dto.RegisterRequest;
import com.jishnu.redditclone.exception.SpringRedditException;
import com.jishnu.redditclone.model.NotificationEmail;
import com.jishnu.redditclone.model.User;
import com.jishnu.redditclone.model.VerificationToken;
import com.jishnu.redditclone.repository.UserRepository;
import com.jishnu.redditclone.repository.VerificationTokenRepository;
import com.jishnu.redditclone.security.JwtProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user= new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        
        log.info(user.toString());

        userRepository.save(user);
        String token= generateVerificationToken(user);

        mailService.sendMail(new NotificationEmail("Please Activate your Account",
        user.getEmail(), "Thank you for signing up to Spring Reddit, " +
        "please click on the below url to activate your account : " +
        "http://localhost:8080/api/auth/accountVerification/" + token));

    }

    private String generateVerificationToken(User user){
        String token=UUID.randomUUID().toString();
        VerificationToken verificationToken= new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;
    }

	public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken= verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable( verificationToken.get());
    }
    
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken){
        String username=verificationToken.getUser().getUsername();
        User user=userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found -"+username));
        user.setEnabled(true);
        userRepository.save(user);
    }

	public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token= jwtProvider.generateToken(authenticate);

        return new AuthenticationResponse(token, loginRequest.getUsername());
        
	}
    
}
