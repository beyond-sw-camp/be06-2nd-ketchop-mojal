package com.example.mojal2ndproject2.member.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberSignupRes {
    private Long idx;
    private String email;
    private String nickName;
}
