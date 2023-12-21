package com.bajidan.supermarketms.controller;

import com.bajidan.supermarketms.controllerInterface.ProductControllerInterface;
import com.bajidan.supermarketms.dto.product.*;
import com.bajidan.supermarketms.model.Product;
import com.bajidan.supermarketms.serviceImp.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class ProductController implements ProductControllerInterface {
    private final ProductService productService;
    @Override
    public ResponseEntity<String> addProduct(AddProduct product) {
        return productService.addProduct(product);
    }

    @Override
    public ResponseEntity<List<GetProduct>> getAllProduct() {
        return productService.getProducts();
    }

    @Override
    public ResponseEntity<String> updateProduct(Integer id, AddProduct product) {
        return productService.updateProduct(id, product);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        return productService.deleteProduct(id);
    }

    @Override
    public ResponseEntity<String> updateStatus(UpdateStatus status) {
        return productService.updateStatus(status);
    }

    @Override
    public ResponseEntity<GetProductById> getProductById(Integer id) {
        return productService.getProductById(id);
    }

    @Override
    public ResponseEntity<List<GetProductByCategory>> getProductByCategory(Integer id) {
        return productService.getProductByCategory(id);
    }
}
