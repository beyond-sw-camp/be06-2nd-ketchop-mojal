package com.example.mojal2ndproject2.chat.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MessageGetRes { //클라이언트로 보낼 메세지 dto
    private Long idx;
    private Long senderIdx;
    private String message;
    private String timeStamp;
}
