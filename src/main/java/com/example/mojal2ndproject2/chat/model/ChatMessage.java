package com.example.mojal2ndproject2.chat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long senderIdx;
    private String message;
    private String timeStamp;
    @ManyToOne
    @JoinColumn(name = "chat_room_idx")
    private ChatRoom chatRoom;
}
