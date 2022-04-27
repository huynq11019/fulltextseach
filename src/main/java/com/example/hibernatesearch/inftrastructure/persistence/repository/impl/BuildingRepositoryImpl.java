package com.example.hibernatesearch.inftrastructure.persistence.repository.impl;

import com.example.hibernatesearch.inftrastructure.persistence.entity.Building;
import com.example.hibernatesearch.inftrastructure.persistence.repository.custom.BuildingRepositoryCustom;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Building> search(String keywords) {
//        final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        // Search query builder
//        final QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
//                .forEntity(Building.class).get();
//
//        // Use a boolean junction and then add queries to it
//        final BooleanJunction<BooleanJunction> outer = queryBuilder.bool();
//        outer.must(queryBuilder.keyword()
//                .onFields("name", "note","address","code")
//                .matching(keywords).
//                createQuery());
//
//        @SuppressWarnings("unchecked")
//        List<Building> resultList = fullTextEntityManager.createFullTextQuery(outer.createQuery(), Building.class)
//                .getResultList();
//        return resultList;
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Building.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .onFields("name", "address", "code", "note")
                .matching(keywords)
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(query, Building.class);
        jpaQuery.setMaxResults(10);
        jpaQuery.setFirstResult(0);
        return jpaQuery.getResultList();
    }

    @Override
    @Transactional
    public List<Building> fuzztSearch(String keywords) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Building.class)
                .get();

        org.apache.lucene.search.Query fuzzyQuery = queryBuilder
                .keyword()
                .fuzzy()
                .withEditDistanceUpTo(2)
                .withPrefixLength(0)
                .onFields("name", "address", "code", "note")
                .matching(keywords)
                .createQuery();
        org.hibernate.search.jpa.FullTextQuery jpaQuery
                = fullTextEntityManager.createFullTextQuery(fuzzyQuery, Building.class);
        jpaQuery.setMaxResults(10);
        jpaQuery.setFirstResult(0);

        return jpaQuery.getResultList();
    }

    @Override
    public List<Building> searchCombined(String keywords, int page, int size) {
        return null;
    }


}
