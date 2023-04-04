package com.mumy.basicboard.dto.request;

import com.mumy.basicboard.dto.ArticleCommentDto;
import com.mumy.basicboard.dto.UserAccountDto;

public record ArticleCommentRequest(Long articleId, String content) {

    public static ArticleCommentRequest of(Long articleId, String content) {
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto) {
        return ArticleCommentDto.of(
            articleId,
            userAccountDto,
            content
        );
    }
}
