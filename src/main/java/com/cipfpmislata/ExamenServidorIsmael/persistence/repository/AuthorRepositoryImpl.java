package com.cipfpmislata.ExamenServidorIsmael.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.cipfpmislata.ExamenServidorIsmael.domain.repository.AuthorRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.AuthorMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.AuthorJpaDao;

public class AuthorRepositoryImpl implements AuthorRepository {

    private AuthorJpaDao authorJpaDao;

    public AuthorRepositoryImpl(AuthorJpaDao authorJpaDao) {
        this.authorJpaDao = authorJpaDao;
    }

    @Override
    public Optional<AuthorDto> findAuthorById(Long id) {
        return authorJpaDao.findAuthorById(id).map(AuthorMapper.getInstance()::fromAuthorJpaEntityToAuthorDto);
    }

    @Override
    public Optional<AuthorDto> findAuthorBySlug(String slug) {
        return authorJpaDao.findAuthorBySlug(slug).map(AuthorMapper.getInstance()::fromAuthorJpaEntityToAuthorDto);
    }

    @Override
    public List<AuthorDto> findAll() {
        return authorJpaDao.findAll().stream().map(AuthorMapper.getInstance()::fromAuthorJpaEntityToAuthorDto).toList();
    }

    @Override
    public AuthorDto save(AuthorDto authorDto) {
        if (authorDto.id() == null) {
            return AuthorMapper.getInstance().fromAuthorJpaEntityToAuthorDto(
                    authorJpaDao.insert(AuthorMapper.getInstance().fromAuthorDtoToAuthorJpaEntity(authorDto)));
        } else {
            return AuthorMapper.getInstance().fromAuthorJpaEntityToAuthorDto(
                    authorJpaDao.update(AuthorMapper.getInstance().fromAuthorDtoToAuthorJpaEntity(authorDto)));
        }
    }

    @Override
    public void delete(Long id) {
        authorJpaDao.delete(id);
    }

}
