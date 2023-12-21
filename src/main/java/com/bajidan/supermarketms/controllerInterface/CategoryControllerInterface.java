package com.bajidan.supermarketms.controllerInterface;

import com.bajidan.supermarketms.dto.category.AddCategory;
import com.bajidan.supermarketms.model.Category;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
public interface CategoryControllerInterface {
    @PostMapping("/add")
    ResponseEntity<String> addCategory(@Valid @RequestBody AddCategory category);

    @GetMapping("/get")
    ResponseEntity<List<Category>> getAllCategory(@RequestParam(required = false)
                                                  boolean filter);
    @PutMapping("/update/{id}")
    ResponseEntity<String> updateCategory(@PathVariable Integer id, @RequestBody Category category);


}
