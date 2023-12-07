package com.clem.restapi.service;

import com.clem.restapi.controller.AuthenticationResponse;
import com.clem.restapi.dto.AuthDto;
import com.clem.restapi.dto.UserDto;
import com.clem.restapi.model.Role;
import com.clem.restapi.model.User;
import com.clem.restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(UserDto request) {
        var user = User.builder().name(request.getName()).email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow(()-> new UsernameNotFoundException("No user found"));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
