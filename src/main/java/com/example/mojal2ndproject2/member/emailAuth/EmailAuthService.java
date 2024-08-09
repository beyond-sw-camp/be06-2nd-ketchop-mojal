package com.example.mojal2ndproject2.member.emailAuth;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.POST_USERS_UNAUTH_EMAIL;
import static com.example.mojal2ndproject2.common.BaseResponseStatus.SUCCESS;

import com.example.mojal2ndproject2.common.BaseException;
import com.example.mojal2ndproject2.common.BaseResponse;
import com.example.mojal2ndproject2.common.BaseResponseStatus;
import com.example.mojal2ndproject2.member.emailAuth.model.EmailAuth;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailAuthService {
    private final JavaMailSender emailSender;
    private final EmailAuthRepository emailAuthRepository;
    private final MemberRepository memberRepository;

    public String sendEmail(String email) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("MOJAL - 이메일 인증");

//        String uuid = UUID.randomUUID().toString();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);

//        message.setText("http://43.203.218.195:8080/email/verify?email="+email+"&uuid="+uuid);
        message.setText(uuid);

        emailSender.send(message);

        save(email, uuid);

//        return uuid;
        return "이메일 전송 성공";
    }

    public void save(String email, String uuid){ //@반환값?
        EmailAuth emailAuth = EmailAuth.builder()
                .email(email)
                .uuid(uuid)
                .build();

        emailAuthRepository.save(emailAuth);
    }

    public BaseResponse<BaseResponseStatus> verify(String email, String uuid) throws BaseException {
        EmailAuth result = emailAuthRepository.findByEmailAndUuid(email, uuid)
                .orElseThrow(()->new BaseException(POST_USERS_UNAUTH_EMAIL));

//        Member member = memberRepository.findByEmail(email).get();
//        Member newMember = Member.builder()
//                .idx(member.getIdx())
//                .nickname(member.getNickname())
//                .password(member.getPassword())
//                .email(member.getEmail())
//                .role(member.getRole())
//                .signupDate(member.getSignupDate())
//                .emailAuth(true)
//                .build();
//
//        memberRepository.save(newMember);

        return new BaseResponse<>(SUCCESS);
    }
}
