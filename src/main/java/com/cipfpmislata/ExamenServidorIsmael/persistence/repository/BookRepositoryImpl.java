package com.cipfpmislata.ExamenServidorIsmael.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.BookJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.BookJpaEntity;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Page;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.BookRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.BookMapper;

public class BookRepositoryImpl implements BookRepository {

    private BookJpaDao bookJpaDao;

    public BookRepositoryImpl(BookJpaDao bookJpaDao) {
        this.bookJpaDao = bookJpaDao;
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        return bookJpaDao.findBookById(id).map(BookMapper.getInstance()::fromBookJpaEntityToBookDto);
    }

    @Override
    public Optional<BookDto> findByIsbn(String isbn) {
        return bookJpaDao.findBookByIsbn(isbn).map(BookMapper.getInstance()::fromBookJpaEntityToBookDto);
    }

    @Override
    public Page<BookDto> findAll(int page, int size) {

        List<BookDto> bookDtoList = bookJpaDao.findAll(page, size).stream()
                .map(BookMapper.getInstance()::fromBookJpaEntityToBookDto).toList();
        Long totalElements = bookJpaDao.count();

        return new Page<>(bookDtoList, page, size, totalElements);
    }

    @Override
    public BookDto save(BookDto bookDto) {
        if (bookDto.id() == null) {
            BookJpaEntity bookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(bookDto);
            return BookMapper.getInstance().fromBookJpaEntityToBookDto(bookJpaDao.insert(bookJpaEntity));
        } else {
            BookJpaEntity bookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(bookDto);
            return BookMapper.getInstance().fromBookJpaEntityToBookDto(bookJpaDao.update(bookJpaEntity));
        }
    }

    @Override
    public void deleteByIsbn(String isbn) {
        bookJpaDao.deleteByIsbn(isbn);
    }

}
