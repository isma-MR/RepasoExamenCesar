package com.cipfpmislata.ExamenServidorIsmael.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.cipfpmislata.ExamenServidorIsmael.domain.repository.AuthorRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.BookRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.PublisherRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.AuthorService;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.BookService;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.PublisherService;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.impl.AuthorServiceImpl;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.impl.BookServiceImpl;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.impl.PublisherServiceImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.AuthorJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.BookJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.AuthorJpaDaoImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.BookJpaDaoImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl.PublisherJpaDaoImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.repository.AuthorRepositoryImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.repository.BookRepositoryImpl;
import com.cipfpmislata.ExamenServidorIsmael.persistence.repository.PublisherRepositoryImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class SpringConfig {

    @Bean
    public BookRepository bookRepository(BookJpaDao bookJpaDao) {
        return new BookRepositoryImpl(bookJpaDao);
    }

    @Bean
    public BookService bookService(BookRepository bookRepository) {
        return new BookServiceImpl(bookRepository);
    }

    /************* PUBLISHER *************/

    @Bean
    public PublisherRepository publisherRepository(PublisherJpaDao publisherJpaDao) {
        return new PublisherRepositoryImpl(publisherJpaDao);
    }

    @Bean
    public PublisherService publisherService(PublisherRepository publisherRepository) {
        return new PublisherServiceImpl(publisherRepository);
    }

    /*************** AUTHOR **************/
    @Bean
    public AuthorRepository authorRepository(AuthorJpaDao authorJpaDao) {
        return new AuthorRepositoryImpl(authorJpaDao);
    }

    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorServiceImpl(authorRepository);
    }

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

    /* *************** ObjectMapper **************/
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

}