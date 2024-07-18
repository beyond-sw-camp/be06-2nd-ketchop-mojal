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

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public BaseResponse<MemberSignupRes> signup(@RequestBody MemberSignupReq request) throws BaseException {

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
