package com.example.mojal2ndproject2.config.filter;

import com.example.mojal2ndproject2.config.jwt.JwtUtil;
import com.example.mojal2ndproject2.member.model.CustomUserDetails;
import com.example.mojal2ndproject2.member.model.dto.request.MemberLoginReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    public final AuthenticationManager authenticationManager;
    public final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        MemberLoginReq memberLoginReq;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            memberLoginReq = objectMapper.readValue(messageBody, MemberLoginReq.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String email = memberLoginReq.getEmail();
        String password = memberLoginReq.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password, null);

        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        String email = userDetails.getUsername();
        Long idx = userDetails.getIdx();
        String role = userDetails.getRole();

        String token = jwtUtil.createToken(email, idx, role);

        PrintWriter out = response.getWriter();
//        out.println("{\"isSuccess\": true, \"accessToken\":\""+token+"\"}");
//        response.addHeader("Authorization", "Bearer " + token);
        Cookie cookie = new Cookie("ATOKEN", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
//        cookie.setMaxAge(7*24*60*60);
        response.addCookie(cookie);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        System.out.println(failed.getCause());
        System.out.println(failed.getMessage());
        super.unsuccessfulAuthentication(request, response, failed);
        //Todo 에러 메세지 보내기 -> 응답을 보내는 필터를 찾아서 거기서 응답
        PrintWriter out = response.getWriter();
        out.println("{\"isSuccess\": false, \"message\":\"이메일 인증 후 다시 로그인을 시도해주세요.\"");
    }
}
