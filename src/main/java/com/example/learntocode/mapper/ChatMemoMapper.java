package com.example.learntocode.mapper;

import com.example.learntocode.models.ChatMemo;
import com.example.learntocode.payload.DTOs.ChatMemoDto;
import com.example.learntocode.repository.UserRepository;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Component
public class ChatMemoMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public ChatMemoMapper(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.modelMapper.addMappings(new PropertyMap<ChatMemo, ChatMemoDto>() {
            @Override
            protected void configure() {
                map().setAuthorId(source.getAuthor().getId());
            }
        });
    }

    public ChatMemoDto toDto(ChatMemo chatMemo) {
        return modelMapper.map(chatMemo, ChatMemoDto.class);
    }

    public List<ChatMemoDto> toDto(List<ChatMemo> chatMemos) {
        return chatMemos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ChatMemo toEntity(ChatMemoDto chatMemoDto) {
        ChatMemo chatMemo = modelMapper.map(chatMemoDto, ChatMemo.class);
        if (chatMemoDto.getAuthorId() != null) {
            chatMemo.setAuthor(findById(userRepository, chatMemoDto.getAuthorId()));
        }
        return chatMemo;
    }

    public ChatMemo toEntity(ChatMessage chatMessage, String sessionId) {
        ChatMemo chatMemo = new ChatMemo();
        chatMemo.setRole(ChatMessageRole.ASSISTANT);
        chatMemo.setContent(chatMessage.getContent());
        chatMemo.setSessionId(sessionId);
        chatMemo.setTimestamp(LocalDateTime.now());
        return chatMemo;
    }

    public List<ChatMessage> toChatMessages(List<ChatMemo> chatMemos) {
        return chatMemos.stream()
                .map(chatMemo ->
                        new ChatMessage(chatMemo.getRole().value(), chatMemo.getContent()))
                .collect(Collectors.toList());
    }
}
