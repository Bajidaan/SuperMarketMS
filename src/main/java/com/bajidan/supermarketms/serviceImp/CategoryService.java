package com.bajidan.supermarketms.serviceImp;

import com.bajidan.supermarketms.dto.category.AddCategory;
import com.bajidan.supermarketms.exeception_handler.CategoryNotFoundException;
import com.bajidan.supermarketms.jwt.JwtFilter;
import com.bajidan.supermarketms.model.Category;
import com.bajidan.supermarketms.repository.CategoryRepository;
import com.bajidan.supermarketms.serviceInterface.CategoryServiceInterface;
import com.bajidan.supermarketms.util.HttpMessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryServiceInterface {

    private final CategoryRepository categoryRepository;
    private final JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addCategory(AddCategory category) {
        try {
            if (jwtFilter.isAdmin()) {
                categoryRepository.save(new Category(category.name()));
                return HttpMessageUtil.successful(category.name() + " was added to category");
            }
            return HttpMessageUtil.unAuthorized("Only Admin is authorize to add category");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return HttpMessageUtil.internalServerError();
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(boolean filter) {
        try {
            if (jwtFilter.isAdmin()) {
                if (filter) {
                    return ResponseEntity.ok(categoryRepository.getAllCategory());
                } else {
                    return ResponseEntity.ok(categoryRepository.findAll());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
    }

    @Override
    public ResponseEntity<String> updateCategory(Integer id, Category categoryUpdated) {
        try {
            Category category = categoryRepository.findById(id).orElseThrow(
                    () -> new CategoryNotFoundException("Category not found")
            );
            category.setName(categoryUpdated.getName());
            if (jwtFilter.isAdmin()) {
                categoryRepository.save(category);
                return HttpMessageUtil.successful("Category updated successfully");
            }
            return HttpMessageUtil.unAuthorized("Access denied");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return HttpMessageUtil.internalServerError();
    }
}
