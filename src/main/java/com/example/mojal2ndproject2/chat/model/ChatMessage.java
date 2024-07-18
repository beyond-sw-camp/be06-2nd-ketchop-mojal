package com.example.mojal2ndproject2.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long senderIdx; //Todo byul : 은주 컨펌 후 멤버와 연관 관계맺기
    private String message;
    private String timeStamp;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    @ManyToOne
    @JoinColumn(name = "chat_room_idx")
    private ChatRoom chatRoom;
}
