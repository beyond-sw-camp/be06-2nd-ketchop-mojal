package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;

    //채팅메세지 저장
    public void save(Long roomIdx, ChatMessage chatMessage) {
        ChatRoom chatRoom = ChatRoom.builder()
                .idx(roomIdx)
                .build();

        ChatMessage newChatMessage = ChatMessage.builder()
                .member(chatMessage.getMember())
//                .senderIdx(chatMessage.getSenderIdx())
                .message(chatMessage.getMessage())
                .timeStamp(chatMessage.getTimeStamp()) //클라이언트에서 현재시간 처리함
                .chatRoom(chatRoom)
                .build();
        chatMessageRepository.save(newChatMessage);

    }
}
