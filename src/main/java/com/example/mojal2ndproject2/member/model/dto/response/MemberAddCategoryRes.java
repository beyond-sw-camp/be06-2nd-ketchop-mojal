package com.example.mojal2ndproject2.member.model.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberAddCategoryRes {
    List<String> categories = new ArrayList<>();
}
