package com.bajidan.supermarketms.serviceInterface;

import com.bajidan.supermarketms.dto.category.AddCategory;
import com.bajidan.supermarketms.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryServiceInterface {

    ResponseEntity<String> addCategory(AddCategory category);

    ResponseEntity<List<Category>> getAllCategory(boolean filter);

    ResponseEntity<String> updateCategory(Integer id, Category category);
}
