package com.example.mojal2ndproject2.sharePost.model.dto.response;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class SharePostCreateRes {
    private Long idx;
    private Long writerIdx;
    private String title;
}
