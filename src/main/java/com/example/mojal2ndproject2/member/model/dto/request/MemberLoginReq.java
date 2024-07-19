package com.example.mojal2ndproject2.member.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginReq {
    @Email(message = "이메일 형식을 확인해주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
}
