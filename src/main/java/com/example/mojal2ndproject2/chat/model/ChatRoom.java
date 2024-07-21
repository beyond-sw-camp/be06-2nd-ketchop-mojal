package com.example.mojal2ndproject2.chat.model;

import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.model.Member;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "exchangePost_idx")
    private ExchangePost exchangePost;

    @ManyToOne
    @JoinColumn(name = "member1_idx")
    private Member member1; //글 작성자

    @ManyToOne
    @JoinColumn(name = "member2_idx")
    private Member member2; //신청자

    @OneToMany(mappedBy = "chatRoom")
    @JsonManagedReference //순환 참조를 방지하기 위해 Jackson 애노테이션을 사용
    private List<ChatMessage> chatMessages; // = new ArrayList<>() 삭제
}
