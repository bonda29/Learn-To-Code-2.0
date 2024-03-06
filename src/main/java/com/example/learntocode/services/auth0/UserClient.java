package com.example.learntocode.services.auth0;

import com.example.learntocode.models.User;

public interface UserClient {
    User getUserByEmail(String email);

    void updateUser(User user);

    void deleteUser(User user);
}
