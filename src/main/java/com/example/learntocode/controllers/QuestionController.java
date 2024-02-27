package com.example.learntocode.controllers;


import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.services.QuestionService;
import lombok.RequiredArgsConstructor;
import com.example.learntocode.payload.DTOs.QuestionDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/")
    public ResponseEntity<MessageResponse> createQuestion(@RequestBody QuestionDto data) {
        return questionService.createQuestion(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @GetMapping("/")
    public ResponseEntity<List<QuestionDto>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateQuestion(@PathVariable Long id, @RequestBody QuestionDto data) {
        return questionService.updateQuestion(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteQuestion(@PathVariable Long id) {
        return questionService.deleteQuestion(id);
    }
}
