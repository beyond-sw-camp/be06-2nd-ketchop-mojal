package com.example.mojal2ndproject2.config;

import com.example.mojal2ndproject2.filter.JwtFilter;
import com.example.mojal2ndproject2.filter.LoginFilter;
import com.example.mojal2ndproject2.jwt.JwtUtil;
import com.example.mojal2ndproject2.oauth.OAuth2AuthenticationSuccessHandler;
import com.example.mojal2ndproject2.oauth.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2Service oAuth2Service;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((auth)-> auth.disable());
        http.httpBasic((auth)->auth.disable());
        http.formLogin((auth)->auth.disable());

        http.authorizeHttpRequests((auth)->
                auth
                        .requestMatchers(
                                "/login","/member/signup","/email/**","/post/read","/post/list", "/search/**","/kakao/**","/shareposts/**").permitAll()
                        .anyRequest().authenticated()
        );

        JwtFilter jwtFilter = new JwtFilter(jwtUtil);
        http.addFilterBefore(jwtFilter, LoginFilter.class);

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        http.addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement((session) -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            session.sessionFixation().none();
        });
        http.oauth2Login((config) -> {
            config.successHandler(oAuth2AuthenticationSuccessHandler);
            config.userInfoEndpoint((endpoint) -> endpoint.userService(oAuth2Service));
        });

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() { //cors error 방지
        CorsConfiguration config = new CorsConfiguration();

        config.addAllowedOrigin("http://localhost:3000"); // 허용할 출처
        config.addAllowedOrigin("http://localhost:8080"); // 허용할 출처
        config.addAllowedMethod("*"); // 허용할 메서드 (GET, POST, PUT 등)
        config.addAllowedHeader("*"); // 허용할 헤더
        config.setAllowCredentials(true); // 자격 증명 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
