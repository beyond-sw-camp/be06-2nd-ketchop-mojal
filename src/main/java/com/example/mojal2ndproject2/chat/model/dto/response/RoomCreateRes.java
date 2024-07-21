package com.example.mojal2ndproject2.chat.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RoomCreateRes {
    private final Long roomIdx;
    private final String roomName;

    private final String owner;
    private final String participant;
}
