package com.example.mojal2ndproject2.member.model.dto.request;

import lombok.Getter;

@Getter
public class MemberModifyReq {
    private Long idx;
    private String nickname;
    private Boolean firstLogin;
}
