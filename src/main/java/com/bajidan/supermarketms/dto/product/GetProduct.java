package com.bajidan.supermarketms.dto.product;

public record GetProduct(
        Integer id,
        String name,
         String description,
         Integer price,
         boolean status,
         Integer categoryId,
        String categoryName
) {
}
