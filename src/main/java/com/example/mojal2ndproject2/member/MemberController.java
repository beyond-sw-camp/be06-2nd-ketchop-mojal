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
        String uuid = emailAuthService.sendEmail(request.getEmail());
        emailAuthService.save(request.getEmail(), uuid);
        //Todo byul: redirect URL로 카테고리 선택하는 창으로 + 카카오에서도

        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add/category")
    public BaseResponse<List<Long>> addCategory(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                          @RequestBody MemberAddCategoryReq request){
        Member user = customUserDetails.getMember();
        List<Long> ret = memberService.addCategory(user, request);
        BaseResponse<List<Long>> result = new BaseResponse<>(ret);
        return result;
    }
}
