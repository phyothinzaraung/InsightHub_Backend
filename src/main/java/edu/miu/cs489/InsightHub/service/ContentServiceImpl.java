package edu.miu.cs489.InsightHub.service;

import edu.miu.cs489.InsightHub.dto.content.ContentDeleteResponse;
import edu.miu.cs489.InsightHub.dto.content.ContentRequest;
import edu.miu.cs489.InsightHub.dto.content.ContentRequestForUpdate;
import edu.miu.cs489.InsightHub.dto.content.ContentResponse;
import edu.miu.cs489.InsightHub.exception.ContentNotFoundException;
import edu.miu.cs489.InsightHub.model.Category;
import edu.miu.cs489.InsightHub.model.Content;
import edu.miu.cs489.InsightHub.model.User;
import edu.miu.cs489.InsightHub.repository.CategoryRepository;
import edu.miu.cs489.InsightHub.repository.ContentRepository;
import edu.miu.cs489.InsightHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ContentResponse addContent(ContentRequest contentRequest) {
        Content newContent = new Content();
        newContent.setContentTitle(contentRequest.contentTitle());
        newContent.setContentDescription(contentRequest.contentDescription());
        Integer categoryId = contentRequest.categoryId();
        Category category = categoryRepository.findCategoryById(categoryId);
        newContent.setCategory(category);
        Integer userId = contentRequest.userId();
        User user = userRepository.findUserById(userId);
        newContent.setUser(user);

        Content savedContent = contentRepository.save(newContent);
        return new ContentResponse(
                savedContent.getContentId(),
                savedContent.getContentTitle(),
                savedContent.getContentDescription(),
                savedContent.getCategory().getCategoryId(),
                savedContent.getUser().getUserId());
    }

    @Override
    public List<ContentResponse> getAllContents() {
        return contentRepository.findAll()
                .stream()
                .map(content -> new ContentResponse(
                        content.getContentId(),
                        content.getContentTitle(),
                        content.getContentDescription(),
                        content.getCategory().getCategoryId(),
                        content.getUser().getUserId()
                )).toList();
    }

    @Override
    public ContentResponse getContentById(Integer contentId) throws ContentNotFoundException {
        Optional<Content> contentOptional = contentRepository.findById(contentId);
        if (contentOptional.isPresent()){
            Content content = contentOptional.get();
            return new ContentResponse(
                    content.getContentId(),
                    content.getContentTitle(),
                    content.getContentDescription(),
                    content.getCategory().getCategoryId(),
                    content.getUser().getUserId()
            );
        }else {
            throw new ContentNotFoundException(String.format("Error: content with Id - %d, is not found.", contentId));
        }
    }

    @Override
    public ContentResponse updateContent(ContentRequestForUpdate contentRequest) {
        Content content = contentRepository.findById(contentRequest.contentId()).orElse(null);
        if (content != null){
            content.setContentTitle(contentRequest.contentTitle());
            content.setContentDescription(contentRequest.contentDescription());
            content.setCategory(content.getCategory());
            Content updateContent = contentRepository.save(content);
            return new ContentResponse(
                    updateContent.getContentId(),
                    updateContent.getContentTitle(),
                    updateContent.getContentDescription(),
                    updateContent.getCategory().getCategoryId(),
                    updateContent.getUser().getUserId()
            );
        }else {
            return null;
        }
    }

    @Override
    public ContentDeleteResponse deleteContent(Integer contentId) {
        contentRepository.deleteById(contentId);
        return new ContentDeleteResponse(
                String.format("Content with Id - %d deleted successfully.", contentId)
        );
    }
}
