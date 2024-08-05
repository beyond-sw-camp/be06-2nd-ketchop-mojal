package com.example.mojal2ndproject2.config.jwt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
public class TokenController { //이제 안씀

    @GetMapping("/token")
    public Map<String, String> getTokenFromCookie(HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("ATOKEN".equals(cookie.getName())) {
                    response.put("token", cookie.getValue());
                    return response;
                }
            }
        }
        response.put("token", null);
        return response;
    }
}

