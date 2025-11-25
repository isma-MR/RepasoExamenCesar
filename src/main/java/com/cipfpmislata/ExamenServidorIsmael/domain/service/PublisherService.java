package com.cipfpmislata.ExamenServidorIsmael.domain.service;

import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;

import java.util.List;
import java.util.Optional;

public interface PublisherService {
    List<PublisherDto> findAll(int page, int size);
    Optional<PublisherDto> findBySlug(String slug);
    PublisherDto create(PublisherDto publisherDto);
    PublisherDto update(PublisherDto publisherDto);
    void delete(String slug);
}
