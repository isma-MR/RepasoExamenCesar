package com.cipfpmislata.ExamenServidorIsmael.domain.service;

import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    List<PublisherDto> findAll();

    Optional<PublisherDto> findById(Long id);

    Optional<PublisherDto> findBySlug(String slug);

    PublisherDto create(PublisherDto publisherDto);

    PublisherDto update(PublisherDto publisherDto);

    void delete(Long id);
}
