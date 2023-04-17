package com.mumy.basicboard.repository;

import com.mumy.basicboard.config.JpaConfig;
import com.mumy.basicboard.domain.Article;
import com.mumy.basicboard.domain.Hashtag;
import com.mumy.basicboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
@Import({JpaRepositoryTest.TestJpaConfig.class}) //JpaConfig는 내가 만든 환경설정 이므로 테스트 클래스는 인식을 못하기 때문에 Import를 사용해야함
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;
    private final HashtagRepository hashtagRepository;

    JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository,
            @Autowired HashtagRepository hashtagRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
        this.hashtagRepository = hashtagRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        List<Article> articles = articleRepository.findAll();

        assertThat(articles).isNotNull().hasSize(123);// classpath:resources/data.sql 참조
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {

        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("hwang", "pw", null, null, null));
        Article article = Article.of(userAccount,"new article", "new content");
        article.addHashtags(Set.of(Hashtag.of("spring")));

        articleRepository.save(article);

        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {

        Article article = articleRepository.findById(1L).orElseThrow();
        Hashtag updateHashtag = Hashtag.of("springboot");
        article.clearHashtags();
        article.addHashtags(Set.of(updateHashtag));

        Article savedArticle = articleRepository.saveAndFlush(article);

        assertThat(savedArticle.getHashtags()).hasSize(1).extracting("hashtagName", String.class)
                .containsExactly(updateHashtag.getHashtagName());
    }

    @DisplayName("[Querydsl] 전체 hashtag 리스트에서 이름만 조회하기")
    @Test
    void givenNothing_whenQueryingHashtags_thenReturnsHashtagNames() {
        // Given

        // When
        List<String> hashtagNames = hashtagRepository.findAllHashtagNames();

        // Then
        assertThat(hashtagNames).hasSize(19);
    }

    @DisplayName("[Querydsl] hashtag로 페이징된 게시글 검색하기")
    @Test
    void givenHashtagNamesAndPageable_whenQueryingArticles_thenReturnsArticlePage() {
        // Given
        List<String> hashtagNames = List.of("blue", "crimson", "fuscia");
        Pageable pageable = PageRequest.of(0, 5, Sort.by(
                Sort.Order.desc("hashtags.hashtagName"),
                Sort.Order.asc("title")
        ));

        // When
        Page<Article> articlePage = articleRepository.findByHashtagNames(hashtagNames, pageable);

        // Then
        assertThat(articlePage.getContent()).hasSize(pageable.getPageSize());
        assertThat(articlePage.getContent().get(0).getTitle()).isEqualTo("Fusce posuere felis sed lacus.");
        assertThat(articlePage.getContent().get(0).getHashtags())
                .extracting("hashtagName", String.class)
                .containsExactly("fuscia");
        assertThat(articlePage.getTotalElements()).isEqualTo(17);
        assertThat(articlePage.getTotalPages()).isEqualTo(4);
    }

    @Test
    @DisplayName("delete 테스트")
    void givenTestData_whendeleting_thenWorksFine() {

        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        articleRepository.delete(article);

        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
    @EnableJpaAuditing
    @TestConfiguration
    public class TestJpaConfig{
        @Bean
        AuditorAware<String> auditorAware() {
            return () -> Optional.of("sun");
        }
    }
}
