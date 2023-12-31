package com.delivery.controller;

import com.delivery.domain.User;
import com.delivery.domain.UserRole;
import com.delivery.dto.UserDTO;
import com.delivery.repository.UserRepository;
import com.delivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * 메인 페이지 /home
 * 회원가입 페이지 /signup
 * 로그인 페이지 /login
 * 로그아웃 페이지 /logout
 * 내 정보 페이지 /user/{userId}/profile
 * 정보 수정 페이지 /user/{userId}/update
 * 회원 탈퇴 /user/{userId}/delete
 */

@Controller // 해당 클래스가 컨트롤러임을 알리고 bean으로 등록하기 위함
@RequiredArgsConstructor // 의존관계 관련해 필요한 어노테이션
public class UserController {
    @Autowired private UserService userService; // 사용자 서비스 (데이터베이스에서 사용자 정보를 가져옴)
    @Autowired private UserRepository userRepository;   //  데이터베이스에 저장된 아이디 가져옴

    // 메인 페이지
    @GetMapping("/")
    public String Home(HttpSession session) {
        // 세션에서 로그인 상태를 확인
        boolean isLoggedIn =
                session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn");

        if (isLoggedIn) {
            // 세션에서 로그인한 사용자 정보를 가져오기
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            // 사용자의 역할(role)을 확인 (예: "admin" 역할이 관리자를 나타내는 역할)
            UserRole role = loggedInUser.getRole();

            if (UserRole.ADMIN.equals(role)) {
                // 관리자 역할인 경우 관리자 페이지로 이동
                return "admin/home";
            }
        }
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
    public String registerUser(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/signup";
        }

        try {
            userService.registerUser(userDTO);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("이미 존재하는 아이디입니다..")) {
                model.addAttribute("IdError", "이미 존재하는 아이디입니다.");
            } else if (e.getMessage().equals("이미 존재하는 전화번호입니다..")) {
                model.addAttribute("PhoneError", "이미 존재하는 전화번호입니다.");
            }
            return "/signup"; // 다시 회원가입 페이지로 리다이렉트하고 에러 메시지를 표시합니다.
        }
    }


    // 로그인 페이지
    @GetMapping("/login")
    public String dispLogin(){
        return "/login";
    }


    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("userId") String userId,
                        @RequestParam("password") String password, Model model, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user != null && user.getPassword().equals(password)) {
            // 로그인 성공
            session.setAttribute("isLoggedIn", true);
            session.setAttribute("loggedInUser", user); //  사용자 객체를 세션에 저장
            return "redirect:/"; // 로그인 후 이동할 페이지
        } else {
            // 로그인 실패
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "/login";
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 세션을 만료시킴
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/"; // 로그아웃 후 메인 페이지로 리다이렉트
    }

    // 내 정보 페이지
    @GetMapping("/user/{userId}/profile")
    public String userProfile(HttpSession session, Model model) {
        // 세션에서 로그인한 사용자 정보를 가져옴
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            // 사용자 정보를 모델에 추가하여 템플릿에서 사용
            model.addAttribute("user", loggedInUser);
            return "/user/profile"; // 내 정보를 표시하는 페이지로 이동
        } else {
            // 로그인하지 않은 경우 로그인 페이지로 이동
            return "redirect:/login";
        }
    }

    // 내 정보 수정 처리
    @PostMapping("/user/{userId}/update")
    public String updateUserProfile(@ModelAttribute UserDTO userDTO, HttpSession session, Model model) {
        try {
            // 세션에서 현재 로그인한 사용자 정보 가져오기
            User loggedInUser = (User) session.getAttribute("loggedInUser");

            if (loggedInUser != null) {
                // 사용자 ID 가져오기
                String userId = loggedInUser.getUserId();

                // UserService를 사용하여 사용자 정보 업데이트
                userService.updateUser(userId, userDTO);

                // 업데이트 후 세션 정보 업데이트
                loggedInUser.setPassword(userDTO.getPassword());
                loggedInUser.setDepartment(userDTO.getDepartment());
                loggedInUser.setAddress(userDTO.getAddress());
                loggedInUser.setDetailsAddress(userDTO.getDetailsAddress());

                // 정보 수정 성공하면 메인 페이지로 리디렉션
                return "redirect:/";
            } else {
                // 로그인하지 않은 경우 로그인 페이지로 이동
                return "redirect:/login";
            }
        } catch (IllegalArgumentException e) {
            // 예외 처리: 유효성 검사 실패 등
            // 에러 메시지를 모델에 추가하여 템플릿에서 사용 가능
            model.addAttribute("error", e.getMessage());
            return "/user/profile"; // 에러 처리 방법에 따라 수정
        }
    }

    // 회원 탈퇴 페이지
    @GetMapping("/user/{userId}/delete")
    public String deleteUserPage(HttpSession session, Model model) {
        // 사용자가 로그인했는지 확인
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // 사용자 삭제 확인 페이지를 표시합니다.
            model.addAttribute("user", loggedInUser);
            return "user/delete";
        } else {
            // 로그인되지 않은 경우 로그인 페이지로 리디렉션합니다.
            return "redirect:/login";
        }
    }

    // 회원 탈퇴 처리
    @PostMapping("/user/{userId}/delete")
    public String deleteUser(HttpSession session, Model model) {
        // 사용자가 로그인했는지 확인
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            try {
                // UserService를 사용하여 사용자를 삭제합니다.
                userService.deleteUser(loggedInUser.getUserId());

                // 삭제 후 세션을 만료시키고 홈 페이지로 리디렉션합니다.
                session.invalidate();
                return "redirect:/";
            } catch (IllegalArgumentException e) {
                // 예외 처리: 사용자를 찾지 못하는 경우
                model.addAttribute("error", e.getMessage());
                return "/error"; // 에러 처리 방법에 따라 수정
            }
        } else {
            // 로그인되지 않은 경우 로그인 페이지로 리디렉션합니다.
            return "redirect:/login";
        }
    }
}
