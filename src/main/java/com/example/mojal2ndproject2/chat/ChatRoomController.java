package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.chat.model.dto.request.RoomCreateReq;
import com.example.mojal2ndproject2.chat.model.dto.response.MessageGetRes;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
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
    //맵을 쓰는 이유>???????? 우리느 그냥 디비에서 가져오면 되는것인디??? 맵을 사용하지 않아봤습니다 일단.


    //로그인 한 유저의 전체 채팅방 조회
    @Operation(summary = "유저 채팅방 조회")
    @GetMapping("/rooms")
    public List<Long> getChatRooms(Long idx, @AuthenticationPrincipal CustomUserDetails customUserDetails) {//onConneted 에서 호출이되는놈
        //TODO ej 내가있는 채팅방만 보이게 처리하기
        List<Long> myChatRoomIds = chatRoomService.findMyChatRoomList(idx, customUserDetails); //진짜 chatRoom idx를 가져온 리스트임

        return myChatRoomIds;
    }


    //전체 채팅방 정보 맵에서, 받은 룸id키의 값에 메세지리스트 가져오기
    //TODO 0720 순환참조 오류발생하여 수정코드
    //클라이언트로 과거메세지 리스트를 보내줄 때, chatMessage객체 자체가 넘어가서 json형식으로 변환될 때 각 객체끼리 연관관계 때문에 순환참조오류가 발생함
    //dto로 리스폰스 타입 변경함
    @Operation(summary = "현재 채팅방 메세지 목록 조회")
    @GetMapping("/rooms/{roomIdx}/messages")
    public List<MessageGetRes> getMessages(@PathVariable String roomIdx) {
        //디비에서 해당 채팅방의 메세지 가져오는 코드 추가
        List<MessageGetRes> currentRoomMessages = chatRoomService.findCurrentMessageList(Long.parseLong(roomIdx));

        return currentRoomMessages;
    }


    //채팅방 생성
    @Operation(summary = "채팅방 생성")
    @PostMapping("/rooms/{postIdx}/messages")
    public void createRoom(@PathVariable String postIdx, @RequestBody RoomCreateReq roomCreateReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException { //포스트id, 룸id,
        //@PathVariable String postIdx, 뺌?


        //포스트id를 받아서 작성자랑, 참여자 사이에 채팅방이 있는지 확인 -> chatRoom 테이블에서 확인
        if(!chatRoomService.findChatRoom(roomCreateReq)) {
            //채팅방 있으면 true 반환되는데, 없으면 저장해줘야하니까 ! 붙이기

            //없으면 새로 생성하고 디비에 저장하고, 맵에 수동으로 저장하기
            ChatRoom newChatRoom = chatRoomService.create(roomCreateReq);

        }
        //있으면 넘어가기
    }
}
