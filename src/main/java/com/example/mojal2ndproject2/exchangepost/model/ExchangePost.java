package com.example.mojal2ndproject2.exchangepost.model;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String contents;
    private LocalDateTime timeStamp;
    private LocalDateTime modifyTime;
    private Boolean status;
    private String postType;
    private String giveBtmCategory;
    private String takeBtmCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "give_category_idx")
    private Category giveCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "take_category_idx")
    private Category takeCategory;
    //  idx  give_c_idx take_c_idx
    //   1        1          2

    @OneToMany(mappedBy = "exchangePost")
    private List<ChatRoom> chatRooms = new ArrayList<>();
    @OneToMany(mappedBy = "exchangePost", fetch = FetchType.LAZY)
    private List<PostMatchingMember> postMatchingMembers = new ArrayList<>();
}
