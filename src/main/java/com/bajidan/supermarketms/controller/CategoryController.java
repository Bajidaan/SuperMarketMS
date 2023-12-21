package com.bajidan.supermarketms.controller;

import com.bajidan.supermarketms.controllerInterface.CategoryControllerInterface;
import com.bajidan.supermarketms.dto.category.AddCategory;
import com.bajidan.supermarketms.model.Category;
import com.bajidan.supermarketms.serviceImp.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerInterface {

    private final CategoryService categoryService;
    @Override
    public ResponseEntity<String> addCategory(AddCategory category) {
        return categoryService.addCategory(category);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(boolean filter) {

        return categoryService.getAllCategory(filter);
    }

    @Override
    public ResponseEntity<String> updateCategory(Integer id, Category category) {
        return categoryService.updateCategory(id, category);
    }

}
