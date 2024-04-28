package edu.miu.cs489.InsightHub;

import edu.miu.cs489.InsightHub.dto.category.CategoryRequest;
import edu.miu.cs489.InsightHub.dto.category.CategoryResponse;
import edu.miu.cs489.InsightHub.model.Category;
import edu.miu.cs489.InsightHub.repository.CategoryRepository;
import edu.miu.cs489.InsightHub.service.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    private Category category;
    private CategoryResponse categoryResponse;

    @BeforeEach
    void setUp(){
        category = new Category(1, "Technology");
        categoryResponse = new CategoryResponse(1, "Technology");
    }

    @Test
    void getAllCategories_shouldReturnListOfCategoriesResponse(){
        when (categoryRepository.findAll()).thenReturn( Arrays.asList(category));
        List<CategoryResponse> categoryResponseList = categoryService.getAllCategories();
        assertEquals(1, categoryResponseList.size());
        assertEquals(categoryResponse, categoryResponseList.get(0));
        verify(categoryRepository).findAll();
    }

    @Test
    void getCategoryById_shouldReturnOneCategory(){
        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        CategoryResponse categoryResult = categoryService.getCategoryById(1);
        assertEquals(category.getCategoryId(), categoryResult.categoryId());
        verify(categoryRepository).findById(1);

    }

    @Test
    void addCategory_shouldReturnResponseCategory(){
        CategoryRequest categoryRequest = new CategoryRequest("Technology");
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        CategoryResponse response = categoryService.addCategory(categoryRequest);
        assertEquals(1, response.categoryId());
        assertEquals("Technology", response.categoryName());
    }
}
