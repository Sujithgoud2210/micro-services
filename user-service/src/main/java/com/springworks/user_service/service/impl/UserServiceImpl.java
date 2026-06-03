package com.springworks.user_service.service.impl;


import com.springworks.user_service.dto.request.SignupRequestDto;
import com.springworks.user_service.dto.request.UserRequestDto;
import com.springworks.user_service.dto.response.UserResponseDto;
import com.springworks.user_service.dto.response.UserStatusRequestDto;
import com.springworks.user_service.entity.User;
import com.springworks.user_service.exception.UserExistsException;
import com.springworks.user_service.exception.UserNotFoundException;
import com.springworks.user_service.repository.UserRepository;
import com.springworks.user_service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto save(SignupRequestDto signupRequestDto) throws UserExistsException {
        userRepository.findByEmail(signupRequestDto.getEmail()).ifPresent(user -> {
            throw new UserExistsException("User already exists with email : " + signupRequestDto.getEmail());
        });
        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setLastLoggedIn(null);
        return modelMapper.map(userRepository.save(modelMapper.map(signupRequestDto, User.class)), UserResponseDto.class);
    }

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email : " + email));
    }

    @Override
    public UserResponseDto update(UserRequestDto userRequestDto) throws UserNotFoundException {
        User user = userRepository.findByEmail(userRequestDto.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found with email : " + userRequestDto.getEmail()));

        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());
        user.setUpdatedAt(LocalDateTime.now());

        return modelMapper.map(userRepository.save(user), UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserResponseDto.class)).toList();
    }

    @Override
    public UserResponseDto findById(int id) throws UserNotFoundException {
        return userRepository.findById(id).map(user -> modelMapper.map(user, UserResponseDto.class)).orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
    }

    @Override
    public void delete(int id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public boolean updateStatus(int id, UserStatusRequestDto UserStatusRequestDto) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
        userRepository.updateStatus(id, UserStatusRequestDto.getStatus());
        return true;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
