package com.bajidan.supermarketms.dto.product;

import com.bajidan.supermarketms.model.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record AddProduct(
        @NotBlank @Length(min = 3)
        String name,
        @NotNull
        Integer category_id,
        String description,
        @Min(value = 0)
        Integer price) {
}
