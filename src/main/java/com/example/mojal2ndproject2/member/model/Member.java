package com.example.mojal2ndproject2.member.model;

import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.userhavecategory.model.UserHaveCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String nickname;
    private String email;
    private String password;
//    @CreationTimestamp
//    @Column(name = "signup_date")
    private String signupDate;
    private Boolean memberAuth;
    private Long kakaoIdx;
//    @ColumnDefault("ROLE_USER")
    private String role;
    @OneToMany(mappedBy = "member")
    private List<SharePost> sharePosts = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<ExchangePost> exchangePosts = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<UserHaveCategory> userHaveCategories = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<PostMatchingMember> postMatchingMembers = new ArrayList<>();
    @OneToMany(mappedBy = "member1")
    private List<ChatRoom> chatRooms = new ArrayList<>();
    @OneToMany(mappedBy = "member2")
    private List<ChatRoom> chatRooms2 = new ArrayList<>();

}
