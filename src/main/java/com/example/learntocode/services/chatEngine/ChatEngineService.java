package com.example.learntocode.services.chatEngine;


import com.example.learntocode.models.User;
import com.example.learntocode.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class ChatEngineService {
    private final static String secret = "password";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    @Value("${chatengine.api.url}")
    private String apiUrl;

    @Value("${chatengine.project.id}")
    private String projectId;

    @Value("${chatengine.api.key}")
    private String apiKey;

    @SneakyThrows
    public void createUser(User user) {
        String url = apiUrl + "users/";

        Map<String, String> headers = Map.of(
                "PRIVATE-KEY", apiKey,
                "Content-Type", "application/json"
        );

        Map<String, Object> body = Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "secret", secret
        );
        String jsonBody = objectMapper.writeValueAsString(body);

        Response response = makeRequest(url, HttpMethod.POST, headers, jsonBody);

        if (response.code() != 201) {
            throw new RuntimeException("Failed to create user" + response.body().string());
        }
    }

    @SneakyThrows
    public ResponseEntity<?> getOrCreateUsers(Long userId) {
        String url = apiUrl + "users/";
        User user = findById(userRepository, userId);

        Map<String, String> headers = Map.of(
                "PRIVATE-KEY", apiKey,
                "Content-Type", "application/json"
        );

        Map<String, Object> body = Map.of(
                "username", user.getUsername(),
                "secret", secret
        );
        String jsonBody = objectMapper.writeValueAsString(body);

        Response response = makeRequest(url, HttpMethod.PUT, headers, jsonBody);

        return ResponseEntity.status(response.code()).body(objectMapper.readTree(response.body().string()));
    }

    @SneakyThrows
    public ResponseEntity<?> getOrCreateChat(Long userId, Set<Long> participants) {
        String url = apiUrl + "chats/";

        User user = findById(userRepository, userId);
        List<String> usernames = userRepository.findUsernamesByIdIn(participants);
        String chatTitle = String.join(", ", usernames);
        Map<String, String> headers = Map.of(
                "Project-ID", projectId,
                "User-Name", user.getUsername(),
                "User-Secret", secret,
                "Content-Type", "application/json"
        );

        Map<String, Object> body = Map.of(
                "usernames", usernames,
                "title", chatTitle,
                "is_direct_chat", usernames.size() == 1
        );
        String jsonBody = objectMapper.writeValueAsString(body);

        Response response = makeRequest(url, HttpMethod.PUT, headers, jsonBody);

        return ResponseEntity.status(response.code()).body(objectMapper.readTree(response.body().string()));

    }

    private Response makeRequest(String url, HttpMethod method, Map<String, String> headers, String jsonBody) {
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonBody
        );

        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(String.valueOf(method), body);

        headers.forEach(builder::addHeader);

        Request request = builder.build();

        try {
            return client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
