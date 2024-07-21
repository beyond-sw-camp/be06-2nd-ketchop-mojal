package com.example.mojal2ndproject2.sharePost.model.dto.response;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class SharePostReadRes {
    private Long postIdx;
    private Long authorIdx;
    private String author;
    private String title;
    private String contents;
    private LocalDateTime timeStamp;
    private Boolean status;
    private String postType;
    private Integer deadline;
    private Integer capacity;
    private Integer currentEnrollment;
    private String category;
    private String btmCategory;
    private List<String> matchingMembers;
}
