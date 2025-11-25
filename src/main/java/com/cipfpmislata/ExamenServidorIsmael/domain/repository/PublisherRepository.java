package com.cipfpmislata.ExamenServidorIsmael.domain.repository;

import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {
    List<PublisherDto> findAll(int page, int size);
    Optional<PublisherDto> findBySlug(String slug);
    PublisherDto save(PublisherDto publisherDto);
    void delete(String slug);
}
