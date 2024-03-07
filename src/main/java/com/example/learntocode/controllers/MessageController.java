package com.example.learntocode.controllers;

import com.example.learntocode.payload.messages.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    @GetMapping("/public")
    public MessageResponse getPublic() {
        return MessageResponse.from("This is a public message.");
    }

    @GetMapping("/protected")
    public MessageResponse getProtected(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        System.out.println(map.get("authorization"));

        return MessageResponse.from("This is a protected message.");
    }

    @GetMapping("/admin")
    public MessageResponse getAdmin() {
        return MessageResponse.from("This is an admin message.");
    }
}
