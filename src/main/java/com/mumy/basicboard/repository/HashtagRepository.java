package com.mumy.basicboard.repository;

import com.mumy.basicboard.domain.Hashtag;
import com.mumy.basicboard.repository.querydsl.HashtagRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RepositoryRestResource
public interface HashtagRepository extends
        JpaRepository<Hashtag, Long>,
        HashtagRepositoryCustom,
        QuerydslPredicateExecutor<Hashtag> {
    Optional<Hashtag> findByHashtagName(String hashtagName);
    List<Hashtag> findByHashtagNameIn(Set<String> hashtagNames);
}
