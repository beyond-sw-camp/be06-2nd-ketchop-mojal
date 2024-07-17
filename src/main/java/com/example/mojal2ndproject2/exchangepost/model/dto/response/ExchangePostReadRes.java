package com.example.mojal2ndproject2.exchangepost.model.dto.respone;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExchangePostReadRes {
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
    private Category giveCategory;
    private Category takeCategory;
}
