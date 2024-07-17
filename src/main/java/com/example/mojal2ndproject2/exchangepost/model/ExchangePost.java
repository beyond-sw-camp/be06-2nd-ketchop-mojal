package com.example.mojal2ndproject2.exchangepost.model;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@DynamicInsert
@Builder
public class ExchangePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String contents;
    private String timeStamp;
    private String modifyTime;
    private Boolean status;
    private Boolean postType;
    private String giveBtmCategory;
    private String takeBtmCategory;
    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "give_category_idx")
    private Category giveCategory;
    @ManyToOne
    @JoinColumn(name = "take_category_idx")
    private Category takeCategory;
    //  idx  give_c_idx take_c_idx
    //   1        1          2

    @OneToMany(mappedBy = "exchangePost")
    private List<ChatRoom> chatRooms = new ArrayList<>();
    @OneToMany(mappedBy = "exchangePost")
    private List<PostMatchingMember> postMatchingMembers = new ArrayList<>();


}
