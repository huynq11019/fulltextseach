package com.example.hibernatesearch.inftrastructure.persistence.repository.impl;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Post;
import com.example.hibernatesearch.inftrastructure.persistence.repository.custom.PostRepositoryCustom;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Post> search(String keywords) {
//        final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//
//        // Search query builder
//        final QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
//                .forEntity(Post.class).get();
//
//        // Use a boolean junction and then add queries to it
//        final BooleanJunction<BooleanJunction> outer = queryBuilder.bool();
//        outer.must(queryBuilder.keyword()
//                .onFields("content", "title").matching(keywords).createQuery());
//
//        @SuppressWarnings("unchecked")
//        List<Post> resultList = fullTextEntityManager.createFullTextQuery(outer.createQuery(), Post.class)
//                .getResultList();
//        return resultList;
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Post.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields("title", "content")
                .matching(keywords)
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, Post.class);

        return jpaQuery.getResultList();
    }
}
