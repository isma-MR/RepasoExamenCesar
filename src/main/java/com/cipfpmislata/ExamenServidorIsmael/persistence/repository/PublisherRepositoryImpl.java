package com.cipfpmislata.ExamenServidorIsmael.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.cipfpmislata.ExamenServidorIsmael.domain.repository.PublisherRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.PublisherMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.PublisherJpaEntity;

public class PublisherRepositoryImpl implements PublisherRepository {

    private PublisherJpaDao publisherJpaDao;

    public PublisherRepositoryImpl(PublisherJpaDao publisherJpaDao) {
        this.publisherJpaDao = publisherJpaDao;
    }

    @Override
    public List<PublisherDto> findAll() {
        return publisherJpaDao.findAll().stream()
                .map(PublisherMapper.getInstance()::fromPublisherJpaEntityToPublisherDto).toList();
    }

    @Override
    public Optional<PublisherDto> findBySlug(String slug) {
        return publisherJpaDao.findPublisherBySlug(slug)
                .map(PublisherMapper.getInstance()::fromPublisherJpaEntityToPublisherDto);
    }

    @Override
    public Optional<PublisherDto> findById(Long id) {
        return publisherJpaDao.findPublisherById(id)
                .map(PublisherMapper.getInstance()::fromPublisherJpaEntityToPublisherDto);
    }

    @Override
    public PublisherDto save(PublisherDto publisherDto) {
        if (publisherDto.id() == null) {
            PublisherJpaEntity publisherJpaEntity = PublisherMapper.getInstance()
                    .fromPublisherDtoToPublisherJpaEntity(publisherDto);
            return PublisherMapper.getInstance()
                    .fromPublisherJpaEntityToPublisherDto(publisherJpaDao.insert(publisherJpaEntity));
        } else {
            PublisherJpaEntity publisherJpaEntity = PublisherMapper.getInstance()
                    .fromPublisherDtoToPublisherJpaEntity(publisherDto);
            return PublisherMapper.getInstance()
                    .fromPublisherJpaEntityToPublisherDto(publisherJpaDao.update(publisherJpaEntity));
        }
    }

    @Override
    public void delete(Long id) {
        publisherJpaDao.delete(id);
    }

}
