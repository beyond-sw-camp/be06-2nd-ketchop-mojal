package com.example.mojal2ndproject2.matching.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchingConfirmedReq {
    private Long loginUserIdx;
    private Long chatRoomIdx;
}
