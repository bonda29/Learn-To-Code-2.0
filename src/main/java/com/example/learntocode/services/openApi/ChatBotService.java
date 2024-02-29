package com.example.learntocode.services.openApi;


import com.example.learntocode.mapper.ChatMemoMapper;
import com.example.learntocode.models.ChatMemo;
import com.example.learntocode.payload.DTOs.ChatMemoDto;
import com.example.learntocode.repository.ChatMemoRepository;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBotService {

    private static final String systemMessage = """
            You are the best programmers companion. Answer the user's questions using the best practices in programming.
            """;
    private final ChatMemoRepository chatMemoRepository;
    private final ChatMemoMapper chatMemoMapper;
    private final AiService openAiService;

    public Map<String, ChatMemoDto> createChatMemo(ChatMemoDto data) {
        var chatMemo = chatMemoMapper.toEntity(data);
        if (chatMemo.getSessionId() == null) {
            String sessionId = UUID.randomUUID().toString();
            chatMemo.setSessionId(sessionId);
        }
        chatMemoRepository.saveAndIncrementOrder(chatMemo);

        var previousMessages = chatMemoRepository.findBySessionId(chatMemo.getSessionId()).orElseGet(ArrayList::new);
        previousMessages.add(chatMemo);
        previousMessages.sort(Comparator.comparingInt(ChatMemo::getOrder));

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage));
        messages.addAll(chatMemoMapper.toChatMessages(previousMessages));

        ChatMessage responseFromAi = openAiService.getChatResponse(messages);

        var response = chatMemoMapper.toEntity(responseFromAi, chatMemo.getSessionId());
        chatMemoRepository.saveAndIncrementOrder(response);

        Map<String, ChatMemoDto> responseMap = new HashMap<>();
        responseMap.put("input", chatMemoMapper.toDto(chatMemo));
        responseMap.put("response", chatMemoMapper.toDto(response));

        return responseMap;
    }
}
