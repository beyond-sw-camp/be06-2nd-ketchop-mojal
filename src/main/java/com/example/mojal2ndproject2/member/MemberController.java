package com.example.mojal2ndproject2.member;

import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.member.emailAuth.EmailAuthService;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import com.example.mojal2ndproject2.member.model.dto.request.MemberAddCategoryReq;
import com.example.mojal2ndproject2.member.model.dto.request.MemberSignupReq;
import com.example.mojal2ndproject2.member.model.dto.response.MemberAddCategoryRes;
import com.example.mojal2ndproject2.member.model.dto.response.MemberSignupRes;
import com.example.mojal2ndproject2.member.model.dto.response.MyInfoReadRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.MYINFO_EMPTY_LOGINUSER;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailAuthService emailAuthService;

    @Operation(
            summary = "회원가입",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(name = "Valid example", value = """
                               {
                                 "nickname": "yourNickname",
                                 "email": "test@email.com",
                                 "password": "Qwer1234!"
                               }"""),
                                    @ExampleObject(name = "Invalid email", value = """
                               {
                                 "nickname": "yourNickname",
                                 "email": "test",
                                 "password": "Qwer1234!"
                               }"""),
                                    @ExampleObject(name = "Invalid password", value = """
                               {
                                 "nickname": "yourNickname",
                                 "email": "test@email.com",
                                 "password": "qwer1234"
                               }""")
                            }
                    )
            ))
    @Tag(name = "signup")
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public BaseResponse<MemberSignupRes> signup(@Valid @RequestBody MemberSignupReq request) throws BaseException {

        BaseResponse<MemberSignupRes> result = memberService.signup(request);
        emailAuthService.sendEmail(request.getEmail());
        return result;
    }

    @Operation( summary = "내 재능 카테고리 추가")
    @RequestMapping(method = RequestMethod.POST, value = "/add/category")
    public BaseResponse<List<Long>> addCategory(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                          @RequestBody MemberAddCategoryReq request){
        Member user = customUserDetails.getMember();
        List<Long> ret = memberService.addCategory(user, request);
        BaseResponse<List<Long>> result = new BaseResponse<>(ret);
        return result;
    }

    @Operation( summary = "내 정보 조회")
    @RequestMapping(method = RequestMethod.POST, value = "/myInfo/read")
    public BaseResponse<MyInfoReadRes> myInfoRead(@AuthenticationPrincipal CustomUserDetails customUserDetails) throws BaseException{
        if(customUserDetails==null){ //로그인하지 않은 사용자가 내 정보 조회를 시도할 때
            throw new BaseException(MYINFO_EMPTY_LOGINUSER);
        }

        //지금은 마이페이지 들어가서 조회누르면 바로 로그인한 유저 정보가 출력됨
        //Todo ej 마이페이지 들어가서 이메일 패스워드 한번 더 입력하면 -> 그 입력한(찾으려는) 계정과 로그인한 계정이 맞는지 확인하고 출력하는것으로 언데이트 하기?

        Member user = customUserDetails.getMember();
        MyInfoReadRes ret = memberService.myInfoRead(user);
        BaseResponse<MyInfoReadRes> result = new BaseResponse<>(ret);
        return result;
    }
}
