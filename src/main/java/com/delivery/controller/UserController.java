package com.delivery.controller;

import com.delivery.domain.User;
import com.delivery.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // 해당 클래스가 컨트롤러임을 알리고 bean으로 등록하기 위함
@RequiredArgsConstructor // 의존관계 관련해 필요한 어노테이션
public class UserController {
    @Autowired
    private UserService userService;
    
    // 메인 페이지
    @GetMapping("/")
    public String Home(){
        return "/home";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String dispSignUp(Model model){
        model.addAttribute("user", new User());
        return "/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String execSignUp(@ModelAttribute("user") User user){
        userService.signupUser(user);
        return "redirect:/";
    }


    // 로그인 페이지
    @GetMapping("/login")
    public String disLogin(){
        return "/login";
    }

    // 접근 거부 페이지
    @GetMapping("/user/denied")
    public String Denied(){
        return "/denied";
    }

    // 내 정보 페이지
    @GetMapping("/user/info")
    public String MyInfo(){
        return "/myinfo";
    }

    // 관리자 페이지
    @GetMapping("/admin")
    public String Admin(){
        return "/admin";
    }
}
