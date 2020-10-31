package com.jishnu.redditclone.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import com.jishnu.redditclone.dto.RegisterRequest;
import com.jishnu.redditclone.model.NotificationEmail;
import com.jishnu.redditclone.model.User;
import com.jishnu.redditclone.model.VerificationToken;
import com.jishnu.redditclone.repository.UserRepository;
import com.jishnu.redditclone.repository.VerificationTokenRepository;

import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    
}
