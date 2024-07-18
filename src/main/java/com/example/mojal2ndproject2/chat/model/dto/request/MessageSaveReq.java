package com.example.mojal2ndproject2.chat.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//채팅 메시지 create
//발송자, 메시지, 채팅방 정보가 담겨 요청
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSaveReq {

    public enum MessageType{
        ENTER, TALK
    }

    private String userName;
    private String msg;
    private int roomNumber;

}
