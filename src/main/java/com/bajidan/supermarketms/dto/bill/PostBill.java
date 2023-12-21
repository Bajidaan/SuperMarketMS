package com.bajidan.supermarketms.dto.bill;


import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PostBill {
    private String name;
    private String email;
    private String contactNumber;
    private String paymentMethod;
    private Integer total;
    private List<ProductDetailsDTO> productDetailsDTOList;
}
