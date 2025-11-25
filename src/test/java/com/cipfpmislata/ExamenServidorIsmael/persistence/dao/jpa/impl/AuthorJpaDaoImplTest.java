package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.AuthorMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.TestConfig;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.AuthorJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.AuthorJpaEntity;

import java.util.Optional;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorJpaDaoImplTest {

        @Autowired
        private AuthorJpaDao authorJpaDao;

        @Nested
        class TestFindAll {
                @Test
                @DisplayName("Test findAll should return list of authors when authors exist")
                void testFindAll_ShouldReturnListOfAuthors_WhenAuthorsExist() {
                        // Arrange
                        List<Author> expectedAuthors = List.of(
                                        new Author(1L, "Author 1", "author-1"),
                                        new Author(2L, "Author 2", "author-2"));
                        List<AuthorDto> expectedAuthorsDto = expectedAuthors.stream()
                                        .map(AuthorMapper.getInstance()::fromAuthorToAuthorDto).toList();
                        List<AuthorJpaEntity> expectedAuthorsJpaEntity = expectedAuthorsDto.stream()
                                        .map(AuthorMapper.getInstance()::fromAuthorDtoToAuthorJpaEntity).toList();
                        // Act
                        List<AuthorJpaEntity> actualAuthors = authorJpaDao.findAll();

                        // Assert
                        assertAll(
                                        () -> assertEquals(expectedAuthorsJpaEntity.get(0).getId(),
                                                        actualAuthors.get(0).getId()),
                                        () -> assertEquals(expectedAuthorsJpaEntity.get(0).getName(),
                                                        actualAuthors.get(0).getName()),
                                        () -> assertEquals(expectedAuthorsJpaEntity.get(0).getSlug(),
                                                        actualAuthors.get(0).getSlug()),
                                        () -> assertEquals(expectedAuthorsJpaEntity.get(1).getId(),
                                                        actualAuthors.get(1).getId()),
                                        () -> assertEquals(expectedAuthorsJpaEntity.get(1).getName(),
                                                        actualAuthors.get(1).getName()),
                                        () -> assertEquals(expectedAuthorsJpaEntity.get(1).getSlug(),
                                                        actualAuthors.get(1).getSlug()));
                }
        }

        @Nested
        class TestFindById {
                @Test
                @DisplayName("Test findById should return author when author exists")
                void testFindById_ShouldReturnAuthor_WhenAuthorExists() {
                        // Arrange
                        Author expectedAuthor = new Author(1L, "Author 1", "author-1");
                        AuthorDto expectedAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
                        AuthorJpaEntity expectedAuthorJpaEntity = AuthorMapper.getInstance()
                                        .fromAuthorDtoToAuthorJpaEntity(
                                                        expectedAuthorDto);
                        // Act
                        Optional<AuthorJpaEntity> actualAuthor = authorJpaDao.findAuthorById(1L);

                        // Assert
                        assertTrue(actualAuthor.isPresent());
                        assertAll(
                                        () -> assertEquals(expectedAuthorJpaEntity.getId(), actualAuthor.get().getId()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getName(),
                                                        actualAuthor.get().getName()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getSlug(),
                                                        actualAuthor.get().getSlug()));
                }
        }

        @Nested
        class TestFindBySlug {
                @Test
                @DisplayName("Test findBySlug should return author when author exists")
                void testFindBySlug_ShouldReturnAuthor_WhenAuthorExists() {
                        // Arrange
                        Author expectedAuthor = new Author(1L, "Author 1", "author-1");
                        AuthorDto expectedAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
                        AuthorJpaEntity expectedAuthorJpaEntity = AuthorMapper.getInstance()
                                        .fromAuthorDtoToAuthorJpaEntity(
                                                        expectedAuthorDto);
                        // Act
                        Optional<AuthorJpaEntity> actualAuthor = authorJpaDao.findAuthorBySlug("author-1");

                        // Assert
                        assertTrue(actualAuthor.isPresent());
                        assertAll(
                                        () -> assertEquals(expectedAuthorJpaEntity.getId(), actualAuthor.get().getId()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getName(),
                                                        actualAuthor.get().getName()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getSlug(),
                                                        actualAuthor.get().getSlug()));
                }
        }

        @Nested
        class TestInsert {
                @Test
                @DisplayName("Test insert should return author when author is inserted")
                void testInsert_ShouldReturnAuthor_WhenAuthorIsInserted() {
                        // Arrange
                        Author expectedAuthor = new Author(null, "Author 1", "author-1");
                        AuthorDto expectedAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
                        AuthorJpaEntity expectedAuthorJpaEntity = AuthorMapper.getInstance()
                                        .fromAuthorDtoToAuthorJpaEntity(
                                                        expectedAuthorDto);
                        // Act
                        int totalAuthors = authorJpaDao.findAll().size();
                        AuthorJpaEntity actualAuthor = authorJpaDao.insert(expectedAuthorJpaEntity);
                        int totalAuthorsAfterInsert = authorJpaDao.findAll().size();
                        // Assert
                        assertAll(
                                        () -> assertEquals(expectedAuthorJpaEntity.getId(), actualAuthor.getId()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getName(), actualAuthor.getName()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getSlug(), actualAuthor.getSlug()),

                                        () -> assertEquals(totalAuthors + 1, totalAuthorsAfterInsert));

                }
        }

        @Nested
        class TestUpdate {
                @Test
                @DisplayName("Test update should return author when author is updated")
                void testUpdate_ShouldReturnAuthor_WhenAuthorIsUpdated() {
                        // Arrange
                        Author expectedAuthor = new Author(1L, "Author 1", "author-1");
                        AuthorDto expectedAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
                        AuthorJpaEntity expectedAuthorJpaEntity = AuthorMapper.getInstance()
                                        .fromAuthorDtoToAuthorJpaEntity(
                                                        expectedAuthorDto);
                        // Act
                        AuthorJpaEntity actualAuthor = authorJpaDao.update(expectedAuthorJpaEntity);
                        // Assert
                        assertAll(
                                        () -> assertEquals(expectedAuthorJpaEntity.getId(), actualAuthor.getId()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getName(), actualAuthor.getName()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getSlug(), actualAuthor.getSlug()));
                }
        }

        @Nested
        class TestDelete {
                @Test
                @DisplayName("Test delete should return author when author is deleted")
                void testDelete_ShouldReturnAuthor_WhenAuthorIsDeleted() {
                        // Arrange
                        Author expectedAuthor = new Author(1L, "Author 1", "author-1");
                        AuthorDto expectedAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
                        AuthorJpaEntity expectedAuthorJpaEntity = AuthorMapper.getInstance()
                                        .fromAuthorDtoToAuthorJpaEntity(
                                                        expectedAuthorDto);
                        // Act
                        long totalAuthors = authorJpaDao.findAll().size();
                        authorJpaDao.delete(expectedAuthorJpaEntity.getId());
                        long totalAuthorsAfterDelete = authorJpaDao.findAll().size();
                        // Assert
                        assertAll(
                                        () -> assertEquals(expectedAuthorJpaEntity.getId(), expectedAuthor.getId()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getName(), expectedAuthor.getName()),
                                        () -> assertEquals(expectedAuthorJpaEntity.getSlug(), expectedAuthor.getSlug()),
                                        () -> assertEquals(totalAuthors - 1, totalAuthorsAfterDelete));
                }
        }

}
