package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.exchangepost.model.dto.respone.ExchangePostReadRes;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/post/exchange")
@RequiredArgsConstructor
public class ExchangePostController {
    private final ExchangePostService exchangePostService;

    // 내가 작성한 교환글 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "/users/author/list")
    public ResponseEntity<List<ExchangePostReadRes>> authorExchangeList (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long requestIdx = customUserDetails.getMember().getIdx();
        List<ExchangePostReadRes> response = exchangePostService.authorExchangeList(requestIdx);
        return ResponseEntity.ok(response);
    }


    // 내가 참여한 교환글 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "/users/list")
    public ResponseEntity<List<ExchangePostReadRes>> exchangeList (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long requestIdx = customUserDetails.getMember().getIdx();
        List<ExchangePostReadRes> response = exchangePostService.exchangeList(requestIdx);
        return ResponseEntity.ok(response);
    }
}
