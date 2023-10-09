package com.delivery.controller.Article;

import com.delivery.domain.Article;
import com.delivery.dto.ArticleDTO;
import com.delivery.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;

    // 게시판 페이지
    @GetMapping("/articles")
    public String Articles(){
        return "/user/articles";
    }


    // 게시글 페이지
    @GetMapping("/article")
    public String article(){

        return "/user/article";
    }

    // 게시글 생성
    @PostMapping("/create")
    public ResponseEntity<String> createArticle(@RequestBody ArticleDTO articleDTO) {
        // DTO를 엔티티로 변환하여 저장
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        Article savedArticle = articleRepository.save(article);
        return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다. ID: " + savedArticle.getId());
    }
}
