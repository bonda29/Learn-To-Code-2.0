package com.example.learntocode.mapper;

import com.example.learntocode.models.User;
import com.example.learntocode.payload.DTOs.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> toDto(List<User> users) {

        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    public User toEntity(UserDto dto, User user) {
        modelMapper.map(dto, user);
        return user;
    }
}
