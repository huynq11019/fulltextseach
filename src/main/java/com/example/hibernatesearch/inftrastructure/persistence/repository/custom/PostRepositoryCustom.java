package com.example.hibernatesearch.inftrastructure.persistence.repository.custom;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> search(final String keywords);
}
