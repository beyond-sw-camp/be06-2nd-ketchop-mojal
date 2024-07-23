package com.example.mojal2ndproject2.sharePost.model;

import com.example.mojal2ndproject2.category.model.Category;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String title;
    private String contents;
    private LocalDateTime timeStamp;
    private LocalDateTime modifyTime;
    private Boolean status;
    private String postType;
    private Integer deadline;
    private Integer capacity;
    private Integer currentEnrollment;
    private String btmCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_idx")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;
    @OneToMany(mappedBy = "sharePost", fetch = FetchType.LAZY)
    private List<PostMatchingMember> postMatchingMembers = new ArrayList<>();
}
