package edu.miu.cs489.InsightHub.service;

import edu.miu.cs489.InsightHub.dto.category.CategoryRequest;
import edu.miu.cs489.InsightHub.dto.category.CategoryResponse;
import edu.miu.cs489.InsightHub.model.Category;
import edu.miu.cs489.InsightHub.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryRequest.categoryName());

        Category category = categoryRepository.save(newCategory);

        return new CategoryResponse(
                category.getCategoryId(),
                category.getCategoryName()
        );
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(category -> new CategoryResponse(
                        category.getCategoryId(),
                        category.getCategoryName()
                )).toList();
    }

    @Override
    public CategoryResponse getCategoryById(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()){
            Category category =optionalCategory.get();
            return new CategoryResponse(
                    category.getCategoryId(),
                    category.getCategoryName()
            );
        }else {
            return null;
        }
    }
}
