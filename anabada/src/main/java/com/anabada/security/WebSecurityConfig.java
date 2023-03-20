package com.anabada.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security 설정
 */
@Configuration
public class WebSecurityConfig {
    @Autowired
    private DataSource dataSource;

    //설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
        .authorizeRequests()
        .antMatchers(
        		"/",
        		"/report",
        		"/review",
        		"/review_used",
          		"/inquiry",
        		"/mypage",
        		"/checkPw",
        		"/reportedValidation",
        		"/myinquirelist",
        		"/assets/**",
                "/img/**",
                "/css/**",
                "/js/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .formLogin()					
        .loginPage("/login")			// 변경 필요
        .loginProcessingUrl("/login").permitAll()
        .usernameParameter("user_email")
        .passwordParameter("user_pwd")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/").permitAll()
        .and()
        .cors()
        .and()
        .httpBasic();

        return http.build();
    }

    //인증을 위한 쿼리 변경 필요!!!
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        // 인증 (로그인)
        .usersByUsernameQuery(
        		"select user_email username, user_pwd password, enabled " +
                "from bbs_member " +
                "where memberid = ?")
        // 권한
        .authoritiesByUsernameQuery(
        		"select memberid username, rolename role_name " +
                "from bbs_member " +
                "where memberid = ?");
    }

    // 단방향 비밀번호 암호화 
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
