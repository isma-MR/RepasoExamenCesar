package com.cipfpmislata.ExamenServidorIsmael.mapper;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.PublisherJpaEntity;

public class PublisherMapper {

    private static PublisherMapper INSTANCE;

    private PublisherMapper() {
    }

    public static PublisherMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PublisherMapper();
        }
        return INSTANCE;
    }

    public Publisher fromPublisherDtoToPublisher(PublisherDto PublisherDto) {
        if (PublisherDto == null) {
            return null;
        }
        return new Publisher(
                PublisherDto.id(),
                PublisherDto.name(),
                PublisherDto.slug());
    }

    public PublisherDto fromPublisherToPublisherDto(Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return new PublisherDto(
                publisher.getId(),
                publisher.getName(),
                publisher.getSlug());
    }

    public PublisherDto fromPublisherJpaEntityToPublisherDto(PublisherJpaEntity publisherJpaEntity) {
        if (publisherJpaEntity == null) {
            return null;
        }
        return new PublisherDto(
                publisherJpaEntity.getId(),
                publisherJpaEntity.getName(),
                publisherJpaEntity.getSlug());
    }

    public PublisherJpaEntity fromPublisherDtoToPublisherJpaEntity(PublisherDto publisherDto) {
        if (publisherDto == null) {
            return null;
        }
        return new PublisherJpaEntity(
                publisherDto.id(),
                publisherDto.name(),
                publisherDto.slug());
    }
}
