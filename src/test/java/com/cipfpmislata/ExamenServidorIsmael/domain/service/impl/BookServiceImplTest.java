package com.cipfpmislata.ExamenServidorIsmael.domain.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Book;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Page;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.BookRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.BusinessException;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;
import com.cipfpmislata.ExamenServidorIsmael.mapper.BookMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Nested
    class TestFindAll {
        @Test
        @DisplayName("Test findAll should return list of books when books exist")
        void testFindAll_ShouldReturnListOfBooks_WhenBooksExist() {
            // Arrange
            int page = 1;
            int size = 10;
            int totalElements = 2;

            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);

            Book book1 = new Book(1L, "Book 1", "Isbn 1", new BigDecimal(19.99), publisher, authors);
            Book book2 = new Book(2L, "Book 2", "Isbn 2", new BigDecimal(19.99), publisher, authors);

            List<Book> expectedBooks = List.of(book1, book2);
            List<BookDto> expectedBooksDto = expectedBooks.stream()
                    .map(BookMapper.getInstance()::fromBookToBookDto).toList();

            Page<BookDto> expectedPage = new Page<>(expectedBooksDto, page, size, totalElements);

            when(bookRepository.findAll(page, size)).thenReturn(expectedPage);

            // Act
            Page<BookDto> actualPage = bookService.findAll(page, size);

            // Assert
            assertAll(
                    () -> assertEquals(expectedPage.data().get(0).id(), actualPage.data().get(0).id()),
                    () -> assertEquals(expectedPage.data().get(0).titleEs(), actualPage.data().get(0).titleEs()),
                    () -> assertEquals(expectedPage.data().get(0).isbn(), actualPage.data().get(0).isbn()),
                    () -> assertEquals(expectedPage.data().get(1).id(), actualPage.data().get(1).id()),
                    () -> assertEquals(expectedPage.data().get(1).titleEs(), actualPage.data().get(1).titleEs()),
                    () -> assertEquals(expectedPage.data().get(1).isbn(), actualPage.data().get(1).isbn()));
            verify(bookRepository).findAll(page, size);
        }

        @Test
        @DisplayName("Test findAll should return empty list when no books exist")
        void testFindAll_ShouldReturnEmptyList_WhenNoBooksExist() {
            // Arrange
            when(bookRepository.findAll(anyInt(), anyInt())).thenReturn(new Page<>(List.of(), 1, 10, 0));

            // Act
            Page<BookDto> actualPage = bookService.findAll(1, 10);

            // Assert
            assertTrue(actualPage.data().isEmpty());
            verify(bookRepository).findAll(1, 10);
        }

        @Test
        @DisplayName("Test findAll should throw IllegalArgumentException when page or size is less than 1")
        void testFindAll_ShouldThrowIllegalArgumentException_WhenPageOrSizeIsLessThan1() {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> bookService.findAll(0, 10));
            assertThrows(IllegalArgumentException.class, () -> bookService.findAll(1, 0));
        }
    }

    @Nested
    class TestFindById {
        @Test
        @DisplayName("Test findById should return book when book exists")
        void testFindById_ShouldReturnBook_WhenBookExists() {
            // Arrange
            Book book = new Book(1L, "Book 1", "Isbn 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findById(1L)).thenReturn(Optional.of(expectedBookDto));

            // Act
            Optional<BookDto> actualBookDto = bookService.findById(1L);

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookDto.id(), actualBookDto.get().id()),
                    () -> assertEquals(expectedBookDto.titleEs(), actualBookDto.get().titleEs()),
                    () -> assertEquals(expectedBookDto.isbn(), actualBookDto.get().isbn()));
            verify(bookRepository).findById(1L);
        }

        @Test
        @DisplayName("Test findById should throw resource not found exception when book doesn't exist")
        void testFindById_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
            // Arrange
            when(bookRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> bookService.findById(1L));
            verify(bookRepository).findById(1L);
        }
    }

    @Nested
    class TestFindByIsbn {
        @Test
        @DisplayName("Test findByIsbn should return book when book exists")
        void testFindByIsbn_ShouldReturnBook_WhenBookExists() {
            // Arrange
            Book book = new Book(1L, "Book 1", "Isbn 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.of(expectedBookDto));

            // Act
            Optional<BookDto> actualBookDto = bookService.findByIsbn("Isbn 1");

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookDto.id(), actualBookDto.get().id()),
                    () -> assertEquals(expectedBookDto.titleEs(), actualBookDto.get().titleEs()),
                    () -> assertEquals(expectedBookDto.isbn(), actualBookDto.get().isbn()));
            verify(bookRepository).findByIsbn("Isbn 1");
        }

        @Test
        @DisplayName("Test findByIsbn should throw resource not found exception when book doesn't exist")
        void testFindByIsbn_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
            // Arrange
            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> bookService.findByIsbn("Isbn 1"));
            verify(bookRepository).findByIsbn("Isbn 1");
        }
    }

    @Nested
    class TestCreate {
        @Test
        @DisplayName("Test create should return book when book is created")
        void testCreate_ShouldReturnBook_WhenBookIsCreated() {
            // Arrange
            Book book = new Book(1L, "Isbn 1", "Book 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.empty());
            when(bookRepository.save(expectedBookDto)).thenReturn(expectedBookDto);

            // Act
            BookDto actualBookDto = bookService.create(expectedBookDto);

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookDto.id(), actualBookDto.id()),
                    () -> assertEquals(expectedBookDto.titleEs(), actualBookDto.titleEs()),
                    () -> assertEquals(expectedBookDto.isbn(), actualBookDto.isbn()));
            verify(bookRepository).save(expectedBookDto);
        }

        @Test
        @DisplayName("Test create should throw business exception when book already exists")
        void testCreate_ShouldThrowBusinessException_WhenBookAlreadyExists() {
            // Arrange
            Book book = new Book(1L, "Isbn 1", "Book 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.of(expectedBookDto));

            // Act & Assert
            assertThrows(BusinessException.class, () -> bookService.create(expectedBookDto));
            verify(bookRepository).findByIsbn("Isbn 1");
        }
    }

    @Nested
    class TestUpdate {
        @Test
        @DisplayName("Test update should return book when book is updated")
        void testUpdate_ShouldReturnBook_WhenBookIsUpdated() {
            // Arrange
            Book book = new Book(1L, "Isbn 1", "Book 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.of(expectedBookDto));
            when(bookRepository.save(expectedBookDto)).thenReturn(expectedBookDto);

            // Act
            BookDto actualBookDto = bookService.update(expectedBookDto);

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookDto.id(), actualBookDto.id()),
                    () -> assertEquals(expectedBookDto.titleEs(), actualBookDto.titleEs()),
                    () -> assertEquals(expectedBookDto.isbn(), actualBookDto.isbn()));
            verify(bookRepository).save(expectedBookDto);
        }

        @Test
        @DisplayName("Test update should throw resource not found exception when book doesn't exist")
        void testUpdate_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
            // Arrange
            Book book = new Book(1L, "Isbn 1", "Book 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> bookService.update(expectedBookDto));
            verify(bookRepository).findByIsbn("Isbn 1");
        }

        @Test
        @DisplayName("Test update with existing isbn should throw business exception")
        void testUpdate_WithExistingIsbn_ShouldThrowBusinessException() {
            // Arrange

            Book updatedBook = new Book(2L, "Isbn 1", "Book 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto updatedBookDto = BookMapper.getInstance().fromBookToBookDto(updatedBook);

            Book book = new Book(1L, "Isbn 1", "Book 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.of(expectedBookDto));

            // Act & Assert
            assertThrows(BusinessException.class, () -> bookService.update(updatedBookDto));
            verify(bookRepository).findByIsbn("Isbn 1");
        }
    }

    @Nested
    class TestDeleteByIsbn {
        @Test
        @DisplayName("Test deleteByIsbn should delete book when book exists")
        void testDeleteByIsbn_ShouldDeleteBook_WhenBookExists() {
            // Arrange
            Book book = new Book(1L, "Isbn 1", "Book 1", new BigDecimal(19.99),
                    new Publisher(1L, "Publisher 1", "publisher1-slug"), List.of());

            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(book);

            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.of(expectedBookDto));

            // Act
            bookService.deleteByIsbn("Isbn 1");

            // Assert
            verify(bookRepository).deleteByIsbn("Isbn 1");
        }

        @Test
        @DisplayName("Test deleteByIsbn should throw resource not found exception when book doesn't exist")
        void testDeleteByIsbn_ShouldThrowResourceNotFoundException_WhenBookDoesNotExist() {
            // Arrange
            when(bookRepository.findByIsbn("Isbn 1")).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> bookService.deleteByIsbn("Isbn 1"));
            verify(bookRepository).findByIsbn("Isbn 1");
        }
    }
}
