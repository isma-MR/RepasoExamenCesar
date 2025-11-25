package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorJpaDaoImplTest {

    @PersistenceContext
    private EntityManager entityManager;

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
                    () -> assertEquals(expectedAuthorsJpaEntity.get(0).getId(), actualAuthors.get(0).getId()),
                    () -> assertEquals(expectedAuthorsJpaEntity.get(0).getName(), actualAuthors.get(0).getName()),
                    () -> assertEquals(expectedAuthorsJpaEntity.get(0).getSlug(), actualAuthors.get(0).getSlug()),
                    () -> assertEquals(expectedAuthorsJpaEntity.get(1).getId(), actualAuthors.get(1).getId()),
                    () -> assertEquals(expectedAuthorsJpaEntity.get(1).getName(), actualAuthors.get(1).getName()),
                    () -> assertEquals(expectedAuthorsJpaEntity.get(1).getSlug(), actualAuthors.get(1).getSlug()));
        }

        @Test
        @DisplayName("Test findAll should return empty list when no authors exist")
        void testFindAll_ShouldReturnEmptyList_WhenNoAuthorsExist() {
            // Act
            List<AuthorJpaEntity> actualAuthors = authorJpaDao.findAll();

            // Assert
            assertTrue(actualAuthors.isEmpty());
        }
    }
}
