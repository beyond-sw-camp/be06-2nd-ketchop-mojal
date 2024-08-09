package com.example.mojal2ndproject2.userhavecategory.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetUserCategoryRes {
    private Long idx;
    private String name;
}
