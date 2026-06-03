package com.springworks.user_service.dto.response;

import com.springworks.user_service.enums.Role;
import com.springworks.user_service.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private int id;
    private String name;
    private String email;
    private Role role;
    private Status status;
}
