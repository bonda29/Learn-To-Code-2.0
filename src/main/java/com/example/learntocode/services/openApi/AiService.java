package com.example.learntocode.services.openApi;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    private static final String model = "gpt-3.5-turbo-0613";
    private static final int numberOfResponses = 1;
    private static final int maxTokens = 150;

    @Value("${openai.api.key}")
    private String token;

    public ChatMessage getChatResponse(List<ChatMessage> messages) {
        OpenAiService service = new OpenAiService(token);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model(model)
                .messages(messages)
                .n(numberOfResponses)
                .maxTokens(maxTokens)
                .logitBias(new HashMap<>())
                .build();

        return service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
    }
}