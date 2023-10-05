package com.delivery.controller;

import com.delivery.dto.UserDTO;
import com.delivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/*
 * 메인 페이지 /home
 * 로그인 페이지 /login
 * 회원가입 페이지 /signup
 * 관리자 페이지 /admin
 * */

@Controller // 해당 클래스가 컨트롤러임을 알리고 bean으로 등록하기 위함
@RequiredArgsConstructor // 의존관계 관련해 필요한 어노테이션
public class UserController {
    @Autowired
    private UserService userService; // 사용자 서비스 (데이터베이스에서 사용자 정보를 가져옴)

    // 메인 페이지
    @GetMapping("/")
    public String Home(){
        return "/home";
    }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String dispSignUp(Model model){
        model.addAttribute("userDTO", new UserDTO());

        return "/signup";
    }


    // 회원가입 처리
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("userDTO") UserDTO userDTO, Model model) {
        try {
            userService.registerUser(userDTO);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            // 이미 존재하는 userId 예외 처리
            model.addAttribute("error", "이미 존재하는 아이디입니다..");
            return "/signup"; // 다시 회원가입 페이지로 리다이렉트하고 에러 메시지를 표시합니다.
        }
    }


    // 로그인 페이지
    @GetMapping("/login")
    public String dispLogin(){
        return "/login";
    }

    @PostMapping("/login")
    public String login() {
        // 사용자 로그인 로직을 구현
        // 스프링 시큐리티를 사용하여 로그인 처리
        return "redirect:/"; // 로그인 성공 시 홈 페이지로 리디렉션
    }

    /*
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
    */

}