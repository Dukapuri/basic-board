package com.mumy.basicboard.service;

import com.mumy.basicboard.domain.Article;
import com.mumy.basicboard.domain.ArticleComment;
import com.mumy.basicboard.domain.UserAccount;
import com.mumy.basicboard.dto.ArticleCommentDto;
import com.mumy.basicboard.repository.ArticleCommentRepository;
import com.mumy.basicboard.repository.ArticleRepository;
import com.mumy.basicboard.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ArticleCommentService {

    private final UserAccountRepository userAccountRepository;
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {

        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.articleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId());
            articleCommentRepository.save(dto.toEntity(article, userAccount));
        } catch (EntityNotFoundException e) {
            log.warn("댓글 저장 실패. 댓글 작성에 필요한 정보를 찾을 수 없습니다. - dto:{}", e.getLocalizedMessage());
        }
    }

    public void updateArticleComment(ArticleCommentDto dto) {
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.articleId());
            if(dto.content() != null) {
                articleComment.setContent(dto.content());
            }
        } catch (EntityNotFoundException e) {
            log.warn("댓글 업데이트 실패. 댓글을 찾을 수 없습니다 - dto = {}", dto);
        }
    }
    public void deleteArticleComment(long articleCommentId) {
        articleCommentRepository.deleteById(articleCommentId);
    }
}
