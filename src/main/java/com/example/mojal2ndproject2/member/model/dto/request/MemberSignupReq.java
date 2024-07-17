package com.example.mojal2ndproject2.member.model.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MemberSignupReq {
    private String nickname;
    private String email;
    private String password;
    private List<Long> categories = new ArrayList<>();
    //5개 제한을 애초에 배열 5개로? 아니면 일단 리스트로?
}
