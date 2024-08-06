package com.example.mojal2ndproject2.config;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // HTTP 요청에서 쿠키를 추출
        String cookies = request.getHeaders().getFirst("Cookie");
        if (cookies != null) {
            attributes.put("cookies", cookies);
        }

//        if (request instanceof org.springframework.http.server.ServletServerHttpRequest) {
//            HttpServletRequest servletRequest = ((org.springframework.http.server.ServletServerHttpRequest) request).getServletRequest();
//            String cookies = servletRequest.getHeader("Cookie");
//            if (cookies != null) {
//                attributes.put("cookies", cookies);
//            }
//        }

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               org.springframework.web.socket.WebSocketHandler wsHandler, Exception ex) {
    }
}

