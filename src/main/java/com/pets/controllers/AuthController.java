package com.pets.controllers;

import com.pets.dto.AuthDTO;
import com.pets.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @Operation(summary = "Register a user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User successfully registered"),
            @ApiResponse(responseCode = "404", description = "Invalid data")
    })
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody AuthDTO dto) {
        service.register(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "User login")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Logged in"),
            @ApiResponse(responseCode = "404", description = "Invalid data")
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO dto) {
        String token = service.login(dto);
        return ResponseEntity.ok(token);
    }
}