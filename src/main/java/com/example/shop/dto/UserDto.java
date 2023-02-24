package com.example.shop.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDto {

    @NotNull(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name should be between 1 to 30 characters")
    private String firstName;
    @NotNull(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name should be between 1 to 30 characters")
    private String lastName;
    @NotNull(message = "User name is required")
    @Size(min = 5, max = 30, message = "User name should be between 5 to 30 characters")
    private String userName;
    @NotNull(message = "Password name is required")
    @Size(min = 8, max = 30, message = "Password should be between 8 to 30 characters")
    private String password;

}
