package com.example.learntocode.util;

import jakarta.servlet.http.HttpServletRequest;

public class TokenUtil {
    public static String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }
}
