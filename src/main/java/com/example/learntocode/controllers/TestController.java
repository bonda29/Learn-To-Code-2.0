package com.example.learntocode.controllers;

import com.example.learntocode.models.User;
import com.example.learntocode.services.auth0.Auth0Client;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final Auth0Client auth0Client;

    @RequestMapping("/users")
    public User getUsers(@RequestParam String email) {
        return auth0Client.getUserByEmail(email);
    }

}
