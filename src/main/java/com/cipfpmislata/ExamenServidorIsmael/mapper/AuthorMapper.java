package com.cipfpmislata.ExamenServidorIsmael.mapper;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.AuthorJpaEntity;

public class AuthorMapper {

    private static AuthorMapper instance;

    private AuthorMapper() {
    }

    public static AuthorMapper getInstance() {
        if (instance == null) {
            instance = new AuthorMapper();
        }
        return instance;
    }

    public AuthorDto fromAuthorToAuthorDto(Author author) {
        if (author == null) {
            return null;
        }
        return new AuthorDto(
                author.getId(),
                author.getName(),
                author.getSlug());
    }

    public Author fromAuthorDtoToAuthor(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }
        return new Author(
                authorDto.id(),
                authorDto.name(),
                authorDto.slug());
    }

    public AuthorDto fromAuthorJpaEntityToAuthorDto(AuthorJpaEntity authorJpaEntity) {
        if (authorJpaEntity == null) {
            return null;
        }
        return new AuthorDto(
                authorJpaEntity.getId(),
                authorJpaEntity.getName(),
                authorJpaEntity.getSlug());
    }

    public AuthorJpaEntity fromAuthorDtoToAuthorJpaEntity(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }
        return new AuthorJpaEntity(
                authorDto.id(),
                authorDto.name(),
                authorDto.slug());
    }

}
