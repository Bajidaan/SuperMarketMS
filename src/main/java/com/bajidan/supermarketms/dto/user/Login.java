package com.bajidan.supermarketms.dto.user;

import jakarta.validation.constraints.Email;

public record Login(@Email String email, String password) {
}
