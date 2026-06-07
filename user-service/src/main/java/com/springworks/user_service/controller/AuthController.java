package com.springworks.user_service.controller;


import com.springworks.user_service.dto.request.LoginRequestDto;
import com.springworks.user_service.dto.request.SignupRequestDto;
import com.springworks.user_service.dto.response.AuthResponseDto;
import com.springworks.user_service.dto.response.UserResponseDto;
import com.springworks.user_service.exception.InvalidCredentialsException;
import com.springworks.user_service.exception.UserExistsException;
import com.springworks.user_service.exception.UserNotFoundException;
import com.springworks.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/auth")

@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;

    /*
        LOGIN
     */

    @PostMapping("/login")

    public ResponseEntity<AuthResponseDto>
    login(

            @RequestBody
            LoginRequestDto loginRequestDto

    ) throws UserNotFoundException,
            InvalidCredentialsException {

        return ResponseEntity.ok(

                authService.login(
                        loginRequestDto
                )
        );
    }

    /*
        SIGNUP
     */

    @PostMapping("/signup")

    public ResponseEntity<UserResponseDto>
    signup(

            @RequestBody
            SignupRequestDto signupRequestDto

    ) throws UserExistsException {

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(

                        authService.signup(
                                signupRequestDto
                        )
                );
    }
}
