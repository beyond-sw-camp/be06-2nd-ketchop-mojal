package com.example.mojal2ndproject2.sharePost;

import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.sharePost.model.dto.request.SharePostCreateReq;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostListRes;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostCreateRes;
import com.example.mojal2ndproject2.sharePost.model.dto.response.SharePostReadRes;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post/share")
@RequiredArgsConstructor
public class SharePostController {
    private final SharePostService sharePostService;

    @RequestMapping(method = RequestMethod.POST, value = "/users/create")
    public BaseResponse<SharePostCreateRes> create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody SharePostCreateReq request) throws BaseException {
        Long requestIdx = customUserDetails.getMember().getIdx();
        SharePostCreateRes result = sharePostService.create(requestIdx, request);
        return new BaseResponse<>(result);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/enrollment")
    public BaseResponse<String> enrollment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                           Long idx) throws BaseException {
        Member member = customUserDetails.getMember();
        BaseResponse<String> result = sharePostService.enrollment(member, idx);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<SharePostReadRes> read(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 Long idx) throws BaseException {
        Long requestIdx = customUserDetails.getMember().getIdx();
        SharePostReadRes result = sharePostService.read(requestIdx, idx);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/list")
    public ResponseEntity<List<SharePostReadRes>> list(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long requestIdx = customUserDetails.getMember().getIdx();
        List<SharePostReadRes> result = sharePostService.list(requestIdx);
        return ResponseEntity.ok(result);
    }

    //내가 작성한 나눔글 전체조회
    @RequestMapping(method = RequestMethod.GET, value = "/users/author/list") //git conflict - uri 수정
    public ResponseEntity<List<SharePostListRes>> authorList(@AuthenticationPrincipal CustomUserDetails customUserDetails) { //토큰보내기
        //로그인한 유저 정보
        Long loginUserIdx = customUserDetails.getMember().getIdx();

        List<SharePostListRes> response= sharePostService.authorList(loginUserIdx);
        return ResponseEntity.ok(response);

    }

    //내가 참여한 글 전체조회
    @RequestMapping(method = RequestMethod.GET, value = "/users/enrolled/list")
    public ResponseEntity<List<SharePostListRes>> enrolledList(@AuthenticationPrincipal CustomUserDetails customUserDetails) { //토큰보내기
        //로그인한 유저 정보
        Member member = customUserDetails.getMember();

        List<SharePostListRes> response= sharePostService.enrolledList(member);
        return ResponseEntity.ok(response);
    }
}
