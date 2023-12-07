package com.clem.restapi.controller;

import com.clem.restapi.dto.AuthDto;
import com.clem.restapi.dto.UserDto;
import com.clem.restapi.model.User;
import com.clem.restapi.repository.UserRepository;
import com.clem.restapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserDto request){

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthDto request){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/users")
    public List<User> getAllUser(){
        return repository.findAll();
    }
}
