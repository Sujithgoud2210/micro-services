package com.springworks.user_service.service.impl;


import com.springworks.user_service.dto.request.LoginRequestDto;
import com.springworks.user_service.dto.request.SignupRequestDto;
import com.springworks.user_service.dto.response.AuthResponseDto;
import com.springworks.user_service.dto.response.UserResponseDto;
import com.springworks.user_service.entity.User;
import com.springworks.user_service.exception.UserExistsException;
import com.springworks.user_service.exception.UserNotFoundException;
import com.springworks.user_service.security.JwtUtil;
import com.springworks.user_service.service.AuthService;
import com.springworks.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    /*
        LOGIN
     */

    @Override
    public AuthResponseDto login(

            LoginRequestDto loginRequestDto

    ) throws UserNotFoundException,
            InvalidCredentialsException {

        /*
            Find User
         */

        User user =
                userService.findByEmail(
                        loginRequestDto.getEmail()
                );

        /*
            Validate Password
         */

        boolean isValidPassword =
                passwordEncoder.matches(

                        loginRequestDto.getPassword(),

                        user.getPassword()
                );

        if(!isValidPassword) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        /*
            Generate Access Token
         */

        String accessToken =
                jwtUtil.generateAccessToken(
                        user
                );

        /*
            Generate Refresh Token
         */

        String refreshToken =
                jwtUtil.generateRefreshToken(
                        user
                );

        /*
            Return Response
         */

        return AuthResponseDto.builder()

                .accessToken(accessToken)

                .refreshToken(refreshToken)

                .user(

                        modelMapper.map(
                                user,
                                UserResponseDto.class
                        )
                )

                .build();
    }

    /*
        SIGNUP
     */

    @Override
    public UserResponseDto signup(

            SignupRequestDto signupRequestDto

    ) throws UserExistsException {

        /*
            Check Existing User
         */



           /* userService.findByEmail(
                    signupRequestDto.getEmail()
            );*/
        if(userService.existsByEmail(signupRequestDto.getEmail())) {
            throw new UserExistsException(
                    "User already exists with email : "
                            + signupRequestDto.getEmail()
            );
        }


        /*
            DTO -> ENTITY
         */





        /*
            Save User
         */

        return userService.save(signupRequestDto);
    }
}
