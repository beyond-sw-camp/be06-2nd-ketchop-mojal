package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.common.annotation.Timer;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.SharePost;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostListRes;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostReadRes;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import groovy.transform.ASTTest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.Time;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/share")
@RequiredArgsConstructor
public class SharePostController {
    private final SharePostService sharePostService;

    @Operation(summary = "나눔글 작성",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Valid example", value = """
                                            {
                                              "title" : "java 과외"
                                              "contents" : "java 알려드립니다~~"
                                              "deadline" : 10
                                              "capacity" : 1
                                              "categoryIdx" : 13
                                              "btmCategory" : "java"
                                            }"""),
                                    @ExampleObject(name = "inValid example", value = """
                                            {
                                              "title" : ""
                                              "contents" : "java 알려드립니다~~"
                                              "deadline" : 10
                                              "capacity" : 2
                                              "categoryIdx" : 13
                                              "btmCategory" : "java"
                                            }"""),
                            }
                    )
            ))
    @RequestMapping(method = RequestMethod.POST, value = "/create")
    public BaseResponse<SharePostCreateRes> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody SharePostCreateReq request) throws BaseException {
        Long requestIdx = customUserDetails.getMember().getIdx();
        SharePostCreateRes result = sharePostService.create(requestIdx, request);
        return new BaseResponse<>(result);
    }

    @Operation( summary = "나눔글 참여")
    @RequestMapping(method = RequestMethod.POST, value = "/join")
    public BaseResponse<String> enrollment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           Long idx) throws BaseException {
        Member member = customUserDetails.getMember();
        BaseResponse<String> result = sharePostService.enrollment(member, idx);
        return result;
    }

    @Operation( summary = "나눔글 idx조회")
    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public BaseResponse<SharePostReadRes> read(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 Long idx) throws BaseException {
        //Todo: customUserDetails가 null일경우, 아닐경우 인자를 다르게 넣어야함
        //이거 확인 안하면 getMember에서 오류남
        //null인지 아닌지에 따라서 작성자인지 아닌지 정보까지 같이 넘기기
        Long requestIdx=null;
        if(customUserDetails!=null){
            requestIdx = customUserDetails.getMember().getIdx();
        }
        SharePostReadRes result = sharePostService.read(requestIdx, idx);
        return new BaseResponse<>(result);
    }

    @Operation( summary = "나눔글 전체 조회")
    @Timer
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public BaseResponse<List<SharePostListRes>> list() {
        List<SharePostListRes> result = sharePostService.list();
        return new BaseResponse<>(result);
    }

    @Operation( summary = "내가 작성한 나눔글 전체 조회")
    @Timer
    @RequestMapping(method = RequestMethod.GET, value = "/my/list") //git conflict - uri 수정
    public BaseResponse<List<SharePostListRes>> authorList(@AuthenticationPrincipal CustomUserDetails customUserDetails) { //토큰보내기
        //로그인한 유저 정보
        Long loginUserIdx = customUserDetails.getMember().getIdx();

        List<SharePostListRes> response= sharePostService.authorList(loginUserIdx);
        return new BaseResponse<>(response);

    }

    @Operation( summary = "내가 참여한 나눔글 전체 조회")
    @Timer
    @RequestMapping(method = RequestMethod.GET, value = "/joined/list")
    public BaseResponse<List<SharePostListRes>> enrolledList(@AuthenticationPrincipal CustomUserDetails customUserDetails) { //토큰보내기
        //로그인한 유저 정보
        Member member = customUserDetails.getMember();

        List<SharePostListRes> response= sharePostService.enrolledList(member);
        return new BaseResponse<>(response);
    }
}
