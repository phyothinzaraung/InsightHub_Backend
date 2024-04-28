package edu.miu.cs489.InsightHub.controller;

import edu.miu.cs489.InsightHub.dto.content.ContentDeleteResponse;
import edu.miu.cs489.InsightHub.dto.content.ContentRequest;
import edu.miu.cs489.InsightHub.dto.content.ContentRequestForUpdate;
import edu.miu.cs489.InsightHub.dto.content.ContentResponse;
import edu.miu.cs489.InsightHub.exception.ContentNotFoundException;
import edu.miu.cs489.InsightHub.service.ContentService;
import edu.miu.cs489.InsightHub.service.ContentServiceImpl;
import edu.miu.cs489.InsightHub.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_VERSION + "/content")
public class ContentController {

    @Autowired
    ContentService contentService;

    @PostMapping("/register")
    public ResponseEntity<ContentResponse> registerContent(@RequestBody ContentRequest contentRequest){
        return ResponseEntity.ok(contentService.addContent(contentRequest));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ContentResponse>> getAllContents(){
        return ResponseEntity.ok(contentService.getAllContents());
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentResponse> getContentById(@PathVariable Integer contentId) throws ContentNotFoundException {
        return ResponseEntity.ok(contentService.getContentById(contentId));
    }

    @PutMapping("/update")
    public ResponseEntity<ContentResponse> updateContent(@RequestBody ContentRequestForUpdate contentRequestForUpdate){
        return ResponseEntity.ok(contentService.updateContent(contentRequestForUpdate));
    }

    @DeleteMapping("/delete/{contentId}")
    public ResponseEntity<ContentDeleteResponse> deleteContent(@PathVariable Integer contentId){
        return ResponseEntity.ok(contentService.deleteContent(contentId));
    }
}
