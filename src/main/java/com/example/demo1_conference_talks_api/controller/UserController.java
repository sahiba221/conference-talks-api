package com.example.demo1_conference_talks_api.controller;

import com.example.demo1_conference_talks_api.dto.UserRequestDto;
import com.example.demo1_conference_talks_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping({"/users", "/user", "/api/users"})
@Tag(name = "Users", description = "Endpoints for user registration")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user with roles for Spring Security authentication")
    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(dto));
    }
}
