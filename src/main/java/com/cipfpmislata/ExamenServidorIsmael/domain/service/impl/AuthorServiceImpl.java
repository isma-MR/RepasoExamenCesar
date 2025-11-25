package com.cipfpmislata.ExamenServidorIsmael.domain.service.impl;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.AuthorRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.AuthorService;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.BusinessException;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Optional<AuthorDto> findAuthorById(Long id) {
        Optional<AuthorDto> author = authorRepository.findAuthorById(id);
        if (author.isPresent()) {
            return author;
        } else {
            throw  new ResourceNotFoundException("Author not found");
        }
    }

    @Override
    public Optional<AuthorDto> findAuthorBySlug(String slug) {
        Optional<AuthorDto> author = authorRepository.findAuthorBySlug(slug);
        if (author.isPresent()) {
            return author;
        } else {
            throw  new ResourceNotFoundException("Author not found");
        }
    }

    @Override
    public List<AuthorDto> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) {
        Optional<AuthorDto> author = authorRepository.findAuthorById(authorDto.id());
        if (author.isEmpty()) {
            return authorRepository.save(authorDto);
        } else {
            throw new BusinessException("Author already exists");
        }
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) {
        Optional<AuthorDto> author = authorRepository.findAuthorById(authorDto.id());
        if (author.isPresent()) {
            return authorRepository.save(authorDto);
        } else  {
            throw new ResourceNotFoundException("Author does not exists");
        }
    }

    @Override
    public void delete(Long id) {
        Optional<AuthorDto> author = authorRepository.findAuthorById(id);
        if (author.isPresent()) {
            authorRepository.delete(id);
        } else  {
            throw new ResourceNotFoundException("Author does not exists");
        }
    }
}
