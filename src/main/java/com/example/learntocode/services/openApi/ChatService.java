//package com.example.learntocode.services.openApi;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.theokanning.openai.client.OpenAiApi;
//import com.theokanning.openai.completion.CompletionRequest;
//import com.theokanning.openai.completion.CompletionResult;
//import com.theokanning.openai.completion.chat.ChatCompletionRequest;
//import com.theokanning.openai.completion.chat.ChatMessage;
//import com.theokanning.openai.completion.chat.ChatMessageRole;
//import com.theokanning.openai.image.CreateImageRequest;
//import com.theokanning.openai.service.OpenAiService;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import okhttp3.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import retrofit2.Retrofit;
//
//import java.time.Duration;
//import java.util.*;
//
//import static com.theokanning.openai.service.OpenAiService.defaultClient;
//import static com.theokanning.openai.service.OpenAiService.defaultRetrofit;
//
//@Service
//@RequiredArgsConstructor
//public class ChatService {
//
//    private final OkHttpClient client;
//    private final ObjectMapper objectMapper;
//
//    @Value("${openai.api.key}")
//    private String token;
//
//    @SneakyThrows
//    public String getOpenaiResponse(String prompt) {
//
//        Map<String, Object> message = new HashMap<>();
//        message.put("role", "user");
//        message.put("content", prompt);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("model", "gpt-3.5-turbo");
//        body.put("messages", Collections.singletonList(message));
//
//        String jsonPrompt;
//        try {
//            jsonPrompt = objectMapper.writeValueAsString(body);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Failed to serialize prompt to JSON", e);
//        }
//
//        Request request = new Request.Builder()
//                .url("https://api.openai.com/v1/chat/completions")
//                .method("POST", RequestBody.create(MediaType.parse("application/json"), jsonPrompt))
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Authorization", "Bearer " + token)
//                .build();
//
//        Response response = client.newCall(request).execute();
//
//        return response.body().string();
//    }
//
//    public CompletionResult test() {
//        OpenAiService service = new OpenAiService("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");
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("Somebody once told me the world is gonna roll me")
//                .model("gpt-3.5-turbo")
//                .echo(true)
//                .build();
//
//        CompletionResult completion = service.createCompletion(completionRequest);
//
//        return completion;
//
//    }
//
//    public static void main(String... args) {
//        String token = "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";
//        OpenAiService service = new OpenAiService(token, Duration.ofSeconds(30));
//
//        System.out.println("\nCreating completion...");
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .model("babbage-002")
//                .prompt("Somebody once told me the world is gonna roll me")
//                .echo(true)
//                .user("testing")
//                .n(3)
//                .build();
//        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
//
//        System.out.println("\nCreating Image...");
//        CreateImageRequest request = CreateImageRequest.builder()
//                .prompt("A cow breakdancing with a turtle")
//                .build();
//
//        System.out.println("\nImage is located at:");
//        System.out.println(service.createImage(request).getData().get(0).getUrl());
//
//        System.out.println("Streaming chat completion...");
//        final List<ChatMessage> messages = new ArrayList<>();
//        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "You are a dog and will speak as such.");
//        messages.add(systemMessage);
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
//                .builder()
//                .model("gpt-3.5-turbo")
//                .messages(messages)
//                .n(1)
//                .maxTokens(50)
//                .logitBias(new HashMap<>())
//                .build();
//
//        service.streamChatCompletion(chatCompletionRequest)
//                .doOnError(Throwable::printStackTrace)
//                .blockingForEach(System.out::println);
//
//        service.shutdownExecutor();
//    }
//
//
//}
