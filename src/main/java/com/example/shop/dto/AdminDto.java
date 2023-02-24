package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto {

    @NotNull(message = "First name is required")
    @Length(min = 2, max = 30, message = "First name should be between 2 to 30 characters")
    private String firstName;
    @NotNull(message = "Last name is required")
    @Length(min = 2, max = 30, message = "Last name should be between 2 to 30 characters")
    private String lastName;
    @NotNull(message = "User name is required")
    @Size(min = 5, max = 18, message = "User name should be between 5 to 18 characters")
    private String userName;
}
