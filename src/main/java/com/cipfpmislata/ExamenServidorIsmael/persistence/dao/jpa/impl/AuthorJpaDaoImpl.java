package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.AuthorJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.AuthorJpaEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class AuthorJpaDaoImpl implements AuthorJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<AuthorJpaEntity> findAuthorById(Long id) {
        AuthorJpaEntity authorJpaEntity = entityManager.find(AuthorJpaEntity.class, id);
        return Optional.ofNullable(authorJpaEntity);
    }

    @Override
    public Optional<AuthorJpaEntity> findAuthorBySlug(String slug) {
        return Optional.ofNullable(
                entityManager.createQuery("SELECT a FROM AuthorJpaEntity a WHERE a.slug = :slug", AuthorJpaEntity.class)
                        .setParameter("slug", slug)
                        .getSingleResult());
    }

    @Override
    public List<AuthorJpaEntity> findAll() {
        return entityManager.createQuery("SELECT a FROM AuthorJpaEntity a ORDER BY a.id", AuthorJpaEntity.class)
                .getResultList();
    }

    @Override
    public AuthorJpaEntity insert(AuthorJpaEntity authorDto) {
        entityManager.persist(authorDto);
        return authorDto;
    }

    @Override
    public AuthorJpaEntity update(AuthorJpaEntity authorDto) {
        entityManager.flush();
        entityManager.merge(authorDto);
        return authorDto;
    }

    @Override
    public void delete(Long id) {
        AuthorJpaEntity authorJpaEntity = entityManager.find(AuthorJpaEntity.class, id);
        entityManager.createQuery("DELETE FROM BookAuthorJpaEntity ba WHERE ba.author.id = :id")
                .setParameter("id", id)
                .executeUpdate();
        entityManager.remove(authorJpaEntity);
    }

}
