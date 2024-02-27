package com.example.learntocode.services.auth0;

import com.example.learntocode.models.User;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Auth0Client {

    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule())
            .configure(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Value("${okta.oauth2.management.api-token}")
    private String token;

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @NotNull
    private static RequestBody getRequestBody(String jsonData) {
        return RequestBody.create(MediaType.parse("application/json"), jsonData);
    }

    @SneakyThrows
    private List<User> getUsersByFilter(String q) {
        Request request = new Request.Builder()
                .url(issuer + "api/v2/users?q=" + q)
                .method("GET", null)
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() == null) {
            return null;
        }

        List<User> users = objectMapper.readValue(response.body().string(), objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));

        return users;
    }

    @SneakyThrows
    private void linkUsers(User parentUser, User childUser) {
        String parentUserId = parentUser.getUserId();
        String childUserId = childUser.getUserId();
        String childProvider = childUserId.split("\\|")[0];


        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("provider", childProvider);
        jsonMap.put("user_id", childUserId);
        String jsonData = objectMapper.writeValueAsString(jsonMap);


        Request request = new Request.Builder()
                .url(issuer + "api/v2/users/" + parentUserId + "/identities")
                .method("POST", getRequestBody(jsonData))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();
    }

    @SneakyThrows
    public User getUserByEmail(String email) {
        List<User> users = getUsersByFilter("email:" + email);
        if (users.isEmpty()) {
            throw new NotFoundException("User not found with email: " + email);
        }
        if (users.size() == 1) {
            return users.get(0);
        }

        User parentUser = users.stream().filter(user -> user.getUserId().startsWith("auth0")).findFirst().orElse(null);
        linkUsers(users.get(0), users.get(1));

        return parentUser;
    }

    @SneakyThrows
    public void updateUser(User user) {
        Map<String, String> jsonMap = new HashMap<>();

        jsonMap.put("username", user.getUsername());
        jsonMap.put("nickname", user.getNickname());
        jsonMap.put("email", user.getEmail());
        jsonMap.put("name", user.getName());
        jsonMap.put("picture", user.getPictureUrl());
        jsonMap.put("blocked", String.valueOf(user.isBlocked()));

        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            if (entry.getValue() == null) {
                jsonMap.remove(entry.getKey());
            }
        }

        String jsonData = objectMapper.writeValueAsString(jsonMap);


        Request request = new Request.Builder()
                .url(issuer + "api/v2/users/" + user.getUserId())
                .method("PATCH", getRequestBody(jsonData))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();
    }

    @SneakyThrows
    public void deleteUser(User user) {
        Request request = new Request.Builder()
                .url(issuer + "api/v2/users/" + user.getUserId())
                .method("DELETE", null)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();
    }

/*    @SneakyThrows
    public void changeUserBanStatus(String userId, boolean blocked) {
        Map<String, Boolean> jsonMap = new HashMap<>();
        jsonMap.put("blocked", blocked);
        String jsonData = objectMapper.writeValueAsString(jsonMap);

        Request request = new Request.Builder()
                .url(issuer + "api/v2/users/" + userId)
                .method("PATCH", getRequestBody(jsonData))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Response response = client.newCall(request).execute();
    }*/
}