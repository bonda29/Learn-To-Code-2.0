package com.example.learntocode.controllers;


import com.example.learntocode.payload.DTOs.TagDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @PostMapping("/")
    public ResponseEntity<MessageResponse> createTag(@RequestBody TagDto data) {
        return tagService.createTag(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @GetMapping("/")
    public ResponseEntity<List<TagDto>> getAllTags() {
        return tagService.getAllTags();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateTagName(@PathVariable Long id, @RequestBody TagDto data) {
        return tagService.updateTagName(id, data.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteTag(@PathVariable Long id) {
        return tagService.deleteTag(id);
    }
}
