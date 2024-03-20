package com.example.learntocode.controllers.websocket;

import com.example.learntocode.payload.DTOs.UserDto;
import com.example.learntocode.services.websocket.UserWebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserWebsocketController {

    private final UserWebsocketService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/user/public")
    public UserDto addUser(@Payload UserDto data) {
        userService.connectUser(data);
        return data;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public UserDto disconnectUser(@Payload UserDto data) {
        userService.disconnectUser(data);
        return data;
    }
}
