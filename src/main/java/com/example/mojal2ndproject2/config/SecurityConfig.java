package com.example.mojal2ndproject2.config;

import com.example.mojal2ndproject2.config.filter.JwtFilter;
import com.example.mojal2ndproject2.config.filter.LoginFilter;
import com.example.mojal2ndproject2.config.jwt.JwtUtil;
import com.example.mojal2ndproject2.member.oauth.OAuth2AuthenticationSuccessHandler;
import com.example.mojal2ndproject2.member.oauth.OAuth2Service;
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

import java.util.Arrays;
import java.util.Collections;

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

//        http.cors((cors) -> cors
//                .configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOriginPatterns(Arrays.asList("*"));
//                    configuration.setAllowedMethods(Collections.singletonList("*"));
//                    configuration.setAllowCredentials(true);
//                    configuration.setAllowedHeaders(Collections.singletonList("*"));
//                    configuration.setExposedHeaders(Collections.singletonList("Authorization"));
//                    return configuration;
//                })); //0807추가

        http.authorizeHttpRequests((auth)->
                auth
                        .requestMatchers(
                                "/share/create",
                                "/share/join",
                                "/share/my/list",
                                "/share/joined/list",
                                "/exchange/create",
                                "/exchange/my/list",
                                "/exchange/joined/list",
                                "/matching/check/exchange",
                                "/member/add/category").authenticated()
                        .requestMatchers("/api/ws","/api/topic").permitAll()
//                        .requestMatchers("/ws/**").permitAll() //0807추가
                        .requestMatchers(
                                "/login",
                                "/member/signup",
                                "/email/**",
                                "/post/**",
                                "/search/**",
                                "/kakao/**").permitAll()
                        .anyRequest().permitAll()
        );

        JwtFilter jwtFilter = new JwtFilter(jwtUtil);
        http.addFilterBefore(jwtFilter, LoginFilter.class);

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/member/login");
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


//        config.addAllowedOrigin("http://127.0.0.1:5500"); //채팅 허용
//        config.addAllowedOrigin("http://127.0.0.1:5501"); //채팅 허용
//        config.addAllowedOriginPattern("http://127.0.0.1:*"); //config.setAllowCredentials(true) 이게 트루면 그냥 addAllowedOrigin로는 먹히지 않는다?
        config.addAllowedOrigin("http://localhost:5500"); //0805추가
        config.addAllowedOrigin("http://localhost:5501"); //0805추가
        config.addAllowedOriginPattern("*"); //0805추가
        

        config.addAllowedOrigin("http://localhost");
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