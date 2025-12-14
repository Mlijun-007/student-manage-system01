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
                                        "/h2-console/**"
                                ).permitAll()
                                // 管理员角色可以访问所有API
                                .requestMatchers("/api/**").hasRole("ADMIN")
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
                // 配置CSRF（禁用H2控制台的CSRF保护）
                .csrf(csrf ->
                        csrf
                                .ignoringRequestMatchers("/h2-console/**")
                )
                // 允许H2控制台的iframe访问
                .headers(headers ->
                        headers.frameOptions(frameOptions -> frameOptions.disable())
                );

        return http.build();
    }
}
