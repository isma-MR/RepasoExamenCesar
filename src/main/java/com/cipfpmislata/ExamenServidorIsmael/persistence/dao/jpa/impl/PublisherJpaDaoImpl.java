package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
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
        PublisherJpaEntity publisherJpaEntity = entityManager.find(PublisherJpaEntity.class, slug);
        return Optional.ofNullable(publisherJpaEntity);
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
        entityManager.remove(publisherJpaEntity);
    }

}