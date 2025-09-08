package com.hospital.Hospital_management.controller;

import com.hospital.Hospital_management.dto.AuthRequestDto;
import com.hospital.Hospital_management.dto.AuthResponseDto;
import com.hospital.Hospital_management.dto.ResponseDto;
import com.hospital.Hospital_management.dto.UserRequestDto;
import com.hospital.Hospital_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto>  registerUser(@RequestBody UserRequestDto requestDto){
        return new ResponseEntity<>(userService.registerUser(requestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto requestDto){
        return ResponseEntity.ok(userService.login(requestDto));
    }

}
