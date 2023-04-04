package com.mumy.basicboard.controller;

import com.mumy.basicboard.dto.UserAccountDto;
import com.mumy.basicboard.dto.request.ArticleCommentRequest;
import com.mumy.basicboard.dto.request.ArticleRequest;
import com.mumy.basicboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping ("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest) {

        // TODO: 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "sun","pw","sun@mail.com",null,null
        )));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId) {

        articleCommentService.deleteArticleComment(commentId);
        return "redirect:/articles/" + articleId;

    }

}
