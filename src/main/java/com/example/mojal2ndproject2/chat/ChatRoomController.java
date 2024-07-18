package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    //전체 채팅방 정보 맵
    private Map<Long, List<ChatMessage>> chatRooms = new HashMap<>();

    //전체 채팅방 idx 조회, return값을 chatRooms.keySet() -> KeyList로 변경
    //TODO ej 클라이언트에서 처리 가능?
    @GetMapping("/rooms")
    public List<Long> getChatRooms(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        //TODO ej 내가있는 채팅방만 보이게 처리하기
        List<Long> myChatRoomIds = chatRoomService.findMyChatRoomList(customUserDetails);

        List<Long> keyList = new ArrayList<>();
        chatRooms.forEach((k,v) -> {
            if(myChatRoomIds.contains(k) ) keyList.add(k); // 키값이 내가있는 채팅방 아이디면 저장
        });

        return keyList;
    }


    //메세지 가져오는 부분
    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessage> getMessages(@PathVariable Long roomId) {
        //전체 채팅방 정보 맵에, 받은 룸id키의 값에 메세지리스트 가져오기
        return chatRooms.getOrDefault(roomId, new ArrayList<>()); //메세지목록반환하고 없으면 빈 목록 반환
    }

    //메세지 넣는 부분
    @PostMapping("/rooms/{roomId}/messages")
    public void addMessage(@PathVariable Long postIdx, @RequestBody ChatMessage chatMessage, @AuthenticationPrincipal CustomUserDetails customUserDetails) { //포스트id, 룸id,
//        chatRooms.computeIfAbsent(postIdx, k -> new ArrayList<>()).add(chatMessage); //값이 없는 채팅방키에 밸류값 넣어주는 함수

        //포스트id를 받아서 작성자랑, 참여자 사이에 채팅방이 있는지 확인 -> chatRoom 테이블에서 확인
        if(!chatRoomService.findChatRoom(postIdx, chatMessage, customUserDetails)) {
            //채팅방 있으면 true 반환되는데, 없으면 저장해줘야하니까 ! 붙이기

            //없으면 새로 생성하고 디비에 저장하고, 맵에 수동으로 저장하기
            ChatRoom newChatRoom = chatRoomService.create(postIdx, customUserDetails);
            chatRooms.put(newChatRoom.getIdx(), new ArrayList<>()).add(chatMessage);
        }

        //있으면 넘어가기




    }
}
