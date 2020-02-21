package com.padoling.portfolio.august.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long>, PostsRepositoryCustom{
    Optional<Posts> findById(Long id);
}
