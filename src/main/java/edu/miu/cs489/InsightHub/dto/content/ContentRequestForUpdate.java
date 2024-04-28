package edu.miu.cs489.InsightHub.dto.content;

public record ContentRequestForUpdate(
        Integer contentId,
        String contentTitle,
        String contentDescription,
        Integer categoryId
) {
}
