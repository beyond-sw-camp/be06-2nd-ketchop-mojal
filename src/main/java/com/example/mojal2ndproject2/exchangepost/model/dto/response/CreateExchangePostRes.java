package com.example.mojal2ndproject2.exchangepost.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateExchangePostRes {
    private Long idx;
    private String name;
    private String title;
    private String content;
}
