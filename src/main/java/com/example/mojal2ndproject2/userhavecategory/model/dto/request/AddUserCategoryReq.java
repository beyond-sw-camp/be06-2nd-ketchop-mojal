package com.example.mojal2ndproject2.userhavecategory.model.dto.request;

import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class AddUserCategoryReq {
    @Size(min = 1, max = 5, message = "카테고리 리스트는 최소 1개, 최대 5개의 항목을 포함해야 합니다.")
    List<Long> categories = new ArrayList<>();
}
