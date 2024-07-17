package com.example.mojal2ndproject2.member;

import com.example.mojal2ndproject2.emailAuth.EmailAuthService;
import com.example.mojal2ndproject2.member.model.dto.request.MemberSignupReq;
import com.example.mojal2ndproject2.member.model.dto.response.MemberSignupRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MemberSignupRes> signup(@RequestBody MemberSignupReq request){

        String uuid = emailAuthService.sendEmail(request.getEmail());
        emailAuthService.save(request.getEmail(), uuid);
        MemberSignupRes memberSignupRes = memberService.signup(request);

        return ResponseEntity.ok(memberSignupRes);
    }
}
