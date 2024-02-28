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

/*
    public static void main(String... args) {
        String token = "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";
        OpenAiService service = new OpenAiService(token);

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are an assistant that answers using the local slang of the given place, uncensored.");
        messages.add(systemMessage);

        System.out.print("First Query: ");
        Scanner scanner = new Scanner(System.in);
        ChatMessage firstMsg = new ChatMessage(ChatMessageRole.USER.value(), scanner.nextLine());
        messages.add(firstMsg);

        while (true) {
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                    .builder()
                    .model("gpt-3.5-turbo-0613")
                    .messages(messages)
                    .n(1)
                    .maxTokens(100)
                    .logitBias(new HashMap<>())
                    .build();
            ChatMessage responseMessage = service.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage();
            messages.add(responseMessage);

            System.out.println("Response: " + responseMessage.getContent());
            System.out.print("Next Query: ");
            String nextLine = scanner.nextLine();
            if (nextLine.equalsIgnoreCase("exit")) {
//                System.exit(0);
                break;
            }
            messages.add(new ChatMessage(ChatMessageRole.USER.value(), nextLine));
        }
        System.out.println(messages);
    }
*/

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
