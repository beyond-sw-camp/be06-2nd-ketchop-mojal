package com.example.mojal2ndproject2.category.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetCategoriesRes {
    Long idx;
    String name;
}
