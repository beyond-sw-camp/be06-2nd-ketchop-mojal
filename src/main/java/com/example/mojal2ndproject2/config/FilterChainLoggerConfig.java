package com.example.mojal2ndproject2.config;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class FilterChainLoggerConfig {

    @Autowired
    private FilterChainProxy filterChainProxy;

    @PostConstruct
    public void printSecurityFilters() {
        List<SecurityFilterChain> filterChains = filterChainProxy.getFilterChains();
        for (SecurityFilterChain chain : filterChains) {
            System.out.println("Security Filter Chain: " + chain);
            chain.getFilters().forEach(filter -> {
                System.out.println("Filter: " + filter.getClass().getName());
            });
        }
    }
}
