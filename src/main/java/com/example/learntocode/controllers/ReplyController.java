package com.example.learntocode.controllers;

import com.example.learntocode.payload.DTOs.ReplyDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.services.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/replies")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/")
    public ResponseEntity<MessageResponse> createReply(@RequestBody ReplyDto data) {
        return replyService.createReply(data);
    }

    @PostMapping("/{parentId}")
    public ResponseEntity<MessageResponse> createChildReply(@PathVariable Long parentId, @RequestBody ReplyDto data) {
        return replyService.createChildReply(parentId, data);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReplyDto> getReplyById(@PathVariable Long id) {
        return replyService.getReplyById(id);
    }

    @GetMapping("/")
    public ResponseEntity<List<ReplyDto>> getAllReplies() {
        return replyService.getAllReplies();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateReply(@PathVariable Long id, @RequestBody Map<String, String> replyMap) {
        return replyService.updateReply(id, replyMap.get("reply"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteReply(@PathVariable Long id) {
        return replyService.deleteReply(id);
    }
}
