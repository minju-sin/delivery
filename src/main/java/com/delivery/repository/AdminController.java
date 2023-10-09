package com.delivery.repository;

import com.delivery.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository; // UserRepository가 데이터베이스와 상호작용하는 것으로 가정합니다.

    @GetMapping("/admin/management")
    public String showUserManagementPage(Model model) {
        List<User> userList = userRepository.findAll(); // 데이터베이스에서 사용자 데이터 가져오기
        model.addAttribute("userList", userList); // 사용자 데이터를 HTML 템플릿으로 전달
        return "/admin/management";
    }
}
