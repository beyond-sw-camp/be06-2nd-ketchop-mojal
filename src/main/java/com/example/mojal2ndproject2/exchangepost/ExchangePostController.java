package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import static com.example.mojal2ndproject2.common.BaseResponseStatus.MEMBER_NOT_LOGIN;

import com.example.mojal2ndproject2.common.annotation.Timer;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.ReadExchangePostRes;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.Time;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class ExchangePostController {
    private final ExchangePostService exchangePostService;

    // 내가 작성한 교환글 전체 조회
    @Timer
    @Operation( summary = "내가 작성한 교환글 전체 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/my/list")
    public BaseResponse<List<ReadExchangePostRes>> authorExchangeList (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        BaseResponse<List<ReadExchangePostRes>> response = exchangePostService.authorExchangeList(member);
        return response;
    }


    // 내가 참여한 교환글 전체 조회
    @Timer
    @Operation( summary = "내가 참여한 교환글 전체 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/joined/list")
    public BaseResponse<List<ReadExchangePostRes>> enrolledExchangeList (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        BaseResponse<List<ReadExchangePostRes>> response = exchangePostService.exchangeList(member);
        return response;
    }

    //교환글생성
    @Operation(summary = "교환글 작성",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Valid example", value = """
                                            {
                                              "giveCategoryIdx": 1,
                                              "giveBtmCategory": "java",
                                              "takeCategoryIdx": 2,
                                              "takeBtmCategory": "python",
                                              "title": "Mytitle",
                                              "contents": "java 알려드릴게요 python 알려주실분!"
                                            }"""),
                                    @ExampleObject(name = "inValid example1 - nonCategory", value = """
                                            {
                                              "giveCategoryIdx": null,
                                              "giveBtmCategory": "",
                                              "takeCategoryIdx": 2,
                                              "takeBtmCategory": "python",
                                              "title": "Mytitle",
                                              "contents": "java 알려드릴게요 python 알려주실분!"
                                            }""")
                            }
                    )
            ))
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public BaseResponse<CreateExchangePostRes> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid @RequestBody CreateExchangePostReq req) throws BaseException {
        Member member = customUserDetails.getMember();
        CreateExchangePostRes res = exchangePostService.create(req, member);
        return new BaseResponse<>(res);
    }

    //교환글전체조회
    @Timer
    @Operation( summary = "전체 교환글 조회")
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public BaseResponse<List<ReadExchangePostRes>> list() throws BaseException{
        List<ReadExchangePostRes> res = exchangePostService.list();
        return new BaseResponse<>(res);
    }

    //교환글해당글조회
    @Operation( summary = "교환글 idx로 조회")
    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public BaseResponse<ReadExchangePostRes> read(@RequestParam Long idx) throws BaseException {
        ReadExchangePostRes res = exchangePostService.read(idx);
        return new BaseResponse<>(res);
    }

}