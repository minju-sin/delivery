package com.delivery.controller;

import com.delivery.domain.User;
import com.delivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String execSignUp(@ModelAttribute("user") User user, Model model) {
        if (userService.isUserIdUnique(user.getUserId())) {
            userService.signupUser(user);
            return "redirect:/"; // 가입 성공 시 홈 페이지로 리다이렉션
        } else {
            // 중복된 아이디일 경우 에러 메시지를 표시하고 회원 가입 페이지로 다시 이동
            model.addAttribute("userExistsMsg", "이미 사용 중인 아이디입니다.");
            return "signup";
        }
    }


    // 로그인 페이지
    @GetMapping("/login")
    public String disLogin(){
        return "/login";
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
