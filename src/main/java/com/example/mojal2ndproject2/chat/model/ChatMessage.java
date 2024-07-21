package com.example.mojal2ndproject2.chat.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private Long senderIdx; //Todo byul : 은주 컨펌 후 멤버와 연관 관계맺기 ej-어떡하쥥!어떡하쥥?~
    private String message;
    private String timeStamp;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }

    @ManyToOne
    @JoinColumn(name = "chat_room_idx")
    @JsonBackReference //순환 참조를 방지하기 위해 Jackson 애노테이션을 사용
    private ChatRoom chatRoom;
}
