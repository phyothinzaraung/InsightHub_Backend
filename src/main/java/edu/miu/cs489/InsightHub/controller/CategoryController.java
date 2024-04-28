package edu.miu.cs489.InsightHub.controller;

import edu.miu.cs489.InsightHub.dto.category.CategoryRequest;
import edu.miu.cs489.InsightHub.dto.category.CategoryResponse;
import edu.miu.cs489.InsightHub.service.CategoryService;
import edu.miu.cs489.InsightHub.util.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_VERSION + "/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping("/register")
    public ResponseEntity<CategoryResponse> registerCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        return ResponseEntity.ok(categoryService.addCategory(categoryRequest));
    }

}
