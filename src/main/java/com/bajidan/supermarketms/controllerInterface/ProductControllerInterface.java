package com.bajidan.supermarketms.controllerInterface;

import com.bajidan.supermarketms.dto.product.*;
import com.bajidan.supermarketms.model.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/product")
public interface ProductControllerInterface {
    @PostMapping("/add")
    ResponseEntity<String> addProduct(@Valid @RequestBody AddProduct product);

    @GetMapping("/get")
    ResponseEntity<List<GetProduct>> getAllProduct();

    @PutMapping("/update/{id}")
    ResponseEntity<String> updateProduct(@PathVariable Integer id, @RequestBody AddProduct product);

    @DeleteMapping("delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Integer id);

    @PutMapping("updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody UpdateStatus status);

    @GetMapping("/findById/{id}")
    ResponseEntity<GetProductById> getProductById(@PathVariable Integer id);
    @GetMapping("/findByCategory/{id}")
    ResponseEntity<List<GetProductByCategory>> getProductByCategory(@PathVariable Integer id);





}
