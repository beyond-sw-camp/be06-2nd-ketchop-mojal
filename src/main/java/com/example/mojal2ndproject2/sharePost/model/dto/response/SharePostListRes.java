package com.example.mojal2ndproject2.sharePost.model.dto.response;

import com.example.mojal2ndproject2.member.model.Member;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SharePostListRes {
    private String title;
    private LocalDateTime timeStamp;
    private Boolean status;
    private String postType;
    private String deadline;
    private Integer capacity;
    private Integer currentEnrollment;
    private String category;
    private String btmCategory;
    private Long writerIdx;
}

