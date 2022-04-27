package com.example.hibernatesearch.inftrastructure.persistence.repository;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Post;
import com.example.hibernatesearch.inftrastructure.persistence.repository.custom.PostRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
    Post save(final Post tweet);
}
