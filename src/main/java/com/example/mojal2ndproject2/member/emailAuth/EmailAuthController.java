package com.example.mojal2ndproject2.member.emailAuth;

import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailAuthController {
    private final EmailAuthService emailAuthService;

    @RequestMapping(method = RequestMethod.GET, value = "/verify")
    public BaseResponse<BaseResponseStatus> verify(String email, String uuid){
        BaseResponse<BaseResponseStatus> result = emailAuthService.verify(email, uuid);
        return result;
    }
}
