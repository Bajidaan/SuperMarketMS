package com.bajidan.supermarketms.repository;

import com.bajidan.supermarketms.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(
            "SELECT c FROM Category c WHERE c.id IN " +
            "(SELECT p.category FROM Product p WHERE p.status=true)"
    )

    @Transactional
    List<Category> getAllCategory();
}
