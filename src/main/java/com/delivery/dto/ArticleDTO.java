package com.delivery.dto;

import com.delivery.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleDTO {

    private Long id;  //  게시글 id
    private User user; // 아이디(학번)
    private String title;    //  제목
    private String content;  //  본문
    private Timestamp createdAt; // 작성일
}
