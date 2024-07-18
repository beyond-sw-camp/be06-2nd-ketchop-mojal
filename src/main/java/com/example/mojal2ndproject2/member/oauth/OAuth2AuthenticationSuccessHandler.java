package com.example.mojal2ndproject2.member.oauth;

import com.example.mojal2ndproject2.config.jwt.JwtUtil;
import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
        String nickName =  (String) properties.get("nickname");
        Long kakaoIdx = (Long) oAuth2User.getAttributes().get("id");

        // nickName으로 DB 확인
        Optional<Member> result = memberRepository.findByNicknameAndKakaoIdx(nickName, kakaoIdx);
        Member saved=null;
        if(!result.isPresent()){

            Member member = Member.builder()
                    .nickname(nickName)
                    .kakaoIdx(kakaoIdx)
                    .role("ROLE_USER")
                    .emailAuth(true)
                    .build(); //Todo signupdate 값 넣기

            saved = memberRepository.save(member);
        }else{
            saved = result.get();
        }
        Long idx = saved.getIdx();

        String token = jwtUtil.createToken(nickName, idx, "ROLE_USER"); //Todo email 넣는 자리에 nickname 넣음..?

        Cookie aToken = new Cookie("aToken", token);
        aToken.setHttpOnly(true);
        aToken.setSecure(true);
        aToken.setPath("/");
        aToken.setMaxAge(60 * 60 * 1);

        response.addCookie(aToken);
        getRedirectStrategy().sendRedirect(request, response, "http://localhost/test.html"); //Todo redirectURL을 카테고리 선택 화면으로?
    }
}
