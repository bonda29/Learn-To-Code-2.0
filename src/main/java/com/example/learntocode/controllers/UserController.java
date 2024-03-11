package com.example.learntocode.controllers;

import com.example.learntocode.payload.DTOs.QuestionDto;
import com.example.learntocode.payload.DTOs.UserDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateUser(@PathVariable Long id, @RequestBody UserDto data) {
        return userService.updateUser(id, data);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<List<QuestionDto>> getQuestionsByUserId(@PathVariable Long id) {
        return userService.getQuestionsByUserId(id);
    }
}
