package com.example.mojal2ndproject2.sharePost.model.dto.request;

import com.example.mojal2ndproject2.category.Category;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SharePostCreateReq {
    @NotBlank(message = "제목을 입력해주세요")
    @NotNull(message = "제목을 입력해주세요")
    private String title;
    @NotBlank(message = "내용을 입력해주세요")
    @NotNull(message = "내용을 입력해주세요")
    private String contents;
    @NotBlank(message = "기간을 정해주세요")
    @NotNull(message = "기간을 정해주세요")
    // TODO byul : 0~24까지만
    private String deadline;
    @NotBlank(message = "인원을 정해주세요")
    @NotNull(message = "인원을 정해주세요")
    private Integer capacity;
    private Long categoryIdx;
    @NotBlank(message = "하위카테고리를 입력해주세요")
    @NotNull(message = "하위카테고리를 입력해주세요")
    private String btmCategory;
}
