package io.security.spring_security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SecurityController{
    @GetMapping("/")
    public String index(HttpSession httpSession){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecurityContext context = (SecurityContext)httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication1 = context.getAuthentication();


        return "home";
    }

    @GetMapping("/thread")
    public String thread(){
        // 부모 스레드와 자식 스레드간 securityContext를 공유하지 않는다.
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    }
                }
        ).start();
        return "thread";
    }



}
