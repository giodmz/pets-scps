package com.pets.controllers;

import com.pets.dto.AuthDTO;
import com.pets.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthDTO dto) {
        service.register(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO dto) {
        String token = service.login(dto);
        return ResponseEntity.ok(token);
    }
}