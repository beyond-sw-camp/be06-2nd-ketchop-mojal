package com.example.mojal2ndproject2.member.oauth;

import com.example.mojal2ndproject2.member.MemberRepository;
import com.example.mojal2ndproject2.member.model.Member;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {
//    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        String nickName = (String) properties.get("nickname");

        // nickName으로 DB 확인
//        Optional<Member> result = memberRepository.findByNickname(nickName);
//        if(!result.isPresent()){
//            String uuid = UUID.randomUUID().toString();
//
//            Member member = Member.builder()
//                    .nickname(nickName+"kToken "+uuid)
//                    .role("ROLE_USER")
//                    .memberAuth(true)
//                    .build(); //Todo signupdate 값 넣기
//
//            memberRepository.save(member);
//        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "id"
        );
    }
}
