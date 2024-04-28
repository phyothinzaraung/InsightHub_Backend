package edu.miu.cs489.InsightHub.service;

import edu.miu.cs489.InsightHub.dto.content.ContentDeleteResponse;
import edu.miu.cs489.InsightHub.dto.content.ContentRequest;
import edu.miu.cs489.InsightHub.dto.content.ContentRequestForUpdate;
import edu.miu.cs489.InsightHub.dto.content.ContentResponse;
import edu.miu.cs489.InsightHub.exception.ContentNotFoundException;

import java.util.List;

public interface ContentService {
    ContentResponse addContent(ContentRequest contentRequest);
    List<ContentResponse> getAllContents();
    ContentResponse getContentById(Integer contentId) throws ContentNotFoundException;
    ContentResponse updateContent(ContentRequestForUpdate contentRequest);
    ContentDeleteResponse deleteContent(Integer contentId);
}
