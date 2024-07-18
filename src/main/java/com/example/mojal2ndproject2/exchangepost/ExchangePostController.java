package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.common.BaseResponse;
import static com.example.mojal2ndproject2.common.BaseResponseStatus.MEMBER_NOT_LOGIN;
import static com.example.mojal2ndproject2.common.BaseResponseStatus.TITLE_NOT_ENTERED;
import static com.example.mojal2ndproject2.common.BaseResponseStatus.CATEGORY_NOT_SELECTED;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.ReadExchangePostRes;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/exchange")
public class ExchangePostController {
    private final ExchangePostService exchangePostService;

    // 내가 작성한 교환글 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "/users/author/list")
    public ResponseEntity<List<ReadExchangePostRes>> authorExchangeList (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long requestIdx = customUserDetails.getMember().getIdx();
        List<ReadExchangePostRes> response = exchangePostService.authorExchangeList(requestIdx);
        return ResponseEntity.ok(response);
    }


    // 내가 참여한 교환글 전체 조회
    @RequestMapping(method = RequestMethod.GET, value = "/users/list")
    public ResponseEntity<List<ReadExchangePostRes>> exchangeList (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long requestIdx = customUserDetails.getMember().getIdx();
        List<ReadExchangePostRes> response = exchangePostService.exchangeList(requestIdx);
        return ResponseEntity.ok(response);
    }

    //교환글생성
    @RequestMapping(method = RequestMethod.POST, value = "/users/create")
    public BaseResponse<CreateExchangePostRes> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody CreateExchangePostReq req) {
        CreateExchangePostRes res = exchangePostService.create(req, customUserDetails);
        return new BaseResponse<>(res);
    }

    //교환글전체조회
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public BaseResponse<List<ReadExchangePostRes>> list() {
        List<ReadExchangePostRes> res = exchangePostService.list();
        return new BaseResponse<>(res);
    }

    //교환글해당글조회
    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public BaseResponse<ReadExchangePostRes> read(@RequestParam Long id) {
        ReadExchangePostRes res = exchangePostService.read(id);
        return new BaseResponse<>(res);
    }

}