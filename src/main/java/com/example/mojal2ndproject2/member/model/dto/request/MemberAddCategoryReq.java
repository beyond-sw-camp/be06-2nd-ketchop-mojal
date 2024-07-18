package com.example.mojal2ndproject2.member.model.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MemberAddCategoryReq {
    List<Long> categories = new ArrayList<>();
}
