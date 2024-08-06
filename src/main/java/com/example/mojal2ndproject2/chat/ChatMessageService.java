package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
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

        //챗메시지 객체의 연관관계매핑된 멤버 추가
        Member senderMember = Member.builder()
                .idx(chatMessage.getSenderIdx())
                .build();

        ChatMessage newChatMessage = ChatMessage.builder()
                .senderIdx(chatMessage.getSenderIdx())
                .message(chatMessage.getMessage())
                .timeStamp(chatMessage.getTimeStamp()) //클라이언트에서 현재시간 처리함
                .chatRoom(chatRoom)
                .member(senderMember) //쒸익쒸익 ()안에꺼 안바꿔줌....
                .build();

        chatMessageRepository.save(newChatMessage);

    }
}
