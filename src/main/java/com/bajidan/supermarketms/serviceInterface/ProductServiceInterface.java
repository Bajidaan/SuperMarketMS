package com.bajidan.supermarketms.serviceInterface;

import com.bajidan.supermarketms.dto.product.*;
import com.bajidan.supermarketms.model.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductServiceInterface {
    ResponseEntity<String> addProduct(AddProduct product);

    ResponseEntity<List<GetProduct>> getProducts();

    ResponseEntity<String> updateProduct(Integer id, AddProduct product);

    ResponseEntity<String> deleteProduct(Integer id);

    ResponseEntity<String> updateStatus(UpdateStatus status);

    ResponseEntity<GetProductById> getProductById(Integer id);

    ResponseEntity<List<GetProductByCategory>> getProductByCategory(Integer id);
}
