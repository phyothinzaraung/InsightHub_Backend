package edu.miu.cs489.InsightHub.dto.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Category name cannot be empty or null.")
        String categoryName
) {
}
