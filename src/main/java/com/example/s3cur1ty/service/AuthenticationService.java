package com.example.s3cur1ty.service;

import com.example.s3cur1ty.dto.request.SignUpRequest;
import com.example.s3cur1ty.dto.request.SigninRequest;
import com.example.s3cur1ty.dto.response.JwtAuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SigninRequest request);
}
