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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/app")
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    //config 파일에서 setApplicationDestinationPrefixes()를 통해 prefix를 "/app"으로 설정 해주었기 때문에,
    //경로가 한번 더 수정되어 “/app/chat.sendMessage/{roomId}”로 바뀐다.
    @MessageMapping("/chat.sendMessage/{roomId}") //sendMessage 누르면 여기로 옴
    @SendTo("/topic/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Long roomIdx, @Payload ChatMessage chatMessage) {
        log.info("[SENDER - {}] messages : {}, timestamp : {}", chatMessage.getSenderIdx(), chatMessage.getMessage(), chatMessage.getTimeStamp());

        //디비에 저장하기
        chatMessageService.save(roomIdx, chatMessage);

        return chatMessage;
    }

    //topic이라서, 1:n라서, 여러명 들어올 수 있으니까 아래 메소드가 필요한것 같다..?
    @MessageMapping("/chat.addUser/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage addUser(@DestinationVariable Long roomId, @Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderIdx());
        log.info("[ADDUSER - {}] ", chatMessage.getSenderIdx());
        return chatMessage;
    }
}
