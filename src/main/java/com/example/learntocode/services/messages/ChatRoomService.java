package com.example.learntocode.services.messages;

import com.example.learntocode.models.chat.ChatRoom;
import com.example.learntocode.models.User;
import com.example.learntocode.repository.ChatRoomRepository;
import com.example.learntocode.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.learntocode.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public Long getChatRoomId(Long senderId, Long recipientId, boolean createNewRoomIfNotExists) {
        User sender = findById(userRepository, senderId);
        User recipient = findById(userRepository, recipientId);
        Set<User> participants = Set.of(sender, recipient);

        Long chatRoomId = chatRoomRepository.findByParticipants(participants).map(ChatRoom::getId).orElse(null);

        if (chatRoomId == null && createNewRoomIfNotExists) {
            chatRoomId = createChatRoom(participants).getId();
        }

        return chatRoomId;
    }

    private ChatRoom createChatRoom(Set<User> participants) {
        ChatRoom chatRoom = ChatRoom.builder().participants(participants).build();
        return chatRoomRepository.save(chatRoom);
    }
}
