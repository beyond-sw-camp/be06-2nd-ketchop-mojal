package com.example.mojal2ndproject2.emailAuth;

import com.example.mojal2ndproject2.emailAuth.model.EmailAuth;
import com.example.mojal2ndproject2.emailAuth.model.dto.request.EmailAuthReq;
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

    public String verify(String email, String uuid) {
        Optional<EmailAuth> result = emailAuthRepository.findByEmailAndUuid(email, uuid);
        if(result.isPresent()){
            Member member = memberRepository.findByEmail(email).get();//Todo orElseThrow
            member.setMemberAuth(true);
            memberRepository.save(member);
        }else{
            return "이메일 인증 정보가 일치하지 않습니다.";
        }
        return "이메일 인증 성공";
    }
}
