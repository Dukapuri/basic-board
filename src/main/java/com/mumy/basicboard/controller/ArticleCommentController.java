package com.mumy.basicboard.controller;

import com.mumy.basicboard.dto.request.ArticleCommentRequest;
import com.mumy.basicboard.dto.security.BoardPrincipal;
import com.mumy.basicboard.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {

    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest,
                                        @AuthenticationPrincipal BoardPrincipal boardPrincipal) {


        articleCommentService.saveArticleComment(articleCommentRequest.toDto(boardPrincipal.toDto()));

        return "redirect:/articles/" + articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId,
                                       @AuthenticationPrincipal BoardPrincipal boardPrincipal) {

        articleCommentService.deleteArticleComment(commentId, boardPrincipal.getUsername());
        return "redirect:/articles/" + articleId;

    }

}
