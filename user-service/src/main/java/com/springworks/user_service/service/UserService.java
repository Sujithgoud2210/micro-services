package com.springworks.user_service.service;

import com.springworks.user_service.dto.request.SignupRequestDto;
import com.springworks.user_service.dto.request.UserRequestDto;
import com.springworks.user_service.dto.response.UserResponseDto;
import com.springworks.user_service.dto.response.UserStatusRequestDto;
import com.springworks.user_service.entity.User;
import com.springworks.user_service.exception.UserExistsException;
import com.springworks.user_service.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    UserResponseDto save(SignupRequestDto userRequestDto) throws UserExistsException;
    User findByEmail(String email) throws UserNotFoundException;
    UserResponseDto update(UserRequestDto userRequestDto) throws UserNotFoundException;
    List<UserResponseDto> findAll();
    UserResponseDto findById(int id) throws UserNotFoundException;
    void delete(int id) throws UserNotFoundException;
    boolean updateStatus(int id, UserStatusRequestDto userStatusRequestDto) throws UserNotFoundException;
    boolean existsByEmail(String email);
}
