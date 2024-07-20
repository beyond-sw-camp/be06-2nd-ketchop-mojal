package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.matching.model.dto.MatchingMemberRes;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matching/check")
@RequiredArgsConstructor
public class MatchingController {
    private final MatchingService matchingService;

    //TODO ej 여기로 나눔글 선착순신청 이동?

    //교환글 채팅 안에서 교환하기 버튼 클릭시
    @Operation( summary = "교환 상대 확정")
    @RequestMapping(method = RequestMethod.GET, value = "/exchange")
    public BaseResponse<MatchingMemberRes> checkExchange(@AuthenticationPrincipal CustomUserDetails customUserDetails , ChatRoom chatRoom) throws BaseException {

        Member user = customUserDetails.getMember();
        MatchingMemberRes result = matchingService.checkExchange(user, chatRoom);

        return new BaseResponse<>(result);
    }

}
