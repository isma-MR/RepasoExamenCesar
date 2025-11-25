package com.cipfpmislata.ExamenServidorIsmael.domain.service.impl;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Author;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.AuthorRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.AuthorDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.BusinessException;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;
import com.cipfpmislata.ExamenServidorIsmael.mapper.AuthorMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Nested
    class TestFindAllAuthors {
        @Test
        @DisplayName("Test findAll should return list of authors when authors exist")
        void testFindAll_ShouldReturnListOfAuthors_WhenAuthorsExist() {
            // Arrange
            List<Author> expectedAuthors = List.of(
                    new Author(1L, "Author 1", "Slug 1"),
                    new Author(2L, "Author 2", "Slug 2"));
            List<AuthorDto> expectedAuthorsDto = expectedAuthors.stream()
                    .map(AuthorMapper.getInstance()::fromAuthorToAuthorDto).toList();

            when(authorRepository.findAll()).thenReturn(expectedAuthorsDto);

            // Act
            List<AuthorDto> actualAuthors = authorService.findAll();

            // Assert
            assertAll(
                    () -> assertEquals(expectedAuthorsDto.get(0).id(), actualAuthors.get(0).id()),
                    () -> assertEquals(expectedAuthorsDto.get(0).name(), actualAuthors.get(0).name()),
                    () -> assertEquals(expectedAuthorsDto.get(0).slug(), actualAuthors.get(0).slug()),
                    () -> assertEquals(expectedAuthorsDto.get(1).id(), actualAuthors.get(1).id()),
                    () -> assertEquals(expectedAuthorsDto.get(1).name(), actualAuthors.get(1).name()),
                    () -> assertEquals(expectedAuthorsDto.get(1).slug(), actualAuthors.get(1).slug()));
            verify(authorRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Test findAll should return empty list when no authors exist")
        void testFindAll_ShouldReturnEmptyList_WhenNoAuthorsExist() {
            // Arrange
            when(authorRepository.findAll()).thenReturn(List.of());

            // Act
            List<AuthorDto> actualAuthors = authorService.findAll();

            // Assert
            assertTrue(actualAuthors.isEmpty());
            verify(authorRepository, times(1)).findAll();
        }
    }

    @Nested
    class TestFindAuthorById {
        @Test
        @DisplayName("Test findAuthorById should return author when author exists")
        void testFindAuthorById_ShouldReturnAuthor_WhenAuthorExists() {
            // Arrange
            Long authorId = 1L;
            Author expectedAuthor = new Author(authorId, "Author 1", "Slug 1");
            AuthorDto expecteAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
            when(authorRepository.findAuthorById(authorId)).thenReturn(Optional.of(expecteAuthorDto));

            // Act
            Optional<AuthorDto> actualAuthor = authorService.findAuthorById(authorId);

            // Assert
            assertAll(
                    () -> assertEquals(expecteAuthorDto.id(), actualAuthor.get().id()),
                    () -> assertEquals(expecteAuthorDto.name(), actualAuthor.get().name()),
                    () -> assertEquals(expecteAuthorDto.slug(), actualAuthor.get().slug()));
            verify(authorRepository).findAuthorById(authorId);
        }

        @Test
        @DisplayName("Test findAuthorById should throw ResourceNotFoundException when author does not exist")
        void testFindAuthorById_ShouldThrowResourceNotFoundException_WhenAuthorDoesNotExist() {
            // Arrange
            Long authorId = 1L;
            when(authorRepository.findAuthorById(authorId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> authorService.findAuthorById(authorId));
            verify(authorRepository).findAuthorById(authorId);

        }
    }

    @Nested
    class TestFindAuthorBySlug {
        @Test
        @DisplayName("Test findAuthorBySlug should return author when author exists")
        void testFindAuthorBySlug_ShouldReturnAuthor_WhenAuthorExists() {
            // Arrange
            String slug = "slug";
            Author expectedAuthor = new Author(1L, "Author 1", slug);
            AuthorDto expecteAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
            when(authorRepository.findAuthorBySlug(slug)).thenReturn(Optional.of(expecteAuthorDto));

            // Act
            Optional<AuthorDto> actualAuthor = authorService.findAuthorBySlug(slug);

            // Assert
            assertAll(
                    () -> assertEquals(expecteAuthorDto.id(), actualAuthor.get().id()),
                    () -> assertEquals(expecteAuthorDto.name(), actualAuthor.get().name()),
                    () -> assertEquals(expecteAuthorDto.slug(), actualAuthor.get().slug()));
            verify(authorRepository).findAuthorBySlug(slug);
        }

        @Test
        @DisplayName("Test findAuthorBySlug should throw ResourceNotFoundException when author does not exist")
        void testFindAuthorBySlug_ShouldThrowResourceNotFoundException_WhenAuthorDoesNotExist() {
            // Arrange
            String slug = "slug";
            when(authorRepository.findAuthorBySlug(slug)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> authorService.findAuthorBySlug(slug));
            verify(authorRepository).findAuthorBySlug(slug);
        }
    }

    @Nested
    class TestCreateAuthor {
        @Test
        @DisplayName("Test create should create author when author does not exist")
        void testCreate_ShouldCreateAuthor_WhenAuthorDoesNotExist() {
            // Arrange
            Author author = new Author(1L, "Author 1", "Slug 1");
            AuthorDto authorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);
            when(authorRepository.save(authorDto)).thenReturn(authorDto);

            // Act
            AuthorDto actualAuthor = authorService.create(authorDto);

            // Assert
            assertAll(
                    () -> assertEquals(authorDto.id(), actualAuthor.id()),
                    () -> assertEquals(authorDto.name(), actualAuthor.name()),
                    () -> assertEquals(authorDto.slug(), actualAuthor.slug()));
            verify(authorRepository).save(authorDto);
        }

        @Test
        @DisplayName("Test create should throw BusinessException when author already exists")
        void testCreate_ShouldThrowBusinessException_WhenAuthorAlreadyExists() {
            // Arrange
            Author author = new Author(1L, "Author 1", "Slug 1");
            AuthorDto authorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);
            when(authorRepository.findAuthorById(authorDto.id())).thenReturn(Optional.of(authorDto));

            // Act & Assert
            assertThrows(BusinessException.class, () -> authorService.create(authorDto));
            verify(authorRepository).findAuthorById(authorDto.id());
        }
    }

    @Nested
    class TestDeleteAuthor {
        @Test
        @DisplayName("Test delete should delete author when author exists")
        void testDelete_ShouldDeleteAuthor_WhenAuthorExists() {
            // Arrange
            Long authorId = 1L;
            Author author = new Author(authorId, "Author 1", "Slug 1");
            AuthorDto authorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(author);
            when(authorRepository.findAuthorById(authorId)).thenReturn(Optional.of(authorDto));

            // Act
            authorService.delete(authorId);

            // Assert
            verify(authorRepository).delete(authorId);
        }

        @Test
        @DisplayName("Test delete should throw ResourceNotFoundException when author does not exist")
        void testDelete_ShouldThrowResourceNotFoundException_WhenAuthorDoesNotExist() {
            // Arrange
            Long authorId = 1L;
            when(authorRepository.findAuthorById(authorId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> authorService.delete(authorId));
            verify(authorRepository).findAuthorById(authorId);
        }

    }

    @Nested
    class TestUpdate {
        @Test
        @DisplayName("Test update should update author when author exists")
        void testUpdate_ShouldUpdateAuthor_WhenAuthorExists() {
            // Arrange
            Long authorId = 1L;
            Author expectedAuthor = new Author(authorId, "Author 1", "Slug 1");
            AuthorDto expecteAuthorDto = AuthorMapper.getInstance().fromAuthorToAuthorDto(expectedAuthor);
            when(authorRepository.findAuthorById(authorId)).thenReturn(Optional.of(expecteAuthorDto));

            // Act
            Optional<AuthorDto> actualAuthor = authorService.findAuthorById(authorId);

            // Assert
            assertAll(
                    () -> assertEquals(expecteAuthorDto.id(), actualAuthor.get().id()),
                    () -> assertEquals(expecteAuthorDto.name(), actualAuthor.get().name()),
                    () -> assertEquals(expecteAuthorDto.slug(), actualAuthor.get().slug()));
            verify(authorRepository).findAuthorById(authorId);
        }

        @Test
        @DisplayName("Test update should throw ResourceNotFoundException when author does not exist")
        void testUpdate_ShouldThrowResourceNotFoundException_WhenAuthorDoesNotExist() {
            // Arrange
            Long authorId = 1L;
            when(authorRepository.findAuthorById(authorId)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> authorService.findAuthorById(authorId));
            verify(authorRepository).findAuthorById(authorId);
        }
    }
}
