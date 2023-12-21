package com.bajidan.supermarketms.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Entity
@DynamicInsert
@DynamicUpdate
@Data
@NoArgsConstructor
public class Bill implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;
    private String name;
    private String email;
    private String contactNumber;
    private String paymentMethod;
    private double total;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    private List<ProductDetails> productDetails;

    private String createdBy;

    public Bill(String uuid, String name, String email, String contactNumber,
                String paymentMethod, Integer total, List<ProductDetails> productDetails) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.productDetails = productDetails;
    }
}

