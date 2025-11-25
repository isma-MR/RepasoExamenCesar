package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.BookJpaEntity;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.PublisherJpaEntity;

public class PublisherJpaDaoImpl implements PublisherJpaDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PublisherJpaEntity> findPublisherById(Long id) {
        PublisherJpaEntity publisherJpaEntity = entityManager.find(PublisherJpaEntity.class, id);
        return Optional.ofNullable(publisherJpaEntity);
    }

    @Override
    public Optional<PublisherJpaEntity> findPublisherBySlug(String slug) {
        try {
            PublisherJpaEntity publisherJpaEntity = entityManager
                    .createQuery("SELECT p FROM PublisherJpaEntity p WHERE p.slug = :slug", PublisherJpaEntity.class)
                    .setParameter("slug", slug)
                    .getSingleResult();
            return Optional.of(publisherJpaEntity);
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<PublisherJpaEntity> findAll() {
        return entityManager.createQuery("SELECT p FROM PublisherJpaEntity p ORDER BY p.id", PublisherJpaEntity.class)
                .getResultList();
    }

    @Override
    public PublisherJpaEntity insert(PublisherJpaEntity publisherDto) {
        entityManager.persist(publisherDto);
        return publisherDto;
    }

    @Override
    public PublisherJpaEntity update(PublisherJpaEntity publisherDto) {
        entityManager.flush();
        entityManager.merge(publisherDto);
        return publisherDto;
    }

    @Override
    public void delete(Long id) {
        PublisherJpaEntity publisherJpaEntity = entityManager.find(PublisherJpaEntity.class, id);
        BookJpaEntity book = entityManager
                .createQuery("SELECT b FROM BookJpaEntity b WHERE b.publisher.id = :id", BookJpaEntity.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.remove(book);
        entityManager.remove(publisherJpaEntity);
    }

}