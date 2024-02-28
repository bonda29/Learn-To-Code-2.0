package com.example.learntocode.services.openApi;

import com.example.learntocode.mapper.ChatMemoMapper;
import com.example.learntocode.models.ChatMemo;
import com.example.learntocode.payload.DTOs.ChatMemoDto;
import com.example.learntocode.repository.ChatMemoRepository;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    private static final String model = "gpt-3.5-turbo-0613";
    private static final int numberOfResponses = 1;
    private static final int maxTokens = 150;
    private final ChatMemoRepository chatMemoRepository;
    private final ChatMemoMapper chatMemoMapper;

    @Value("${openai.api.key}")
    private String token;


    public Map<String, ChatMemoDto> createChatMemo(ChatMemoDto data) {
        OpenAiService service = new OpenAiService(token);

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

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(model)
                .messages(messages)
                .n(numberOfResponses)
                .maxTokens(maxTokens)
                .logitBias(new HashMap<>())
                .build();

        ChatMessage responseFromAi = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();

        var response = chatMemoMapper.toEntity(responseFromAi, chatMemo.getSessionId());
        chatMemoRepository.saveAndIncrementOrder(response);

        Map<String, ChatMemoDto> responseMap = new HashMap<>();
        responseMap.put("input", chatMemoMapper.toDto(chatMemo));
        responseMap.put("response", chatMemoMapper.toDto(response));

        return responseMap;

    }

}
