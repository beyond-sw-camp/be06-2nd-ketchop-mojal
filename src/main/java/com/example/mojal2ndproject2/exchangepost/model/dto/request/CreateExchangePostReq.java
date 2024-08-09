package com.example.mojal2ndproject2.exchangepost.model.dto.request;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
@Getter
@Builder
@AllArgsConstructor
public class CreateExchangePostReq {
    private Long giveCategoryIdx;
    private Long takeCategoryIdx;
    @NotBlank(message = "제공할 하위카테고리를 입력해주세요")
    @NotNull(message = "제공할 하위카테고리를 입력해주세요")
    @Size(min = 1, max = 45, message = "하위 카테고리는 45자 이하로 작성해야합니다")
    private String giveBtmCategory;
    @NotBlank(message = "제공받을 하위카테고리를 입력해주세요")
    @NotNull(message = "제공받을 하위카테고리를 입력해주세요")
    @Size(min = 1, max = 45, message = "하위 카테고리는 45자 이하로 작성해야합니다")
    private String takeBtmCategory;
    @NotBlank(message = "제목을 입력해주세요")
    @NotNull(message = "제목을 입력해주세요")
    @Size(min = 1, max = 45, message = "제목은 45자 이하로 작성해야합니다")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    @NotNull(message = "내용을 입력해주세요")
    @Size(min = 1, max = 900, message = "내용은 900자 이하로 작성해야합니다")
    private String contents;

    private List<String> images;

}
