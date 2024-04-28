package edu.miu.cs489.InsightHub.dto.content;

public record ContentRequest(
        String contentTitle,
        String contentDescription,
        Integer categoryId,
        Integer userId
) {
}
