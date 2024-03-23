//package com.example.learntocode.mapper;
//
//import com.example.learntocode.models.chat.ChatMessage;
//import com.example.learntocode.payload.DTOs.ChatMessageDto;
//import com.example.learntocode.repository.ChatRoomRepository;
//import com.example.learntocode.repository.UserRepository;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.PropertyMap;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static com.example.learntocode.util.RepositoryUtil.findById;
//
//@Component
//public class ChatMessageMapper {
//    private final ModelMapper modelMapper;
//    private final ChatRoomRepository chatRoomRepository;
//    private final UserRepository userRepository;
//
//
//    public ChatMessageMapper(ModelMapper modelMapper, ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
//        this.modelMapper = modelMapper;
//        this.chatRoomRepository = chatRoomRepository;
//        this.userRepository = userRepository;
//        this.modelMapper.addMappings(new PropertyMap<ChatMessage, ChatMessageDto>() {
//            @Override
//            protected void configure() {
//                map().setChatRoomId(source.getChatRoom().getId());
//                map().setSenderId(source.getSender().getId());
//                map().setRecipientId(source.getRecipient().getId());
//            }
//        });
//
//    }
//
//    public ChatMessageDto toDto(ChatMessage chatMessage) {
//        return modelMapper.map(chatMessage, ChatMessageDto.class);
//    }
//
//    public List<ChatMessageDto> toDto(List<ChatMessage> chatMessages) {
//        return chatMessages.stream()
//                .map(this::toDto)
//                .collect(Collectors.toList());
//    }
//
//
//    public ChatMessage toEntity(ChatMessageDto chatMessageDto) {
//        ChatMessage chatMessage = modelMapper.map(chatMessageDto, ChatMessage.class);
//
//        chatMessage.setChatRoom(findById(chatRoomRepository, chatMessageDto.getChatRoomId()));
//        chatMessage.setSender(findById(userRepository, chatMessageDto.getSenderId()));
//        chatMessage.setRecipient(findById(userRepository, chatMessageDto.getRecipientId()));
//
//        return chatMessage;
//    }
//}