package com.example.mojal2ndproject2.sharePost.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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
