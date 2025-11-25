package com.cipfpmislata.ExamenServidorIsmael.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.BookJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.AuthorJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.PublisherJpaDaoImpl;

import jakarta.persistence.EntityManager;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.BookJpaDaoImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.AuthorJpaDaoImpl;

@TestConfiguration
@EnableJpaRepositories(basePackages = "com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa")
@EntityScan(basePackages = "com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity")
public class TestConfig {

    @Bean
    public PublisherJpaDao publisherJpaDao(EntityManager entityManager) {
        return new PublisherJpaDaoImpl();
    }

    @Bean
    public BookJpaDao bookJpaDao(EntityManager entityManager) {
        return new BookJpaDaoImpl();
    }

    @Bean
    public AuthorJpaDao authorJpaDao(EntityManager entityManager) {
        return new AuthorJpaDaoImpl();
    }

}
