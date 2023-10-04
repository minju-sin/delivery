package com.delivery.config;

import com.delivery.domain.User;
import com.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;


// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/admin/**").hasRole("ADMIN")  //  /admin/** 패턴에 대해 ADMIN 권한 필요
                        .requestMatchers("/user/**").hasRole("USER")  //  /user/** 패턴에 대해 USER 권한 필요
                        .requestMatchers("/login").permitAll() // 누구나 접근 허용
                        .requestMatchers("/signup").permitAll() // 회원가입 페이지에 대한 접근 허용
                        .requestMatchers("/").permitAll()
                )
                .formLogin(formLogin -> {
                    formLogin.loginPage("/login") // 커스텀 로그인 페이지 URL 지정
                            .defaultSuccessUrl("/") // 로그인 성공 후 이동할 경로
                            .permitAll();
                })
                .logout(withDefaults())    // 기본 로그아웃 설정 사용
                .csrf().disable();         // CSRF 비활성화 (테스트 목적으로)

        return http.build();
    }

    // 비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}