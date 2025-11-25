package com.cipfpmislata.ExamenServidorIsmael.domain.service.impl;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.PublisherRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.PublisherService;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.BusinessException;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<PublisherDto> findAll() {
        return publisherRepository.findAll();
    }

    @Override
    public Optional<PublisherDto> findBySlug(String slug) {
        Optional<PublisherDto> publisher = publisherRepository.findBySlug(slug);
        if (publisher.isEmpty()) {
            throw new ResourceNotFoundException("Publisher not found");
        } else {
            return publisher;
        }
    }

    @Override
    public Optional<PublisherDto> findById(Long id) {
        Optional<PublisherDto> publisher = publisherRepository.findById(id);
        if (publisher.isEmpty()) {
            throw new ResourceNotFoundException("Publisher not found");
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
            throw new BusinessException("Publisher already exists");
        }
    }

    @Override
    public PublisherDto update(PublisherDto publisherDto) {
        Optional<PublisherDto> publisher = publisherRepository.findBySlug(publisherDto.slug());
        if (publisher.isEmpty()) {
            throw new ResourceNotFoundException("Publisher not found");
        } else {
            return publisherRepository.save(publisherDto);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<PublisherDto> publisher = publisherRepository.findById(id);
        if (publisher.isEmpty()) {
            throw new ResourceNotFoundException("Publisher not found");
        } else {
            publisherRepository.delete(id);
        }
    }
}
