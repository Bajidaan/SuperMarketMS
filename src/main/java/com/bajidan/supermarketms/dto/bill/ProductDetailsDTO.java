package com.bajidan.supermarketms.dto.bill;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDetailsDTO {
    private Integer id;
    private String name;
    private String category;
    private int quantity;
    private double price;
    private double total;

    public ProductDetailsDTO(Integer id, String name, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.total = price * quantity;
    }

    public double getTotal() {
        return getPrice() * getQuantity();
    }
}
