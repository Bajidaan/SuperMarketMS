package com.bajidan.supermarketms.serviceImp;

import com.bajidan.supermarketms.dto.product.*;
import com.bajidan.supermarketms.exeception_handler.CategoryNotFoundException;
import com.bajidan.supermarketms.exeception_handler.ProductNotFoundException;
import com.bajidan.supermarketms.jwt.JwtFilter;
import com.bajidan.supermarketms.model.Category;
import com.bajidan.supermarketms.model.Product;
import com.bajidan.supermarketms.repository.CategoryRepository;
import com.bajidan.supermarketms.repository.ProductRepository;
import com.bajidan.supermarketms.serviceInterface.ProductServiceInterface;
import com.bajidan.supermarketms.util.HttpMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceInterface {
    private final ProductRepository productRepository;
    private final JwtFilter jwtFilter;
    private final CategoryRepository categoryRepository;
    @Override
    public ResponseEntity<String> addProduct(AddProduct product) {
        try {
            Category category = categoryRepository.findById(product.category_id()).orElseThrow();

            Product newProduct = new Product(product.name(), category, product.description(), product.price());
            newProduct.setStatus(true);

            if (jwtFilter.isAdmin()) {
                productRepository.save(newProduct);
                return HttpMessageUtil.successful(newProduct.getName() + " is added successfully");
            } else {
                return HttpMessageUtil.successful("Unauthorized");
            }

        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found");
        }
    }

    @Override
    public ResponseEntity<List<GetProduct>> getProducts() {
        try {
            if (jwtFilter.isAdmin()) {
                return ResponseEntity.ok(mapProductToGetProduct(productRepository.findAll()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       return new  ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> updateProduct(Integer id, AddProduct product) {
        try {
            if (jwtFilter.isAdmin()) {
                Product productToBeUpdated = productRepository.findById(id)
                        .orElseThrow(() -> new ProductNotFoundException("Product id not found"));

                Category category = categoryRepository.findById(product.category_id()).orElseThrow(
                        () -> new CategoryNotFoundException("category not found")
                );

                productToBeUpdated.setName(product.name());
                productToBeUpdated.setCategory(category);
                productToBeUpdated.setDescription(product.description());
                productToBeUpdated.setPrice(product.price());

                productRepository.save(productToBeUpdated);
                return HttpMessageUtil.successful("Product successfully updated");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<String> deleteProduct(Integer id) {
        try {
            if (jwtFilter.isAdmin()) {
                productRepository.deleteById(id);
                return HttpMessageUtil.successful("Product successfully deleted");
            }
        } catch (Exception e) {
            return HttpMessageUtil.badRequest(e.getMessage());
        }
        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<String> updateStatus(UpdateStatus status) {
        try {
            if (jwtFilter.isAdmin()) {
                Product product = productRepository.findById(status.id()).orElseThrow(
                        () -> new ProductNotFoundException("Product not found")
                );
                product.setStatus(status.status());
                productRepository.save(product);

                return HttpMessageUtil.successful("Product status updated");
            }

        } catch (Exception e) {
            return HttpMessageUtil.badRequest(e.getMessage());
        }
        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<GetProductById> getProductById(Integer id) {
        try {
            return new ResponseEntity<>(productRepository.findProductById(id).orElseThrow(),
                        HttpStatus.OK);

        } catch (Exception e) {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public ResponseEntity<List<GetProductByCategory>> getProductByCategory(Integer id) {
        try {
            return new ResponseEntity<>(productRepository.findProductByCategory(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found")),
                    HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public List<GetProduct> mapProductToGetProduct(List<Product> productList) {
        return productList.stream()
                .map(product -> new GetProduct(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.isStatus(),
                        product.getCategory().getId(),
                        product.getCategory().getName())).toList();
    }
}
