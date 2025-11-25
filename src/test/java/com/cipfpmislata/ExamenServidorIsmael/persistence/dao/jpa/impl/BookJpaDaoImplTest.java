package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Book;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Page;
import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.BookDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.BookMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.TestConfig;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.BookJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.BookJpaEntity;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookJpaDaoImplTest {
    @Autowired
    private BookJpaDao bookJpaDao;

    @Nested
    public class FindBookById {
        @Test
        public void testFindBookById() {

            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(1L, "1234567890", "Book 1", new BigDecimal(10), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            // Act
            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            // Assert
            Optional<BookJpaEntity> actualBook = bookJpaDao.findBookById(1L);

            assertAll(
                    () -> assertEquals(expectedBookJpaEntity.getId(), actualBook.get().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getTitleEs(), actualBook.get().getTitleEs()),
                    () -> assertEquals(expectedBookJpaEntity.getIsbn(), actualBook.get().getIsbn()),
                    () -> assertEquals(expectedBookJpaEntity.getPublisher().getId(),
                            actualBook.get().getPublisher().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(0).getId(),
                            actualBook.get().getAuthors().get(0).getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(1).getId(),
                            actualBook.get().getAuthors().get(1).getId()));
        }
    }

    @Nested
    public class FindBookByIsbn {
        @Test
        public void testFindBookByIsbn() {

            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(1L, "1234567890", "Book 1", new BigDecimal(10), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            // Act
            Optional<BookJpaEntity> actualBook = bookJpaDao.findBookByIsbn("1234567890");

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookJpaEntity.getId(), actualBook.get().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getTitleEs(), actualBook.get().getTitleEs()),
                    () -> assertEquals(expectedBookJpaEntity.getIsbn(), actualBook.get().getIsbn()),
                    () -> assertEquals(expectedBookJpaEntity.getPublisher().getId(),
                            actualBook.get().getPublisher().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(0).getId(),
                            actualBook.get().getAuthors().get(0).getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(1).getId(),
                            actualBook.get().getAuthors().get(1).getId()));
        }
    }

    @Nested
    public class FindAll {
        @Test
        public void testFindAll() {

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
            List<BookJpaEntity> expectedBookJpaEntity = expectedBooksDto.stream()
                    .map(BookMapper.getInstance()::fromBookDtoToBookJpaEntity).toList();

            // Act
            List<BookJpaEntity> actualPage = bookJpaDao.findAll(page, size);

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookJpaEntity.size(), actualPage.size()),
                    () -> assertEquals(expectedBookJpaEntity.get(0).getId(), actualPage.get(0).getId()),
                    () -> assertEquals(expectedBookJpaEntity.get(0).getTitleEs(), actualPage.get(0).getTitleEs()),
                    () -> assertEquals(expectedBookJpaEntity.get(0).getIsbn(), actualPage.get(0).getIsbn()),
                    () -> assertEquals(expectedBookJpaEntity.get(0).getPublisher().getId(),
                            actualPage.get(0).getPublisher().getId()),
                    () -> assertEquals(expectedBookJpaEntity.get(0).getAuthors().get(0).getId(),
                            actualPage.get(0).getAuthors().get(0).getId()),
                    () -> assertEquals(expectedBookJpaEntity.get(0).getAuthors().get(1).getId(),
                            actualPage.get(0).getAuthors().get(1).getId()));

        }
    }

    @Nested
    public class Insert {
        @Test
        public void testInsert() {

            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(null, "1234567890", "Book 1", new BigDecimal(10), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            // Act
            Long totalBooksBeforeInsert = bookJpaDao.count();
            bookJpaDao.insert(expectedBookJpaEntity);
            Optional<BookJpaEntity> actualBook = bookJpaDao.findBookByIsbn("1234567890");
            Long totalBooksAfterInsert = bookJpaDao.count();

            // Assert
            assertAll(
                    () -> assertEquals(totalBooksBeforeInsert + 1, totalBooksAfterInsert),
                    () -> assertEquals(expectedBookJpaEntity.getId(), actualBook.get().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getTitleEs(), actualBook.get().getTitleEs()),
                    () -> assertEquals(expectedBookJpaEntity.getIsbn(), actualBook.get().getIsbn()),
                    () -> assertEquals(expectedBookJpaEntity.getPublisher().getId(),
                            actualBook.get().getPublisher().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(0).getId(),
                            actualBook.get().getAuthors().get(0).getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(1).getId(),
                            actualBook.get().getAuthors().get(1).getId()));
        }
    }

    @Nested
    public class Update {
        @Test
        public void testUpdate() {

            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(1L, "1234567890", "Book 1", new BigDecimal(10), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            // Act
            Optional<BookJpaEntity> actualBook = bookJpaDao.findBookByIsbn("1234567890");

            // Assert
            assertAll(
                    () -> assertEquals(expectedBookJpaEntity.getId(), actualBook.get().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getTitleEs(), actualBook.get().getTitleEs()),
                    () -> assertEquals(expectedBookJpaEntity.getIsbn(), actualBook.get().getIsbn()),
                    () -> assertEquals(expectedBookJpaEntity.getPublisher().getId(),
                            actualBook.get().getPublisher().getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(0).getId(),
                            actualBook.get().getAuthors().get(0).getId()),
                    () -> assertEquals(expectedBookJpaEntity.getAuthors().get(1).getId(),
                            actualBook.get().getAuthors().get(1).getId()));
        }
    }

    @Nested
    public class DeleteByIsbn {
        @Test
        public void testDeleteByIsbn() {

            // Arrange
            Publisher publisher = new Publisher(1L, "Publisher 1", "publisher1-slug");

            Author author1 = new Author(1L, "Author 1", "author1-slug");
            Author author2 = new Author(2L, "Author 2", "author2-slug");
            List<Author> authors = List.of(author1, author2);
            Book expectedBook = new Book(1L, "1234567890", "Book 1", new BigDecimal(10), publisher, authors);
            BookDto expectedBookDto = BookMapper.getInstance().fromBookToBookDto(expectedBook);

            BookJpaEntity expectedBookJpaEntity = BookMapper.getInstance().fromBookDtoToBookJpaEntity(expectedBookDto);

            // Act
            Long totalBooksBeforeDelete = bookJpaDao.count();
            bookJpaDao.deleteByIsbn("1234567890");
            Long totalBooksAfterDelete = bookJpaDao.count();

            // Assert
            assertAll(
                    () -> assertEquals(totalBooksBeforeDelete - 1, totalBooksAfterDelete));
        }
    }

}
