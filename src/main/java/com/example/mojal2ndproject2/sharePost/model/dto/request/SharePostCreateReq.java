package com.example.mojal2ndproject2.sharePost.model.dto.request;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SharePostCreateReq {
    private String title;
    private String contents;
    private String deadline;
    private Integer capacity;
    private Long categoryIdx;
    private String btmCategory;
}
