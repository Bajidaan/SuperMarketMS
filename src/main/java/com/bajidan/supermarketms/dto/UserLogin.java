package com.bajidan.supermarketms.dto;

import jakarta.validation.constraints.Email;

public record UserLogin(@Email String email, String password) {
}
