package com.example.mojal2ndproject2.chat;

import com.example.mojal2ndproject2.chat.model.ChatMessage;
import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.chat.model.dto.request.RoomCreateReq;
import com.example.mojal2ndproject2.chat.model.dto.response.MessageGetRes;
import com.example.mojal2ndproject2.chat.model.dto.response.RoomGetRes;
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
    @Operation(summary = "채팅방 전체 조회", description = "현재 로그인한 유저가 속한 채팅방 전체를 리스트로 조회합니다.")
    @GetMapping("/rooms")
    public List<RoomGetRes> getChatRooms(Long userIdx, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        //반환값 List<Long>에서 List<RoomGetRes>로 변경

        List<RoomGetRes> myChatRoomIds = chatRoomService.findMyChatRoomList(userIdx, customUserDetails);

        return myChatRoomIds;
    }


    //채팅방 하나의 메시지 목록 조회
    @Operation(summary = "채팅방 메세지 목록 조회", description = "현재 입장한 채팅방의 과거 메시지를 조회합니다.")
    @GetMapping("/rooms/{roomIdx}/messages")
    public List<MessageGetRes> getMessages(@PathVariable String roomIdx) {
        //클라이언트로 과거메세지 리스트를 보내줄 때, chatMessage객체 자체가 넘어가서 json형식으로 변환될 때 각 객체끼리 연관관계 때문에 순환참조오류가 발생함
        //순환참조 오류발생하여 수정
        //dto로 리스폰스 타입 변경함

        //디비에서 해당 채팅방의 메세지 가져오는 코드 추가
        List<MessageGetRes> currentRoomMessages = chatRoomService.findCurrentMessageList(Long.parseLong(roomIdx));

        return currentRoomMessages;
    }


    //채팅방 생성
    @Operation(summary = "채팅방 생성",
            description = "회원은 교환글에서 채팅하기 버튼을 클릭하여 채팅방을 생성할 수 있습니다. " +
                    "교환글의 idx(postIdx)와 참여자의 idx(participants)를 필요로 합니다. " +
                    "채팅방 생성에 앞서 교환글, 글 작성자, 참여자 사이에 이미 만들어진 채팅방이 있는지 확인합니다. " +
                    "있다면 그 채팅방의 정보를 넘겨주고, 없다면 카테고리 부분을 확인합니다. " +
                    "그 후, 글 작성자가 교환글에서 설정한 받을 카테고리(takeCategoryIdx)가 참여자의 재능 카테고리에 있는지 확인합니다. " +
                    "있다면 채팅방을 생성하고 db에 저장합니다. ")
    @PostMapping("/rooms/{postIdx}/messages")
    public Long createRoom(@PathVariable String postIdx, @RequestBody RoomCreateReq roomCreateReq, @AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException { //포스트id, 룸id,
        //@PathVariable String postIdx, 뺌?

        //포스트id를 받아서 작성자랑, 참여자 사이에 채팅방이 있는지 확인 -> chatRoom 테이블에서 확인
        Long roomIdx = chatRoomService.findChatRoom(roomCreateReq);
        if(roomIdx == 0L) {
            //채팅방 있으면 true 반환되는데, 없으면 저장해줘야하니까 ! 붙이기 -> 수정
            //방있으면 방 idx 반환, 0L이면 생성된 방이 없으므로 만들어주기

            //없으면 새로 생성하고 디비에 저장
            ChatRoom newChatRoom = chatRoomService.create(roomCreateReq);
            return newChatRoom.getIdx();

        }
        //있으면 넘어가기 -> 수정. 있으면 방 idx 반환
        return roomIdx;
    }
}
