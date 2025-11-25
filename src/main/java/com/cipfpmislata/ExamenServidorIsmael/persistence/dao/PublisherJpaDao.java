package com.cipfpmislata.ExamenServidorIsmael.persistence.dao;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.PublisherJpaEntity;

import java.util.List;
import java.util.Optional;

public interface PublisherJpaDao {
    Optional<PublisherJpaEntity> findPublisherById(Long id);

    Optional<PublisherJpaEntity> findPublisherBySlug(String slug);

    List<PublisherJpaEntity> findAll();

    PublisherJpaEntity insert(PublisherJpaEntity publisherDto);

    PublisherJpaEntity update(PublisherJpaEntity publisherDto);

    void delete(Long id);
}
