package com.example.hibernatesearch.application.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.CacheMode;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.Instant;

@Slf4j
@Component
@Transactional
public class BuildSearchIndex implements ApplicationListener<ApplicationReadyEvent> {

    @PersistenceContext
    private EntityManager entityManager;
    private static final Integer DEFAULT_BATCH_INDEX_SIZE = 25;
    private FullTextEntityManager fullTextEntityManager;

    private Instant lastIndexedTime = Instant.now();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        reindexAll(25);
    }

    @Async
    public void reindexAll(int batchSize) {
        log.info("start create index" + Instant.now());
        if (batchSize <= 0) {
            batchSize = DEFAULT_BATCH_INDEX_SIZE;
        }
        try {
            fullTextEntityManager.createIndexer()
                    .batchSizeToLoadObjects(batchSize)
                    .threadsToLoadObjects(5)
                    .cacheMode(CacheMode.NORMAL)
                    .startAndWait();
            this.lastIndexedTime = Instant.now();
            log.info("Successfully created search index");
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.warn("Failed to create search index: {}", e.toString());
        }
    }
}
