package com.cipfpmislata.ExamenServidorIsmael.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.PublisherJpaDaoImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.BookJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.BookJpaDaoImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.AuthorJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.AuthorJpaDaoImpl;

@Configuration
@EnableJpaRepositories(basePackages = "com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa")
public class PersistenceConfig {

    @Bean
    public PublisherJpaDao publisherJpaDao() {
        return new PublisherJpaDaoImpl();
    }

    @Bean
    public BookJpaDao bookJpaDao() {
        return new BookJpaDaoImpl();
    }

    @Bean
    public AuthorJpaDao authorJpaDao() {
        return new AuthorJpaDaoImpl();
    }

}
