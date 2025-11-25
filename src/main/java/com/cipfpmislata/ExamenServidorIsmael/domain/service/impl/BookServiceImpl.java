package com.cipfpmislata.ExamenServidorIsmael.domain.service.impl;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Page;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.BookRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.BookService;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.BusinessException;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;

import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Page<BookDto> findAll(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
        return bookRepository.findAll(page, size);
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        Optional<BookDto> bookDto = bookRepository.findById(id);
        if (bookDto.isPresent()) {
            return bookDto;
        } else {
            throw new ResourceNotFoundException("Book not found");
        }
    }

    @Override
    public Optional<BookDto> findByIsbn(String isbn) {
        Optional<BookDto> bookDto = bookRepository.findByIsbn(isbn);
        if (bookDto.isPresent()) {
            return bookDto;
        } else {
            throw new ResourceNotFoundException("Book not found");
        }
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Optional<BookDto> book = bookRepository.findByIsbn(bookDto.isbn());
        if (book.isPresent()) {
            throw new BusinessException("Book already exists");
        } else {
            return bookRepository.save(bookDto);
        }
    }

    @Override
    public BookDto update(BookDto bookDto) {
        Optional<BookDto> book = bookRepository.findByIsbn(bookDto.isbn());
        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book doesn't exists");
        } else {
            BookDto b = book.get();
            if (!b.id().equals(bookDto.id())) {
                throw new BusinessException("Another book with ISBN " + bookDto.isbn() + " already exists");
            }
            return bookRepository.save(bookDto);
        }
    }

    @Override
    public void deleteByIsbn(String isbn) {
        Optional<BookDto> book = bookRepository.findByIsbn(isbn);
        if (book.isEmpty()) {
            throw new ResourceNotFoundException("Book doesn't exists");
        } else {
            bookRepository.deleteByIsbn(isbn);
        }
    }
}
