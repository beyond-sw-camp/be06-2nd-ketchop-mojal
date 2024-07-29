package com.example.mojal2ndproject2.member.emailAuth;

import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailAuthService;

    @Operation( summary = "이메일 인증 (스웨거로 테스트 x, 회원가입 후 실제 메일함을 확인 해 주세요.)",
            description = "회원가입 시 등록한 이메일로 온 인증 url을 클릭하면, 이메일 인증이 완료되어 고유 uuid 생성 후 db에 저장됩니다.")
    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    public BaseResponse<BaseResponseStatus> verify(String email, String uuid){
        BaseResponse<BaseResponseStatus> result = emailAuthService.verify(email, uuid);
        //Todo byul: redirect URL로 카테고리 선택하는 창으로 + 카카오에서도
        return result;
    }
}
