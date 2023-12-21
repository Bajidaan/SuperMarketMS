package com.bajidan.supermarketms.repository;

import com.bajidan.supermarketms.dto.product.GetProductByCategory;
import com.bajidan.supermarketms.dto.product.GetProductById;
import com.bajidan.supermarketms.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query("SELECT new com.bajidan.supermarketms.dto.product.GetProductById(p.id, p.name, p.description, p.price)" +
            " FROM Product p WHERE p.id=:id")
    Optional<GetProductById> findProductById(@Param("id") Integer id);

    @Query("SELECT new com.bajidan.supermarketms.dto.product.GetProductByCategory(p.id, p.name) " +
            "FROM Product p WHERE p.category.id=:id AND p.status=true ")
  Optional<List<GetProductByCategory>> findProductByCategory(@Param("id") Integer id);
}
