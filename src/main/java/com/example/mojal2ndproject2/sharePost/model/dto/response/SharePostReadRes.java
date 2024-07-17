package com.example.mojal2ndproject2.sharePost.model.dto.response;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class SharePostReadRes {
    private String title;
    private String contents;
    private String timeStamp;
    private Boolean status;
    private Boolean postType;
    private String deadline;
    private Integer capacity;
    private Integer currentEnrollment;
    private String category;
    private String btmCategory;
    private Member writer;
    private List<String> matchingMembers;
}
