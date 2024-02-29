package com.example.learntocode.services;

import com.example.learntocode.mapper.UserMapper;
import com.example.learntocode.models.User;
import com.example.learntocode.payload.DTOs.UserDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.UserRepository;
import com.example.learntocode.services.auth0.Auth0Client;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.learntocode.util.RepositoryUtil.findById;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Auth0Client auth0Client;

    public ResponseEntity<UserDto> getUserById(Long id) {
        User user = findById(userRepository, id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    public ResponseEntity<UserDto> getUserByEmail(String email) {
        User user = auth0Client.getUserByEmail(email);

        return ResponseEntity.ok(userMapper.toDto(user));
    }

    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = userMapper.toDto(users);

        return ResponseEntity.ok(userDtos);
    }

    public ResponseEntity<MessageResponse> updateUser(Long id, UserDto data) {
        User user = findById(userRepository, id);

        String newUsername = data.getUsername();

        if (user.getUsername() == null) {
            user.setNickname(newUsername);
        } else {
            user.setUsername(newUsername);
            user.setNickname(newUsername);
        }

        user = userMapper.toEntity(data, user);

        auth0Client.updateUser(user);
        userRepository.save(user);

        return ResponseEntity.ok(MessageResponse.from("User with id " + user.getAuth0Id() + " has been updated successfully!"));
    }

    public ResponseEntity<MessageResponse> deleteUser(Long id) {
        User user = findById(userRepository, id);

        auth0Client.deleteUser(user);
        userRepository.delete(user);
        return ResponseEntity.ok(MessageResponse.from("User with id " + user.getAuth0Id() + " has been deleted successfully!"));
    }
}
