package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.BookJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.BookJpaEntity;

public class BookJpaDaoImpl implements BookJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<BookJpaEntity> findBookById(Long id) {
        BookJpaEntity bookJpaEntity = entityManager.find(BookJpaEntity.class, id);
        return Optional.ofNullable(bookJpaEntity);
    }

    @Override
    public Optional<BookJpaEntity> findBookByIsbn(String isbn) {
        String sql = "SELECT b FROM BookJpaEntity b WHERE b.isbn = :isbn";
        try {
            BookJpaEntity bookJpaEntity = entityManager.createQuery(sql, BookJpaEntity.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
            return Optional.of(bookJpaEntity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<BookJpaEntity> findAll(int page, int size) {
        String sql = "SELECT b FROM BookJpaEntity b ORDER BY b.id";
        TypedQuery<BookJpaEntity> bookJpaEntityPage = entityManager.createQuery(sql, BookJpaEntity.class);
        bookJpaEntityPage.setFirstResult(page * size);
        bookJpaEntityPage.setMaxResults(size);
        return bookJpaEntityPage.getResultList();
    }

    @Override
    public BookJpaEntity insert(BookJpaEntity bookJpaEntity) {
        entityManager.persist(bookJpaEntity);
        return bookJpaEntity;
    }

    @Override
    public BookJpaEntity update(BookJpaEntity bookJpaEntity) {

        entityManager.flush();
        entityManager.merge(bookJpaEntity);
        return bookJpaEntity;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        BookJpaEntity bookJpaEntity = entityManager.find(BookJpaEntity.class, isbn);
        entityManager.remove(bookJpaEntity);
    }

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(b) FROM BookJpaEntity b", Long.class).getSingleResult();
    }

}