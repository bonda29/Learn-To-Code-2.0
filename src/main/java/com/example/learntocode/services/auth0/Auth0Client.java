package com.example.learntocode.services.auth0;

import com.example.learntocode.models.User;
import com.example.learntocode.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Service
@RequiredArgsConstructor
public class Auth0Client implements UserClient {

    private final UserRepository userRepository;

    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${okta.oauth2.management.api-token}")
    private String token;

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @NotNull
    private static RequestBody getRequestBody(String jsonData) {
        return RequestBody.create(MediaType.parse("application/json"), jsonData);
    }

    @SneakyThrows
    public User getUserByEmail(String email) {
        List<User> users = getUsersByFilter("email:" + email);
        if (users.isEmpty()) {
            throw new NotFoundException("User not found with email: " + email);
        }
        return processUsers(users, email);
    }

    @SneakyThrows
    public void updateUser(User user) {
        String jsonData = buildJsonDataForUpdateUser(user);
        Request request = buildRequest(issuer + "api/v2/users/" + user.getAuth0Id(), "PATCH", jsonData);
        executeRequest(request);
    }

    @SneakyThrows
    public void deleteUser(User user) {
        Request request = buildRequest(issuer + "api/v2/users/" + user.getAuth0Id(), "DELETE", null);
        executeRequest(request);
    }

    @SneakyThrows
    public User getUserByAccessToken(String token) {
        Request request = buildRequest(issuer + "userinfo", "GET", null);
        String email = executeRequest(request).get(0).getEmail();
        User user = getUserByEmail(email);

        return user;
    }

    @SneakyThrows
    private List<User> getUsersByFilter(String q) {
        Request request = buildRequest(issuer + "api/v2/users?q=" + q, "GET", null);
        return executeRequest(request);
    }

    @SneakyThrows
    private void linkUsers(User parentUser, User childUser) {
        String jsonData = buildJsonDataForLinkUsers(childUser);
        Request request = buildRequest(issuer + "api/v2/users/" + parentUser.getAuth0Id() + "/identities", "POST", jsonData);
        executeRequest(request);
    }

    private Request buildRequest(String url, String method, String jsonData) {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method, jsonData != null ? getRequestBody(jsonData) : null)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + token);
        return builder.build();
    }

    private List<User> executeRequest(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        if (response.body() == null) {
            return null;
        }
        return objectMapper.readValue(response.body().string(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
    }

    private String buildJsonDataForLinkUsers(User childUser) throws JsonProcessingException {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("provider", childUser.getAuth0Id().split("\\|")[0]);
        jsonMap.put("user_id", childUser.getAuth0Id());
        return objectMapper.writeValueAsString(jsonMap);
    }

    private User processUsers(List<User> users, String email) {
        if (users.size() == 1) {
            if (userRepository.existsByEmail(email)) {
                return userRepository.findByEmail(email).orElse(null);
            }
            userRepository.save(users.get(0));
            return users.get(0);
        }
        int indexOfUserWithAuth0Id = users.stream().filter(user -> user.getAuth0Id().startsWith("auth0")).findFirst().map(users::indexOf).orElse(0);
        User parentUser = users.get(indexOfUserWithAuth0Id);
        User childUser = users.get(indexOfUserWithAuth0Id == 0 ? 1 : 0);
        linkUsers(parentUser, childUser);

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            userRepository.save(parentUser);
            return parentUser;
        }

        user.setAuth0Id(parentUser.getAuth0Id());
        return userRepository.save(user);
    }

    private String buildJsonDataForUpdateUser(User user) throws JsonProcessingException {
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("username", user.getUsername());
        jsonMap.put("nickname", user.getNickname());
        jsonMap.put("email", user.getEmail());
        jsonMap.put("name", user.getName());
        jsonMap.put("picture", user.getPictureUrl());
        jsonMap.put("blocked", String.valueOf(user.isBlocked()));

        jsonMap.entrySet().removeIf(entry -> entry.getValue() == null);

        return objectMapper.writeValueAsString(jsonMap);
    }
}