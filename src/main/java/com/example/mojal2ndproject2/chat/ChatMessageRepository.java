package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
//    Optional<List<ChatMessage>> findAllByChatRoom(ChatRoom chatRoom);
    List<ChatMessage> findAllByChatRoom(ChatRoom chatRoom);
    Slice<ChatMessage> findAllByChatRoom(ChatRoom chatRoom, Pageable pageable);

    Optional<ChatMessage> findFirstByChatRoomOrderByTimeStampDesc(ChatRoom chatRoom);
}
