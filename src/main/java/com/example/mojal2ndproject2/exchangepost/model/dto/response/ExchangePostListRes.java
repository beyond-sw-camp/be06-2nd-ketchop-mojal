package com.example.mojal2ndproject2.exchangepost.model.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExchangePostListRes {
    private Long postIdx;
    private Long memberIdx;
    private String giveBtmCategory;
    private String takeBtmCategory;
    private String giveCategory;
    private String takeCategory;
    private String title;
    private LocalDateTime timeStamp;
    private LocalDateTime modifyTime;
    private Boolean status;
    private String postType;
}
