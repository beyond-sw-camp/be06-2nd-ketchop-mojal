package com.example.mojal2ndproject2.sharePost.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
public class SharePostCreateRes {
    private Long idx;
    private Long writerIdx;
    private String title;
}
