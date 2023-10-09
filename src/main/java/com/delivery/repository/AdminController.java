package com.delivery.repository;

import com.delivery.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository; // UserRepository가 데이터베이스와 상호작용하는 것으로 가정합니다.

    // 사용자 관리 페이지
    @GetMapping("/admin/management")
    public String showUserManagementPage(Model model) {
        List<User> userList = userRepository.findAll(); // 데이터베이스에서 사용자 데이터 가져오기
        model.addAttribute("userList", userList); // 사용자 데이터를 HTML 템플릿으로 전달
        return "/admin/management";
    }

    // 사용자 관리 페이지 - 회원 정보 삭제
    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam("userId") String userId) {
        // 삭제할 아이디를 찾는다.
        User user = userRepository.findByUserId(userId);
        if (user != null) {
            userRepository.delete(user);    //  해당 사용자 삭제
        }
        return "redirect:/admin/management";  //  삭제 후 리다이렉트
    }

}
