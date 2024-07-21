package com.example.mojal2ndproject2.exchangepost.model.dto.response;

import com.example.mojal2ndproject2.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ReadExchangePostRes {
    private Long postIdx;
    private Long memberIdx;
    private String memberNickname;
    private String giveBtmCategory;
    private String takeBtmCategory;
    private String giveCategory;
    private String takeCategory;
    private String title;
    private String contents;
    private LocalDateTime timeStamp;
    private LocalDateTime modifyTime;
    private Boolean status;
    private String postType;
}
