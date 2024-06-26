package com.example.learntocode.services;

import com.example.learntocode.mapper.QuestionMapper;
import com.example.learntocode.mapper.UserMapper;
import com.example.learntocode.models.Question;
import com.example.learntocode.models.User;
import com.example.learntocode.payload.DTOs.QuestionDto;
import com.example.learntocode.payload.DTOs.UserDto;
import com.example.learntocode.payload.messages.MessageResponse;
import com.example.learntocode.repository.UserRepository;
import com.example.learntocode.services.auth0.Auth0Client;
import com.example.learntocode.services.chatEngine.ChatEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.example.learntocode.models.enums.Status.ONLINE;
import static com.example.learntocode.util.RepositoryUtil.findById;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final QuestionMapper questionMapper;
    private final Auth0Client auth0Client;
    private final ChatEngineService chatEngineService;

    public ResponseEntity<UserDto> getUserById(Long id) {
        User user = findById(userRepository, id);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    public ResponseEntity<UserDto> getUserByEmail(String email) {
        boolean userExists = userRepository.existsByEmail(email);
        User user = auth0Client.getUserByEmail(email);
        if (!userExists) {
            chatEngineService.createUser(user);
        }


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

    public ResponseEntity<List<QuestionDto>> getQuestionsByUserId(Long id) {
        User user = findById(userRepository, id);
        Set<Question> questions = user.getQuestions();
        List<Question> questionList = new ArrayList<>(questions);

        List<QuestionDto> questionDtos = questionMapper.toDto(questionList);

        return ResponseEntity.ok(questionDtos);
    }

    public ResponseEntity<List<UserDto>> getConnectedUsers() {
        List<User> connectedUsers = userRepository.findAllByStatus(ONLINE).orElse(null);
        if (connectedUsers == null) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.ok(userMapper.toDto(connectedUsers));
    }
}
