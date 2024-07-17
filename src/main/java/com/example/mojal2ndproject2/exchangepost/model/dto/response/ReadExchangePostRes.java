package com.example.mojal2ndproject2.exchangepost.model.dto.response;

import com.example.mojal2ndproject2.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReadExchangePostRes {
    private Long idx;
    private Long memberIdx;
    private String giveBtmCategory;
    private String takeBtmCategory;
    private String giveCategory;
    private String takeCategory;
    private String title;
    private String contents;
    private String timeStamp;
    private String modifyTime;
    private Boolean status;
    private Boolean postType;
}
