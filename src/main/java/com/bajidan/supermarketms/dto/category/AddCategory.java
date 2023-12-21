package com.bajidan.supermarketms.dto.category;

import jakarta.validation.constraints.NotBlank;

public record AddCategory(@NotBlank String name) {
}
