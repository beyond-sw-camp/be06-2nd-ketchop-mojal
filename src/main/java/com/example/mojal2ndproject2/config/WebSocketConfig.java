package com.example.mojal2ndproject2.config;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final StompHandler stompHandler;

    //메세지 브로커 관련된 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        //1.메세지 받을 때 관련 경로 설정, 스프링의 내장브로커 사용
        //2.“/queue”, “/topic”을 통해 1:1, 1:N 설정을 해줌
        //3.“/queue”, “/topic”가 api에 prefix로 붙은 경우, messageBroker가 해당 경로를 가로챔
        config.setApplicationDestinationPrefixes("/app");
        //1.메세지 보낼 때 관련 경로 설정
        //2.클라이언트가 메세지를 보낼 떄, 경로 앞에 “/app”이 붙어있으면 Broker로 보내짐
    }

    //소켓연결과 관련된 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") //소켓연결 uri
//                .setAllowedOrigins("http://127.0.0.1:5500") //소켓 CORS 설정
//                .setAllowedOriginPatterns("http://127.0.0.1:*") //모든 출처 허용, setAllowedOrigins는 먹히지 않는다?
                .setAllowedOrigins("http://localhost:5500") //0805추가
                .setAllowedOrigins("http://localhost:5501") //0805추가
                .setAllowedOriginPatterns("*") //0805추가
                .addInterceptors(new CustomHandshakeInterceptor()) //0805추가
                .withSockJS(); //소켓 지원하지 않으면 sockJS 사용하도록 하는 설정
    }

    //인터셉터 추가
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}
