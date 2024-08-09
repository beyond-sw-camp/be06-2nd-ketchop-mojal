package com.example.mojal2ndproject2.member.emailAuth.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailAuthReq {
    private String email;
    private String uuid;
}
