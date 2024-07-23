package com.example.mojal2ndproject2.chat;
import com.example.mojal2ndproject2.chat.model.ChatMessage;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    //config 파일에서 setApplicationDestinationPrefixes()를 통해 prefix를 "/app"으로 설정 해주었기 때문에,
    //경로가 한번 더 수정되어 “/app/chat.sendMessage/{roomId}”로 바뀐다.
    @MessageMapping("/chat.sendMessage/{currentRoomIdx}") //마지막 {}부분을 나는 밑밑에 매개변수와 맞춰야 하는줄 알았는데 클라이언트의 설정과 맞아야 한다..
    @SendTo("/topic/{currentRoomIdx}") //메시지를 해당 채팅방(예: /topic/1)을 구독하는 모든 클라이언트에게 전송한다
    public ChatMessage sendMessage(@DestinationVariable Long currentRoomIdx, @Payload ChatMessage chatMessage) {
        //@DestinationVariable Long roomIdx : url에서 roomIdx를 추출하여 매칭시켜주는 어노테이션
        //@Payload ChatMessage chatMessage: 메시지 본문을 ChatMessage 객체로 변환하여 처리
        log.info("[SENDER - {}] messages : {}, timestamp : {}",
                chatMessage.getMember().getNickname(),
                chatMessage.getMessage(),
                chatMessage.getTimeStamp()); //로그 확인용

        chatMessageService.save(currentRoomIdx, chatMessage);

        return chatMessage; //클라이언트에게 반환된 메시지는 실시간으로 구독자들에게 전송
    }

    //topic이라서, 1:n라서, 여러명 들어올 수 있으니까 아래 메소드가 필요한것 같다..?
    @MessageMapping("/chat.addUser/{currentRoomIdx}")
    @SendTo("/topic/{currentRoomIdx}")
    public ChatMessage addUser(@DestinationVariable Long currentRoomIdx, @Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderIdx());//sendermessage?
        headerAccessor.getSessionAttributes().put("username", chatMessage.getMember().getIdx());
        log.info("[ADDUSER - {}] ", chatMessage.getMember().getNickname());
        return chatMessage;
    }
}
