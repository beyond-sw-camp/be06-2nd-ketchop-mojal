package com.example.mojal2ndproject2.sharePost.model.dto.request;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.Getter;

@Getter
public class SharePostCreateReq {
    @NotBlank(message = "제목을 입력해주세요")
    @NotNull(message = "제목을 입력해주세요")
    @Size(min = 1, max = 45, message = "제목은 45자 이하로 작성해야합니다")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    @NotNull(message = "내용을 입력해주세요")
    @Size(min = 1, max = 900, message = "내용은 900자 이하로 작성해야합니다")
    private String contents;
    @NotBlank(message = "기간을 정해주세요")
    @NotNull(message = "기간을 정해주세요")
    @Min(value = 1, message = "기간은 1시간 이상으로 설정해야합니다")
    @Max(value = 24, message = "기간은 24시간 이하로 설정해야합니다")
    // TODO byul : 0~24까지만
    private Integer deadline;
    @NotBlank(message = "인원을 정해주세요")
    @NotNull(message = "인원을 정해주세요")
    private Integer capacity;
    private Long categoryIdx;
    @NotBlank(message = "하위카테고리를 입력해주세요")
    @NotNull(message = "하위카테고리를 입력해주세요")
    @Size(min = 1, max = 45, message = "하위 카테고리는 45자 이하로 작성해야합니다")
    private String btmCategory;

    private List<String> images;
}
