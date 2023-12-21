package com.bajidan.supermarketms.dto.product;

public record GetProductByCategory(Integer id, String name) {

    public GetProductByCategory(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
