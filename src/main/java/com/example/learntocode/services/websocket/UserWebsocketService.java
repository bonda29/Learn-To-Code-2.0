//package com.example.learntocode.services.websocket;
//
//import com.example.learntocode.mapper.UserMapper;
//import com.example.learntocode.models.User;
//import com.example.learntocode.payload.DTOs.UserDto;
//import com.example.learntocode.payload.messages.MessageResponse;
//import com.example.learntocode.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.example.learntocode.models.enums.Status.OFFLINE;
//import static com.example.learntocode.models.enums.Status.ONLINE;
//import static com.example.learntocode.util.RepositoryUtil.findById;
//
//@Service
//@RequiredArgsConstructor
//public class UserWebsocketService {
//
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//
//    public ResponseEntity<MessageResponse> connectUser(UserDto user) {
//        var storedUser = findById(userRepository, user.getId());
//        if (storedUser != null) {
//            storedUser.setStatus(ONLINE);
//            userRepository.save(storedUser);
//        }
//        return ResponseEntity.ok(MessageResponse.from("User with id " + user.getId() + " has been connected successfully!"));
//    }
//
//    public ResponseEntity<MessageResponse> disconnectUser(UserDto user) {
//        var storedUser = findById(userRepository, user.getId());
//        if (storedUser != null) {
//            storedUser.setStatus(OFFLINE);
//            userRepository.save(storedUser);
//        }
//        return ResponseEntity.ok(MessageResponse.from("User with id " + user.getId() + " has been disconnected successfully!"));
//    }
//
//    public ResponseEntity<List<UserDto>> findConnectedUsers() {
//        List<User> connectedUsers = userRepository.findAllByStatus(ONLINE).orElse(null);
//        if (connectedUsers == null) {
//            return ResponseEntity.ok(new ArrayList<>());
//        }
//        return ResponseEntity.ok(userMapper.toDto(connectedUsers));
//    }
//}
