package io.security.spring_security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController{
    @GetMapping("/")
    public String index(){
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "loginPage";
    }


    @GetMapping("/user")
    public String userPage(){
        return "user can access";
    }

    @GetMapping("/admin/pay")
    public String payPage(){
        return "admin only can access";
    }

    @GetMapping("/admin/**")
    public String admin(){
        return "admin and sys can access";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/denied")
    public String denied(){
        return "Access is denied";
    }

}
