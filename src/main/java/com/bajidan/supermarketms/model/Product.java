package com.bajidan.supermarketms.model;

import com.bajidan.supermarketms.dto.product.AddProduct;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.access.method.P;

import java.io.Serial;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 123456L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String description;

    private Integer price;

    private boolean status;

    public Product(String name, Category category, String description, Integer price) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
    }

}
