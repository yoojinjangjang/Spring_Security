package io.security.spring_security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration // 설정 클래스이기 떄문에
@EnableWebSecurity // 기본 선언 필요 --> WebSecurityConfiguration 설정 클래스를 임포트하여 실행시키는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter { //사용자 정의 보안기능 구현을 위해 상속을 받는다.


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated(); //요청에 대한 보안 검사가 실행되고 어떠한 요청에도 인증을 받도록 설정하였다.
                //위는 인가 정책이다.
        http
                .formLogin() //인증 정책은 기본적으로 formLogin방식으로 인증을 할 수 있도록 설정
                //.loginPage("/loginPage") //원하는 페이지를 로그인 화면으로 사용 --> 경로 ! controller에 등록해야한다.
                .defaultSuccessUrl("/") //인증 성공시 이동하는 페이지
                .failureUrl("/login") // 인증 실패시 이동하는 페이지
                .usernameParameter("userId")  // username 파라미터 설정
                .passwordParameter("passwd")
                .loginProcessingUrl("/login_proc") //폼 태그의 액션 url
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        System.out.println("authentication " + authentication.getName()); // 인증에 성공한 사용자 이름 출력
//                        response.sendRedirect("/"); //루트 페이지로 이동
//
//
//                    }
//                }) //성공시 호출할 핸들러 , AuthenticationSuccessHandler() --> 인증 성공시 인증한 결과를 닮은 객체 까지 파라미터로 넘어온다. 이러한 정보를 활용하여 구체적인 로직들을 구현한다.
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                        System.out.println("exception: " + exception.getMessage()); //예외에 메세지를 출력한다.
//                        response.sendRedirect("/login"); //로그인 페이지로 이동
//
//                    }
//                }) //인증예외에 객체를 파라미터로 전달한다.
                .permitAll() //  위의 경로로 접근하는 모든 url은 접근이 가능하도록 설정해주어야 한다.
        ;

        http.
                logout()
                .logoutUrl("/logout") //원칙적으로 post방식으로 수행한다.
                .logoutSuccessUrl("/login")
                .addLogoutHandler(new LogoutHandler() {
                    @Override
                    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        HttpSession session = request.getSession();
                        session.invalidate(); //세션을 무효화 시킴
                    }
                })
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        response.sendRedirect("/login");

                    }
                })
                .deleteCookies("remember-me") //remember-me 쿠키를 제거한다.
                ;

    }


}
