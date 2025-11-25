package com.cipfpmislata.ExamenServidorIsmael.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.AuthorMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.AuthorJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.AuthorJpaEntity;

@ExtendWith(MockitoExtension.class)
public class AuthorRepositoryImplTest {

    @Mock
    private AuthorJpaDao authorJpaDao;

    @InjectMocks
    private AuthorRepositoryImpl authorRepositoryImpl;

    @Nested
    class TestFindAll {
        @Test
        @DisplayName("Test findAll should return list of authors when authors exist")
        void testFindAll_ShouldReturnListOfAuthors_WhenAuthorsExist() {

            // Arrange
            List<Author> expectedAuthors = List.of(
                    new Author(1L, "Author 1", "Slug 1"),
                    new Author(2L, "Author 2", "Slug 2"));
            List<AuthorDto> expectedAuthorsDto = expectedAuthors.stream()
                    .map(AuthorMapper.getInstance()::fromAuthorToAuthorDto).toList();
            List<AuthorJpaEntity> expectedAuthorsJpaEntity = expectedAuthorsDto.stream()
                    .map(AuthorMapper.getInstance()::fromAuthorDtoToAuthorJpaEntity).toList();

            when(authorJpaDao.findAll()).thenReturn(expectedAuthorsJpaEntity);

            // Act
            List<AuthorDto> actualAuthors = authorRepositoryImpl.findAll();

            // Assert
            assertAll(
                    () -> assertEquals(expectedAuthorsDto.get(0).id(), actualAuthors.get(0).id()),
                    () -> assertEquals(expectedAuthorsDto.get(0).name(), actualAuthors.get(0).name()),
                    () -> assertEquals(expectedAuthorsDto.get(0).slug(), actualAuthors.get(0).slug()),
                    () -> assertEquals(expectedAuthorsDto.get(1).id(), actualAuthors.get(1).id()),
                    () -> assertEquals(expectedAuthorsDto.get(1).name(), actualAuthors.get(1).name()),
                    () -> assertEquals(expectedAuthorsDto.get(1).slug(), actualAuthors.get(1).slug()));
            verify(authorJpaDao).findAll();
        }

        @Test
        @DisplayName("Test findAll should return empty list when no authors exist")
        void testFindAll_ShouldReturnEmptyList_WhenNoAuthorsExist() {
            // Arrange
            when(authorJpaDao.findAll()).thenReturn(List.of());

            // Act
            List<AuthorDto> actualAuthors = authorRepositoryImpl.findAll();

            // Assert
            assertTrue(actualAuthors.isEmpty());
            verify(authorJpaDao).findAll();
        }
    }

    @Nested
    class TestFindBySlug {
        @Test
        @DisplayName("Test findBySlug should return author when author exists")
        void testFindBySlug_ShouldReturnAuthor_WhenAuthorExists() {
            // Arrange
            Author expectedAuthor = new Author(1L, "Author 1", "Slug 1");
            AuthorDto expectedAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
            AuthorJpaEntity expectedAuthorJpaEntity = AuthorMapper.getInstance()
                    .fromAuthorDtoToAuthorJpaEntity(expectedAuthorDto);

            when(authorJpaDao.findAuthorBySlug(expectedAuthor.getSlug()))
                    .thenReturn(Optional.of(expectedAuthorJpaEntity));

            // Act
            Optional<AuthorDto> actualAuthor = authorRepositoryImpl.findAuthorBySlug(expectedAuthor.getSlug());

            // Assert
            assertAll(
                    () -> assertEquals(expectedAuthorDto.id(), actualAuthor.get().id()),
                    () -> assertEquals(expectedAuthorDto.name(), actualAuthor.get().name()),
                    () -> assertEquals(expectedAuthorDto.slug(), actualAuthor.get().slug()));
            verify(authorJpaDao).findAuthorBySlug(expectedAuthor.getSlug());
        }

        @Test
        @DisplayName("Test findBySlug should return empty optional when author does not exist")
        void testFindBySlug_ShouldReturnEmptyOptional_WhenAuthorDoesNotExist() {
            // Arrange
            String slug = "Slug 1";
            when(authorJpaDao.findAuthorBySlug(slug)).thenReturn(Optional.empty());

            // Act
            Optional<AuthorDto> actualAuthor = authorRepositoryImpl.findAuthorBySlug(slug);

            // Assert
            assertTrue(actualAuthor.isEmpty());
            verify(authorJpaDao).findAuthorBySlug(slug);
        }
    }

    @Nested
    class TestFindById {
        @Test
        @DisplayName("Test findById should return author when author exists")
        void testFindById_ShouldReturnAuthor_WhenAuthorExists() {
            // Arrange
            Author expectedAuthor = new Author(1L, "Author 1", "Slug 1");
            AuthorDto expectedAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
            AuthorJpaEntity expectedAuthorJpaEntity = AuthorMapper.getInstance()
                    .fromAuthorDtoToAuthorJpaEntity(expectedAuthorDto);
            when(authorJpaDao.findAuthorById(expectedAuthor.getId())).thenReturn(Optional.of(expectedAuthorJpaEntity));

            // Act
            Optional<AuthorDto> actualAuthor = authorRepositoryImpl.findAuthorById(expectedAuthor.getId());

            // Assert
            assertAll(
                    () -> assertEquals(expectedAuthorDto.id(), actualAuthor.get().id()),
                    () -> assertEquals(expectedAuthorDto.name(), actualAuthor.get().name()),
                    () -> assertEquals(expectedAuthorDto.slug(), actualAuthor.get().slug()));
            verify(authorJpaDao).findAuthorById(expectedAuthor.getId());
        }

        @Test
        @DisplayName("Test findById should return empty optional when author does not exist")
        void testFindById_ShouldReturnEmptyOptional_WhenAuthorDoesNotExist() {
            // Arrange
            Long id = 1L;
            when(authorJpaDao.findAuthorById(id)).thenReturn(Optional.empty());

            // Act
            Optional<AuthorDto> actualAuthor = authorRepositoryImpl.findAuthorById(id);

            // Assert
            assertTrue(actualAuthor.isEmpty());
            verify(authorJpaDao).findAuthorById(id);
        }
    }

    @Nested
    class TestSave {
        @Test
        @DisplayName("Test save should insert when author id is null")
        void testSave_ShouldInsert_WhenAuthorIdIsNull() {
            // Arrange
            Author author = new Author(null, "Author 1", "Slug 1");
            AuthorDto authorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);
            AuthorJpaEntity authorJpaEntity = AuthorMapper.getInstance().fromAuthorDtoToAuthorJpaEntity(authorDto);
            when(authorJpaDao.insert(authorJpaEntity)).thenReturn(authorJpaEntity);

            // Act
            AuthorDto actualAuthorDto = authorRepositoryImpl.save(authorDto);

            // Assert
            assertAll(
                    () -> assertEquals(authorDto.id(), actualAuthorDto.id()),
                    () -> assertEquals(authorDto.name(), actualAuthorDto.name()),
                    () -> assertEquals(authorDto.slug(), actualAuthorDto.slug()));
            verify(authorJpaDao).insert(authorJpaEntity);
        }

        @Test
        @DisplayName("Test save should update when book id is not null")
        void testSave_ShouldUpdate_WhenBookIdIsNotNull() {
            // Arrange
            Author author = new Author(1L, "Author 1", "Slug 1");
            AuthorDto authorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);
            AuthorJpaEntity authorJpaEntity = AuthorMapper.getInstance().fromAuthorDtoToAuthorJpaEntity(authorDto);
            when(authorJpaDao.update(authorJpaEntity)).thenReturn(authorJpaEntity);

            // Act
            AuthorDto actualAuthorDto = authorRepositoryImpl.save(authorDto);

            // Assert
            assertAll(
                    () -> assertEquals(authorDto.id(), actualAuthorDto.id()),
                    () -> assertEquals(authorDto.name(), actualAuthorDto.name()),
                    () -> assertEquals(authorDto.slug(), actualAuthorDto.slug()));
            verify(authorJpaDao).update(authorJpaEntity);
        }

    }

    @Nested
    class TestDelete {
        @Test
        @DisplayName("Test delete should delete when author id is not null")
        void testDelete_ShouldDelete_WhenAuthorIdIsNotNull() {
            // Arrange
            Long id = 1L;

            // Act
            authorRepositoryImpl.delete(id);

            // Assert
            verify(authorJpaDao).delete(id);
        }
    }

}
