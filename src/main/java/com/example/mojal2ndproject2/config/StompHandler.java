package com.example.mojal2ndproject2.config;

import com.example.mojal2ndproject2.config.jwt.JwtUtil;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    //preSend 메서드는 ChannelInterceptor 인터페이스의 메서드로, 메시지가 실제로 전송되기 전에 호출됩니다.
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //STOMP 메시지의 헤더 정보를 쉽게 다룰 수 있도록 StompHeaderAccessor 객체로 래핑합니다.
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        //메시지의 STOMP 명령이 CONNECT 인지 확인합니다. 클라이언트가 서버와의 연결을 설정하려고 할 때 사용되는 명령입니다.
        if (accessor.getCommand() == StompCommand.CONNECT) {

            // HandshakeInterceptor에서 설정한 쿠키를 가져옴
            String cookies = (String) accessor.getSessionAttributes().get("cookies");

            // 쿠키에서 JWT 토큰 추출
            if (cookies != null) {
                String token = extractTokenFromCookies(cookies);

                //토큰의 유효성을 검증합니다. 유효하지 않으면 true를 반환합니다.
                if (token != null && !jwtUtil.isExpired(token)) { //0805추가 { ㅎㅎ
                    //토큰이 유효하지 않은 경우 AccessDeniedException 예외를 발생시키고, 이를 잡아서 스택 트레이스를 출력합니다.
                    try {
                        String email = jwtUtil.getEmail(token);
                        Long idx = jwtUtil.getIdx(token);
                        String role = jwtUtil.getRole(token);

                        Member member = Member.builder()
                                .email(email)
                                .idx(idx)
                                .role(role)
                                .build();

                        CustomUserDetails customUserDetails = new CustomUserDetails(member);

                        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authToken);


                    } catch (AccessDeniedException e) {
                        e.printStackTrace();

                        throw new AccessDeniedException("");

                    }
                }
            }
        }
        //유효하지 않은 토큰에 대한 예외 처리가 끝난 후, 메시지를 그대로 반환합니다. 유효한 토큰이면 메시지를 정상적으로 처리합니다.
        return message;
    }

    // 쿠키에서 토큰을 추출하는 메서드
    private String extractTokenFromCookies(String cookies) {
        // "atoken="으로 시작하는 쿠키를 찾아서 반환
        for (String cookie : cookies.split(";")) {
            if (cookie.trim().startsWith("atoken=")) {
                return cookie.split("=")[1].trim();
            }
        }
        return null;
    }
}