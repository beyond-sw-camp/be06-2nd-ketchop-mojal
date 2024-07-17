package com.example.mojal2ndproject2.exchangepost.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReadExchangePostRes {
    private Long idx;
    private String title;
    private String contents;
    private String timeStamp;
    private String modifyTime;
    private Boolean status;
    private Boolean postType;
    private String giveBtmCategory;
    private String takeBtmCategory;
    private Long memberIdx;
    private String memberNickname;
    private String giveCategoryName;
    private String takeCategoryName;
}
