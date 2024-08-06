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
public class StompHandler implements ChannelInterceptor { //TODO 0718추가

    private final JwtUtil jwtUtil;

    @Override
    //preSend 메서드는 ChannelInterceptor 인터페이스의 메서드로, 메시지가 실제로 전송되기 전에 호출됩니다.
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //STOMP 메시지의 헤더 정보를 쉽게 다룰 수 있도록 StompHeaderAccessor 객체로 래핑합니다.
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        //메시지의 STOMP 명령이 CONNECT 인지 확인합니다. 클라이언트가 서버와의 연결을 설정하려고 할 때 사용되는 명령입니다.
        if(accessor.getCommand() == StompCommand.CONNECT) {
            //쿠키에서 토큰 찾아서 저장
            //String token = fkdjgnfkdjgnkfjgnkdfjngfkjgfkdngdfg
            //STOMP 메시지의 네이티브 헤더에서 "token"이라는 이름의 첫 번째 값을 가져와, 이 토큰의 유효성을 검증합니다. 유효하지 않으면 true를 반환합니다.
            if(!jwtUtil.isExpired(accessor.getFirstNativeHeader("tokk")))
                //토큰이 유효하지 않은 경우 AccessDeniedException 예외를 발생시키고, 이를 잡아서 스택 트레이스를 출력합니다.
                try {

                   String token =  accessor.getFirstNativeHeader("tokk");
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
        //유효하지 않은 토큰에 대한 예외 처리가 끝난 후, 메시지를 그대로 반환합니다. 유효한 토큰이면 메시지를 정상적으로 처리합니다.
        return message;
    }
}
