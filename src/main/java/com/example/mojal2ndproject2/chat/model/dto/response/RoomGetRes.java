package com.example.mojal2ndproject2.chat.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoomGetRes {

    private Long roomIdx;

    private String postWriterNickname;
    private String participantNickname;

    private Long postIdx;
    private String title;
    private String giveBtmCategory;
    private String takeBtmCategory;
    private Boolean status;

    private String lastMessage;
    private String lastMessageTimeStamp;
}
