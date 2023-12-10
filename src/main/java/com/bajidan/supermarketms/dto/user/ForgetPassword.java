package com.bajidan.supermarketms.dto.user;

import jakarta.validation.constraints.Email;

public record ForgetPassword(@Email String email) {
}
