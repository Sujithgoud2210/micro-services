package com.springworks.user_service.dto.response;


import com.springworks.user_service.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserStatusRequestDto {
    @NotBlank(message = "Status is required")
    private int id;
    @NotBlank(message = "Status is required")
    private Status status;
    @NotBlank(message = "Reason is required")
    private String reason;
}
