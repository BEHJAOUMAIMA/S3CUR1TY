package com.example.s3cur1ty.service.impl;

import com.example.s3cur1ty.domain.User;
import com.example.s3cur1ty.domain.enums.UserRole;
import com.example.s3cur1ty.dto.request.SignInRequest;
import com.example.s3cur1ty.dto.request.SignUpRequest;
import com.example.s3cur1ty.dto.response.JwtAuthenticationResponse;
import com.example.s3cur1ty.repository.UserRepository;
import com.example.s3cur1ty.service.AuthenticationService;
import com.example.s3cur1ty.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        UserRole role = switch (request.getRole()) {
            case "ADMIN" -> UserRole.ADMIN;
            case "SUPER_ADMIN" -> UserRole.SUPER_ADMIN;
            default -> UserRole.USER;
        };

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Email or password !"));

        String jwt = jwtService.generateToken(user);

        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
