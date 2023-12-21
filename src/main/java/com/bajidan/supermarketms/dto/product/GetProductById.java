package com.bajidan.supermarketms.dto.product;

public record GetProductById(Integer id, String name, String description, Integer price) {

    public GetProductById(Integer id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
