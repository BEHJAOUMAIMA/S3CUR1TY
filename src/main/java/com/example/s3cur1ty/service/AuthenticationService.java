package com.example.s3cur1ty.service;

import com.example.s3cur1ty.dto.request.SignInRequest;
import com.example.s3cur1ty.dto.request.SignUpRequest;
import com.example.s3cur1ty.dto.response.JwtAuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
