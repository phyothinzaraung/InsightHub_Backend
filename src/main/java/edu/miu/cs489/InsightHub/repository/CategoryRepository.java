package edu.miu.cs489.InsightHub.repository;

import edu.miu.cs489.InsightHub.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT c FROM Category c WHERE c.categoryId = :categoryId")
    public Category findCategoryById(Integer categoryId);
}
