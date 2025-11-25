package com.cipfpmislata.ExamenServidorIsmael.mapper;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Book;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.BookJpaEntity;

import java.util.List;

public class BookMapper {

    private static BookMapper INSTANCE;

    private BookMapper() {
    }

    public static BookMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookMapper();
        }
        return INSTANCE;
    }

    public BookDto fromBookToBookDto(Book book) {
        if (book == null) {
            return null;
        }
        List<AuthorDto> authors = null;
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            authors = book.getAuthors().stream().map(AuthorMapper.getInstance()::fromAuthorToAuthorDto).toList();
        }
        return new BookDto(
                book.getId(),
                book.getIsbn(),
                book.getTitleEs(),
                book.getPrice(),
                PublisherMapper.getInstance().fromPublisherToPublisherDto(book.getPublisher()),
                authors);
    }

    public Book fromBookDtoToBook(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        return new Book(
                bookDto.id(),
                bookDto.isbn(),
                bookDto.titleEs(),
                bookDto.price(),
                PublisherMapper.getInstance().fromPublisherDtoToPublisher(bookDto.publisher()),
                bookDto.authors().stream().map(AuthorMapper.getInstance()::fromAuthorDtoToAuthor).toList());
    }

    public BookDto fromBookJpaEntityToBookDto(BookJpaEntity bookJpaEntity) {
        if (bookJpaEntity == null) {
            return null;
        }
        List<AuthorDto> authors = null;
        if (bookJpaEntity.getAuthors() != null && !bookJpaEntity.getAuthors().isEmpty()) {
            authors = bookJpaEntity.getAuthors().stream()
                    .map(AuthorMapper.getInstance()::fromAuthorJpaEntityToAuthorDto).toList();
        }
        return new BookDto(
                bookJpaEntity.getId(),
                bookJpaEntity.getIsbn(),
                bookJpaEntity.getTitleEs(),
                bookJpaEntity.getPrice(),
                PublisherMapper.getInstance().fromPublisherJpaEntityToPublisherDto(bookJpaEntity.getPublisher()),
                authors);
    }

    public BookJpaEntity fromBookDtoToBookJpaEntity(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }

        return new BookJpaEntity(
                bookDto.id(),
                bookDto.isbn(),
                bookDto.titleEs(),
                bookDto.price(),
                PublisherMapper.getInstance().fromPublisherDtoToPublisherJpaEntity(bookDto.publisher()),
                bookDto.authors().stream().map(AuthorMapper.getInstance()::fromAuthorDtoToAuthorJpaEntity).toList());
    }

}
