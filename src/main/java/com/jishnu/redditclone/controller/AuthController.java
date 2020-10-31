package com.jishnu.redditclone.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import com.jishnu.redditclone.dto.RegisterRequest;
import com.jishnu.redditclone.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value="/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {

        authService.signup(registerRequest);

        return new ResponseEntity<>("User Registration Succesful", HttpStatus.OK);
        
    }
    
    
}
