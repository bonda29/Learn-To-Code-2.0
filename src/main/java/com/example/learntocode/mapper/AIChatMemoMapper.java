package com.example.learntocode.mapper;

import com.example.learntocode.models.AiChatMemo;
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
public class AIChatMemoMapper {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;


    public AIChatMemoMapper(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.modelMapper.addMappings(new PropertyMap<AiChatMemo, ChatMemoDto>() {
            @Override
            protected void configure() {
                map().setAuthorId(source.getAuthor().getId());
            }
        });
    }

    public ChatMemoDto toDto(AiChatMemo aiChatMemo) {
        return modelMapper.map(aiChatMemo, ChatMemoDto.class);
    }

    public List<ChatMemoDto> toDto(List<AiChatMemo> aiChatMemos) {
        return aiChatMemos.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public AiChatMemo toEntity(ChatMemoDto chatMemoDto) {
        AiChatMemo aiChatMemo = modelMapper.map(chatMemoDto, AiChatMemo.class);
        if (chatMemoDto.getAuthorId() != null) {
            aiChatMemo.setAuthor(findById(userRepository, chatMemoDto.getAuthorId()));
        }
        return aiChatMemo;
    }

    public AiChatMemo toEntity(ChatMessage chatMessage, String sessionId) {
        AiChatMemo aiChatMemo = new AiChatMemo();
        aiChatMemo.setRole(ChatMessageRole.ASSISTANT);
        aiChatMemo.setContent(chatMessage.getContent());
        aiChatMemo.setSessionId(sessionId);
        aiChatMemo.setTimestamp(LocalDateTime.now());
        return aiChatMemo;
    }

    public List<ChatMessage> toChatMessages(List<AiChatMemo> aiChatMemos) {
        return aiChatMemos.stream()
                .map(aiChatMemo ->
                        new ChatMessage(aiChatMemo.getRole().value(), aiChatMemo.getContent()))
                .collect(Collectors.toList());
    }
}
