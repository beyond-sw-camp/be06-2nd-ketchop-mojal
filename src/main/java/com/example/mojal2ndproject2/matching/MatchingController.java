package com.example.mojal2ndproject2.matching;

import com.example.mojal2ndproject2.chat.model.ChatRoom;
import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.matching.model.PostMatchingMember;
import com.example.mojal2ndproject2.matching.model.dto.MatchingConfirmedReq;
import com.example.mojal2ndproject2.matching.model.dto.MatchingMemberRes;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matching/check")
@RequiredArgsConstructor
public class MatchingController {
    private static final Logger log = LoggerFactory.getLogger(MatchingController.class);
    private final MatchingService matchingService;

    //TODO ej 여기로 나눔글 선착순신청 이동?

    //교환글 채팅 안에서 교환하기 버튼 클릭시
    @Operation( summary = "교환 상대 확정")
//    @RequestMapping(method = RequestMethod.GET, value = "/exchange")
    @PostMapping("/exchange")
    public BaseResponse<MatchingMemberRes> checkExchange(@AuthenticationPrincipal CustomUserDetails customUserDetails , @RequestBody MatchingConfirmedReq confirmedReq) throws BaseException {

//        Member user = customUserDetails.getMember();
        MatchingMemberRes result = matchingService.checkExchange(confirmedReq);

        log.info("[member1]: {} [member2]: {} [exchangePostIdx]: {}",
                result.getMember1Idx(),
                result.getMember2Idx(),
                result.getExchangePostIdx());
        return new BaseResponse<>(result);
    }

}
