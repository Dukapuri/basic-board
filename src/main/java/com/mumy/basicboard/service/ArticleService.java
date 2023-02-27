package com.mumy.basicboard.service;

import com.mumy.basicboard.domain.type.SearchType;
import com.mumy.basicboard.dto.ArticleDto;
import com.mumy.basicboard.dto.ArticleWithCommentsDto;
import com.mumy.basicboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType title, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long id) {
        return null;
    }

    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }
    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(ArticleDto dto) {

    }

    public void deleteArticle(long articleId) {
        
    }
}
