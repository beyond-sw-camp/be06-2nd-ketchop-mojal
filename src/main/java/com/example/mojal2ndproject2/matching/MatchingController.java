package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matching/check")
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;

    //로그인한 회원이 나눔글에서 선착순으로 나눔 받기에 성공했을 때 -> 매칭테이블로 들어간다 (작성자, 참여자, 나눔글) 각각
    //로그인한 회원이 교환글에서 채팅을 하다가 합의하에 교환에 성공했을 때 -> 매칭테이블로 들어간다 (작성자, 참여자, 교환글) 각각

    //TODO ej 여기로 나눔글 선착순신청 이동?

    //교환글 채팅 안에서 교환하기 버튼 클릭시
    @RequestMapping(method = RequestMethod.GET, value = "/exchange")
    public ResponseEntity<String> checkExchange(ChatRoom chatRoom){

        String result = matchingService.checkExchange(chatRoom);

        return ResponseEntity.ok(result);
    }

}
