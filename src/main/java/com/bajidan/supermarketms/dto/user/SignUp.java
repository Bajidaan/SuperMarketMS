package com.bajidan.supermarketms.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignUp(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 200, message = "invalid")
        String name,
        @Size(min = 11, max = 11, message = "invalid")
        String contactNumber,
        @Email
        String email,
        String password

) {
}
