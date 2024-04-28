package edu.miu.cs489.InsightHub.dto.content;

import edu.miu.cs489.InsightHub.model.Category;
import edu.miu.cs489.InsightHub.model.User;

public record ContentResponse(
        Integer contentId,
        String contentTitle,
        String contentDescription,
        Integer categoryId,
        Integer userId
) {
}
