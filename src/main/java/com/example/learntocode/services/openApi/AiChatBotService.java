package com.example.learntocode.services.openApi;

import com.example.learntocode.mapper.AIChatMemoMapper;
import com.example.learntocode.models.chat.AiChatMemo;
import com.example.learntocode.payload.DTOs.ChatMemoDto;
import com.example.learntocode.repository.ChatMemoRepository;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class AiChatBotService {
    private static final String SYSTEM_MESSAGE = "You are the best programmer's companion. Answer the user's questions using the best practices in programming.";
    private final ChatMemoRepository chatMemoRepository;
    private final AIChatMemoMapper aiChatMemoMapper;
    private final AiService openAiService;

    public Map<String, ChatMemoDto> createChatMemo(ChatMemoDto data) {
        AiChatMemo chatMemo = aiChatMemoMapper.toEntity(data);
        if (chatMemo.getSessionId() == null) {
            chatMemo.setSessionId(UUID.randomUUID().toString());
        }
        chatMemoRepository.saveAndIncrementOrder(chatMemo);

        List<AiChatMemo> previousMessages = chatMemoRepository.findBySessionId(chatMemo.getSessionId()).orElse(new ArrayList<>());
        previousMessages.add(chatMemo);
        previousMessages.sort(Comparator.comparingInt(AiChatMemo::getOrder));

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), SYSTEM_MESSAGE));
        messages.addAll(aiChatMemoMapper.toChatMessages(previousMessages));

        ChatMessage responseFromAi = openAiService.getChatResponse(messages);

        AiChatMemo response = aiChatMemoMapper.toEntity(responseFromAi, chatMemo.getSessionId());
        chatMemoRepository.saveAndIncrementOrder(response);

        Map<String, ChatMemoDto> responseMap = new HashMap<>();
        responseMap.put("input", aiChatMemoMapper.toDto(chatMemo));
        responseMap.put("response", aiChatMemoMapper.toDto(response));

        return responseMap;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ChatMemoDto>> getFirstMessageOfEachSessionByUserId(Long userId) {
        List<ChatMemoDto> response = aiChatMemoMapper.toDto(chatMemoRepository.findFirstMessageOfEachSessionByUserId(userId));
        return ResponseEntity.ok(response);
    }
}