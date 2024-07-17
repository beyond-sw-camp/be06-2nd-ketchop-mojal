package com.example.mojal2ndproject2.member.model.dto.request;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.POST_USERS_INVALID_EMAIL;

import com.example.mojal2ndproject2.common.BaseException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MemberSignupReq {
    @NotNull(message = "닉네임을 입력해주세요.")
    private String nickname;
    @Email(message = "이메일 형식을 확인해주세요.")
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "비밀번호는 8자 이상, 대문자, 소문자, 특수문자를 포함해야 합니다.")
    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password;
}
