package com.example.mojal2ndproject2.config;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.logging.Logger;

public class CustomHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = Logger.getLogger(CustomHandshakeInterceptor.class.getName());

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        // HTTP 요청에서 쿠키를 추출
        String cookies = request.getHeaders().getFirst("Cookie");
//        logger.info("Before Handshake: " + request.getURI());
        if (cookies != null) {
//            logger.info("Cookies found: " + cookies);
            attributes.put("cookies", cookies);
        } else {
//            logger.info("No cookies found.");
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
        logger.info("After Handshake: " + request.getURI());

    }
}

