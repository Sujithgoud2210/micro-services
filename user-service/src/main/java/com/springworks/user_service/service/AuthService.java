package com.springworks.user_service.service;


import com.springworks.user_service.dto.request.LoginRequestDto;
import com.springworks.user_service.dto.request.SignupRequestDto;
import com.springworks.user_service.dto.response.AuthResponseDto;
import com.springworks.user_service.dto.response.UserResponseDto;
import com.springworks.user_service.exception.InvalidCredentialsException;
import com.springworks.user_service.exception.UserExistsException;
import com.springworks.user_service.exception.UserNotFoundException;

public interface AuthService {
    AuthResponseDto login(LoginRequestDto loginRequestDto) throws UserNotFoundException, InvalidCredentialsException;
    UserResponseDto signup(SignupRequestDto signupRequestDto) throws UserExistsException;
}
