package com.example.mojal2ndproject2.matching.model.dto;


import com.example.mojal2ndproject2.exchangepost.model.ExchangePost;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchingMemberRes {
    private Long sharePostIdx;

    private Long member1Idx; //작성자
    private Long member2Idx; //참여자

    private Long exchangePostIdx;
}
