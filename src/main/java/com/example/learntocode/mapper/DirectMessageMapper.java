package com.example.learntocode.mapper;


import com.example.learntocode.models.DirectMessage;
import com.example.learntocode.payload.DTOs.DirectMessageDto;
import com.example.learntocode.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class DirectMessageMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public DirectMessageMapper(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.modelMapper.addMappings(new PropertyMap<DirectMessage, DirectMessageDto>() {
            @Override
            protected void configure() {
                map().setSenderId(source.getSender().getId());
                map().setReceiverId(source.getReceiver().getId());
            }
        });
    }

    public DirectMessageDto toDto(DirectMessage directMessage) {
        return modelMapper.map(directMessage, DirectMessageDto.class);
    }

    public List<DirectMessageDto> toDto(List<DirectMessage> directMessages) {
        return directMessages.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public DirectMessage toEntity(DirectMessageDto directMessageDto) {
        DirectMessage directMessage = modelMapper.map(directMessageDto, DirectMessage.class);
        directMessage.setSender(findById(userRepository, directMessageDto.getSenderId()));
        directMessage.setReceiver(findById(userRepository, directMessageDto.getReceiverId()));
        return directMessage;
    }
}
