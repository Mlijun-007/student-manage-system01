package com.example.system_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // 创建内存用户存储（实际项目中应该使用数据库存储）
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 配置授权规则
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // 公开访问的URL
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/api/**" // 开发环境中API接口公开访问
                                ).permitAll()
                                // 其他请求都需要认证
                                .anyRequest().authenticated()
                )
                // 配置表单登录（默认）
                .formLogin(formLogin ->
                        formLogin
                                .permitAll()
                )
                // 配置基本认证
                .httpBasic(httpBasic -> {})
                // 配置CSRF
                .csrf(csrf ->
                        csrf.disable() // 开发环境中禁用CSRF保护，生产环境中应该启用
                )
                // 配置安全头
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.deny()) // 禁用iframe，提高安全性
                );

        return http.build();
    }
}
