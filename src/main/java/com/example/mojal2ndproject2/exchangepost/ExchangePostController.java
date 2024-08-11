package com.example.mojal2ndproject2.exchangepost;

import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import static com.example.mojal2ndproject2.common.BaseResponseStatus.MEMBER_NOT_LOGIN;

import com.example.mojal2ndproject2.common.annotation.Timer;
import com.example.mojal2ndproject2.exchangepost.model.dto.request.CreateExchangePostReq;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.CreateExchangePostRes;
import com.example.mojal2ndproject2.exchangepost.model.dto.response.ReadExchangePostRes;
import com.example.mojal2ndproject2.file.CloudFileUploadService;
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
import org.springframework.web.multipart.MultipartFile;

@ControllerAdvice
@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class ExchangePostController {
    private final ExchangePostService exchangePostService;
    private final CloudFileUploadService cloudFileUploadService;

    // 내가 작성한 교환글 전체 조회
    @Timer
    @Operation( summary = "내가 작성한 교환글 전체 조회",
            description = "로그인 한 회원이 작성한 교환글 전체를 리스트로 조회합니다.")
    @RequestMapping(method = RequestMethod.GET, value = "/my/list")
    public BaseResponse<List<ReadExchangePostRes>> authorExchangeList (
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            Integer page, Integer size) {
        Member member = customUserDetails.getMember();
        BaseResponse<List<ReadExchangePostRes>> response = exchangePostService.authorExchangeList(member, page, size);
        return response;
    }


    // 내가 참여한 교환글 전체 조회
    @Timer
    @Operation( summary = "내가 참여한 교환글 전체 조회",
            description = "로그인 한 회원이 교환에 성공하여 교환 매칭된 교환글 전체를 리스트로 조회합니다.")
    @RequestMapping(method = RequestMethod.GET, value = "/joined/list")
    public BaseResponse<List<ReadExchangePostRes>> enrolledExchangeList (@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = customUserDetails.getMember();
        BaseResponse<List<ReadExchangePostRes>> response = exchangePostService.exchangeList(member);
        return response;
    }

    //교환글생성
    @Operation(summary = "교환글 작성",
            description = "회원은 교환글을 작성할 수 있습니다." +
                    "교환글은 작성자가 줄 카테고리(giveCategoryIdx)와, 받을 카테고리(takeCategoryIdx) 둘 다 명시해야 합니다. " +
                    "줄 카테고리는 작성자가 선택했던 내 재능 카테고리 안에 포함되어있어야 합니다",
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
    public BaseResponse<CreateExchangePostRes> create(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                      @Valid @RequestBody CreateExchangePostReq req) throws BaseException {
        Member member = customUserDetails.getMember();
        CreateExchangePostRes res = exchangePostService.create(req, member);
        return new BaseResponse<>(res);
    }

    //교환글전체조회
    @Timer
    @Operation( summary = "교환글 전체 조회",
            description = "등록된 교환글 전체를 리스트로 조회합니다.")
    @RequestMapping(method = RequestMethod.GET,value = "/list")
    public BaseResponse<List<ReadExchangePostRes>> list(Integer page, Integer size) throws BaseException{
        List<ReadExchangePostRes> res = exchangePostService.list(page, size);
        return new BaseResponse<>(res);
    }

    //교환글해당글조회
    @Operation( summary = "교환글 상세 조회",
            description = "각 교환글의 idx로 교환글 하나의 상세 내용을 조회합니다.")
    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public BaseResponse<ReadExchangePostRes> read(@RequestParam Long idx) throws BaseException {
        ReadExchangePostRes res = exchangePostService.read(idx);
        return new BaseResponse<>(res);
    }

}