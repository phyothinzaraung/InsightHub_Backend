package edu.miu.cs489.InsightHub.service;

import edu.miu.cs489.InsightHub.dto.category.CategoryRequest;
import edu.miu.cs489.InsightHub.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse addCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Integer categoryId);
}
