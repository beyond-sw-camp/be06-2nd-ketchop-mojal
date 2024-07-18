package com.example.mojal2ndproject2.sharePost.model;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.criteria.CriteriaBuilder.In;
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
    private String deadline;
    private Integer capacity;
    private Integer currentEnrollment;
    private String btmCategory;
    @ManyToOne
    @JoinColumn(name = "category_idx")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;
    @OneToMany(mappedBy = "sharePost")
    private List<PostMatchingMember> postMatchingMembers = new ArrayList<>();
}
