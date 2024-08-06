package com.example.mojal2ndproject2.chat;
import com.example.mojal2ndproject2.chat.model.ChatMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    //config 파일에서 setApplicationDestinationPrefixes()를 통해 prefix를 "/app"으로 설정 해주었기 때문에, 경로가 한번 더 수정되어 “/app/chat.sendMessage/{roomId}”로 바뀐다.
    @PostMapping("/chat.sendMessage/{currentRoomIdx}")
    @Operation(summary = "메시지 전송 (스웨거로 테스트 x)",
            description = "클라이언트가 현재 채팅방(currentRoomIdx)에 메시지를 보낼 때, 해당 메시지를 저장하고, 채팅방을 구독하는 모든 클라이언트에게 실시간으로 메시지를 전송합니다.")
    @MessageMapping("/chat.sendMessage/{currentRoomIdx}") //마지막 {}부분 클라이언트의 설정과 맞아야 함
    @SendTo("/topic/{currentRoomIdx}") //메시지를 해당 채팅방(예: /topic/1)을 구독하는 모든 클라이언트에게 전송한다
    public ChatMessage sendMessage(@DestinationVariable Long currentRoomIdx, @Payload ChatMessage chatMessage) {
        //@DestinationVariable Long roomIdx : url에서 roomIdx를 추출하여 매칭시켜주는 어노테이션
        //@Payload ChatMessage chatMessage: 메시지 본문을 ChatMessage 객체로 변환하여 처리

        log.info("[SENDER - {}] messages : {}, timestamp : {}",
                chatMessage.getSenderIdx(),
                chatMessage.getMessage(),
                chatMessage.getTimeStamp()); //로그 확인용

        //현재 클라이언트에서 전송된 챗메시지 DTO를 매개변수에서 바로 객체로 바꿔서 save에 전달하고 있음
        chatMessageService.save(currentRoomIdx, chatMessage);

        return chatMessage; //클라이언트에게 반환된 메시지는 실시간으로 구독자들에게 전송
    }

    //topic이라서, 1:n라서, 여러명 들어올 수 있으니까 아래 메소드가 필요한것 같다..?
    @MessageMapping("/chat.addUser/{currentRoomIdx}")
    @Operation(summary = "채팅방에 새 사용자 추가 (스웨거로 테스트 x)",
            description = "WebSocket을 통해 새로운 사용자가 채팅방에 참여할 때 호출되며, " +
                    "해당 사용자의 정보를 WebSocket 세션에 저장하고, 참여 사실을 다른 클라이언트들에게 알립니다. ")
    @SendTo("/topic/{currentRoomIdx}")
    @PostMapping("/chat.addUser/{currentRoomIdx}")
    public ChatMessage addUser(@DestinationVariable Long currentRoomIdx, @Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderIdx());//sendermessage?
        headerAccessor.getSessionAttributes().put("username", chatMessage.getMember().getIdx());
        log.info("[ADDUSER - {}] ", chatMessage.getMember().getNickname());
        return chatMessage;
    }
}
