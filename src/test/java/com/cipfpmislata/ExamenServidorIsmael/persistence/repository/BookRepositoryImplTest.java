package com.cipfpmislata.ExamenServidorIsmael.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Book;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Page;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.BookRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.BookMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.BookJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.BookJpaEntity;

@ExtendWith(MockitoExtension.class)
public class BookRepositoryImplTest {

    @Mock
    private BookJpaDao bookJpaDao;

    @InjectMocks
    private BookRepositoryImpl bookRepositoryImpl;

    @Nested
    class findAll {
        @Test
        @DisplayName("findAll should return a list of books")
        void findAll_ShouldReturnListOfBooks_WhenBooksExist() {
            // Arrange
            int page = 1;
            int size = 10;
            long totalElements = 2L;

            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);

            Book book1 = new Book(1L, "Book 1", "Isbn 1", new BigDecimal(19.99), publisher, authors);
            Book book2 = new Book(2L, "Book 2", "Isbn 2", new BigDecimal(19.99), publisher, authors);

            List<Book> expectedBooks = List.of(book1, book2);
            List<BookDto> expectedBooksDto = expectedBooks.stream()
                    .map(BookMapper.getInstance()::fromBookToBookDto).toList();

            List<BookJpaEntity> expectedBooksJpaEntity = expectedBooksDto.stream()
                    .map(BookMapper.getInstance()::fromBookDtoToBookJpaEntity).toList();

            when(bookJpaDao.findAll(page, size)).thenReturn(expectedBooksJpaEntity);
            when(bookJpaDao.count()).thenReturn(totalElements);

            // Act
            Page<BookDto> result = bookRepositoryImpl.findAll(page, size);

            // Assert
            assertAll(
                    () -> assertEquals(expectedBooksDto, result.data()),
                    () -> assertEquals(page, result.pageNumber()),
                    () -> assertEquals(size, result.pageSize()),
                    () -> assertEquals(totalElements, result.totalElements()));
            verify(bookJpaDao).findAll(page, size);
        }

        @Test
        @DisplayName("findAll should return an empty list when no books exist")
        void findAll_ShouldReturnEmptyList_WhenNoBooksExist() {
            // Arrange

            int page = 1;
            int size = 1;
            when(bookJpaDao.findAll(page, size)).thenReturn(List.of());
            when(bookJpaDao.count()).thenReturn(0L);

            // Act
            Page<BookDto> result = bookRepositoryImpl.findAll(page, size);

            // Assert
            assertAll(
                    () -> assertTrue(result.data().isEmpty()),
                    () -> assertEquals(0, result.totalElements()));
            verify(bookJpaDao).findAll(page, size);
        }
    }

    @Nested
    class findById {
        @Test
        @DisplayName("findById should return a book")
        void findById_ShouldReturnBook_WhenBookExists() {

            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(1L, "Book 1", "Isbn 1", new BigDecimal(19.99), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            when(bookJpaDao.findBookById(expectedBook.getId())).thenReturn(Optional.of(expectedBookJpaEntity));

            // Act
            Optional<BookDto> result = bookRepositoryImpl.findById(expectedBook.getId());

            // Assert
            assertEquals(expectedBookDto, result.get());
            verify(bookJpaDao).findBookById(expectedBook.getId());
        }

        @Test
        @DisplayName("findById should return an empty optional when book does not exist")
        void findById_ShouldReturnEmptyOptional_WhenBookDoesNotExist() {
            // Arrange
            Long id = 1L;
            when(bookJpaDao.findBookById(id)).thenReturn(Optional.empty());

            // Act
            Optional<BookDto> result = bookRepositoryImpl.findById(id);

            // Assert
            assertTrue(result.isEmpty());
            verify(bookJpaDao).findBookById(id);
        }
    }

    @Nested
    class findByIsbn {
        @Test
        @DisplayName("findByIsbn should return a book")
        void findByIsbn_ShouldReturnBook_WhenBookExists() {
            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(1L, "Book 1", "Isbn 1", new BigDecimal(19.99), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            when(bookJpaDao.findBookByIsbn(expectedBook.getIsbn())).thenReturn(Optional.of(expectedBookJpaEntity));

            // Act
            Optional<BookDto> result = bookRepositoryImpl.findByIsbn(expectedBook.getIsbn());

            assertEquals(expectedBookDto, result.get());
            verify(bookJpaDao).findBookByIsbn(expectedBook.getIsbn());
        }

        @Test
        @DisplayName("findByIsbn should return an empty optional when book does not exist")
        void findByIsbn_ShouldReturnEmptyOptional_WhenBookDoesNotExist() {
            // Arrange
            String isbn = "Isbn 1";
            when(bookJpaDao.findBookByIsbn(isbn)).thenReturn(Optional.empty());

            // Act
            Optional<BookDto> result = bookRepositoryImpl.findByIsbn(isbn);

            // Assert
            assertTrue(result.isEmpty());
            verify(bookJpaDao).findBookByIsbn(isbn);
        }
    }

    @Nested
    class save {
        @Test
        @DisplayName("save should insert a new book")
        void save_ShouldInsertNewBook_WhenBookDoesNotExist() {
            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(null, "Book 1", "Isbn 1", new BigDecimal(19.99), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            when(bookJpaDao.insert(expectedBookJpaEntity)).thenReturn(expectedBookJpaEntity);

            // Act
            BookDto result = bookRepositoryImpl.save(expectedBookDto);

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookDto.id(), result.id()),
                    () -> assertEquals(expectedBookDto.titleEs(), result.titleEs()),
                    () -> assertEquals(expectedBookDto.isbn(), result.isbn()),
                    () -> assertEquals(expectedBookDto.price(), result.price()),
                    () -> assertEquals(expectedBookDto.publisher(), result.publisher()),
                    () -> assertEquals(expectedBookDto.authors(), result.authors()));
            verify(bookJpaDao).insert(expectedBookJpaEntity);
        }

        @Test
        @DisplayName("save should update an existing book")
        void save_ShouldUpdateExistingBook_WhenBookExists() {
            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(1L, "Book 1", "Isbn 1", new BigDecimal(19.99), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            when(bookJpaDao.update(expectedBookJpaEntity)).thenReturn(expectedBookJpaEntity);

            // Act
            BookDto result = bookRepositoryImpl.save(expectedBookDto);

            // Assert
            assertEquals(expectedBookDto, result);
            verify(bookJpaDao).update(expectedBookJpaEntity);
        }
    }

    @Nested
    class deleteByIsbn {
        @Test
        @DisplayName("deleteByIsbn should delete a book")
        void deleteByIsbn_ShouldDeleteBook_WhenBookExists() {
            // Arrange
            String isbn = "Isbn 1";

            // Act
            bookRepositoryImpl.deleteByIsbn(isbn);

            // Assert
            verify(bookJpaDao).deleteByIsbn(isbn);
        }
    }
}
