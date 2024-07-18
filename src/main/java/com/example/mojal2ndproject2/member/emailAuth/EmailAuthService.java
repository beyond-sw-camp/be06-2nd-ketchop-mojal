package com.example.mojal2ndproject2.member.emailAuth;

import static com.example.mojal2ndproject2.common.BaseResponseStatus.POST_USERS_UNAUTH_EMAIL;
import static com.example.mojal2ndproject2.common.BaseResponseStatus.SUCCESS;

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

        String uuid = UUID.randomUUID().toString();

        message.setText("http://localhost:8080/email/verify?email="+email+"&uuid="+uuid);

        emailSender.send(message);

        return uuid;
    }

    public void save(String email, String uuid){ //@반환값?
        EmailAuth emailAuth = EmailAuth.builder()
                .email(email)
                .uuid(uuid)
                .build();

        emailAuthRepository.save(emailAuth);
    }

    public BaseResponse<BaseResponseStatus> verify(String email, String uuid) {
        Optional<EmailAuth> result = emailAuthRepository.findByEmailAndUuid(email, uuid);
        if(result.isPresent()){
            Member member = memberRepository.findByEmail(email).get();//Todo orElseThrow
            member.setEmailAuth(true);
            memberRepository.save(member);
        }else{
            return new BaseResponse<>(POST_USERS_UNAUTH_EMAIL);
        }
        return new BaseResponse<>(SUCCESS);
    }
}
