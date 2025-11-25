package com.cipfpmislata.ExamenServidorIsmael.domain.service.impl;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.PublisherRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.PublisherService;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<PublisherDto> findAll(int page, int size) {
        if(page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        return publisherRepository.findAll(page, size);
    }

    @Override
    public Optional<PublisherDto> findBySlug(String slug) {
        Optional<PublisherDto> publisher = publisherRepository.findBySlug(slug);
        if (publisher.isEmpty()) {
            throw   new ResourceNotFoundException("Publisher not found");
        } else {
            return publisher;
        }
    }

    @Override
    public PublisherDto create(PublisherDto publisherDto) {
        Optional<PublisherDto> publisher = publisherRepository.findBySlug(publisherDto.slug());
        if (publisher.isEmpty()) {
            return publisherRepository.save(publisherDto);
        } else {
            throw new  ResourceNotFoundException("Publisher not found");
        }
    }

    @Override
    public PublisherDto update(PublisherDto publisherDto) {
        Optional<PublisherDto> publisher = publisherRepository.findBySlug(publisherDto.slug());
        if (publisher.isEmpty()) {
            throw  new ResourceNotFoundException("Publisher not found");
        } else {
            return publisherRepository.save(publisherDto);
        }
    }

    @Override
    public void delete(String slug) {
        Optional<PublisherDto> publisher = publisherRepository.findBySlug(slug);
        if (publisher.isEmpty()) {
            publisherRepository.delete(slug);
        } else {
            throw  new  ResourceNotFoundException("Publisher not found");
        }
    }
}
