package com.example.mojal2ndproject2.exchangepost.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.codehaus.groovy.runtime.dgmimpl.arrays.IntegerArrayGetAtMetaMethod;
@Getter
@Builder
@AllArgsConstructor
public class CreateExchangePostReq {
    private Long giveCategoryIdx;
    private Long takeCategoryIdx;
    @NotBlank(message = "제공할 하위카테고리를 입력해주세요")
    @NotNull(message = "제공할 하위카테고리를 입력해주세요")
    private String giveBtmCategory;
    @NotBlank(message = "제공받을 하위카테고리를 입력해주세요")
    @NotNull(message = "제공받을 하위카테고리를 입력해주세요")
    private String takeBtmCategory;
    @NotBlank(message = "제목을 입력해주세요")
    @NotNull(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    @NotNull(message = "내용을 입력해주세요")
    private String contents;
}
