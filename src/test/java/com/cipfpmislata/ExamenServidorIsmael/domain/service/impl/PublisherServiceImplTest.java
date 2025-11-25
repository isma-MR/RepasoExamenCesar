package com.cipfpmislata.ExamenServidorIsmael.domain.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.repository.PublisherRepository;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.BusinessException;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;
import com.cipfpmislata.ExamenServidorIsmael.mapper.PublisherMapper;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceImplTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    @Nested
    class TestFindAll {
        @Test
        @DisplayName("Test findAll should return list of publishers when publishers exist")
        void testFindAll_ShouldReturnListOfPublishers_WhenPublishersExist() {
            // Arrange
            List<Publisher> expectedPublishers = List.of(
                    new Publisher(1L, "Publisher 1", "Slug 1"),
                    new Publisher(2L, "Publisher 2", "Slug 2"));
            List<PublisherDto> expectedPublishersDto = expectedPublishers.stream()
                    .map(PublisherMapper.getInstance()::fromPublisherToPublisherDto).toList();

            when(publisherRepository.findAll()).thenReturn(expectedPublishersDto);

            // Act
            List<PublisherDto> actualPublishers = publisherService.findAll();

            // Assert
            assertAll(
                    () -> assertEquals(expectedPublishersDto.get(0).id(), actualPublishers.get(0).id()),
                    () -> assertEquals(expectedPublishersDto.get(0).name(), actualPublishers.get(0).name()),
                    () -> assertEquals(expectedPublishersDto.get(0).slug(), actualPublishers.get(0).slug()),
                    () -> assertEquals(expectedPublishersDto.get(1).id(), actualPublishers.get(1).id()),
                    () -> assertEquals(expectedPublishersDto.get(1).name(), actualPublishers.get(1).name()),
                    () -> assertEquals(expectedPublishersDto.get(1).slug(), actualPublishers.get(1).slug()));
            verify(publisherRepository).findAll();
        }

        @Test
        @DisplayName("Test findAll should return empty list when no publishers exist")
        void testFindAll_ShouldReturnEmptyList_WhenNoPublishersExist() {
            // Arrange
            when(publisherRepository.findAll()).thenReturn(List.of());

            // Act
            List<PublisherDto> actualPublishers = publisherService.findAll();

            // Assert
            assertTrue(actualPublishers.isEmpty());
            verify(publisherRepository).findAll();
        }
    }

    @Nested
    class TestFindBySlug {
        @Test
        @DisplayName("Test findBySlug should return publisher when publisher exists")
        void testFindBySlug_ShouldReturnPublisher_WhenPublisherExists() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.findBySlug(expectedPublisher.getSlug()))
                    .thenReturn(Optional.of(expectedPublisherDto));

            // Act
            Optional<PublisherDto> actualPublisher = publisherService.findBySlug(expectedPublisher.getSlug());

            // Assert
            assertAll(
                    () -> assertEquals(expectedPublisherDto.id(), actualPublisher.get().id()),
                    () -> assertEquals(expectedPublisherDto.name(), actualPublisher.get().name()),
                    () -> assertEquals(expectedPublisherDto.slug(), actualPublisher.get().slug()));
            verify(publisherRepository).findBySlug(expectedPublisher.getSlug());
        }

        @Test
        @DisplayName("Test findBySlug should return empty optional when publisher does not exist")
        void testFindBySlug_ShouldReturnEmptyOptional_WhenPublisherDoesNotExist() {
            // Arrange
            String slug = "Slug 1";
            when(publisherRepository.findBySlug(slug)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> publisherService.findBySlug(slug));
            verify(publisherRepository).findBySlug(slug);
        }
    }

    @Nested
    class TestFindById {
        @Test
        @DisplayName("Test findById should return publisher when publisher is found")
        void testFindById_ShouldReturnPublisher_WhenPublisherIsFound() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.findById(expectedPublisher.getId()))
                    .thenReturn(Optional.of(expectedPublisherDto));

            // Act
            Optional<PublisherDto> actualPublisher = publisherService.findById(expectedPublisher.getId());

            // Assert
            assertAll(
                    () -> assertEquals(expectedPublisherDto.id(), actualPublisher.get().id()),
                    () -> assertEquals(expectedPublisherDto.name(), actualPublisher.get().name()),
                    () -> assertEquals(expectedPublisherDto.slug(), actualPublisher.get().slug()));
            verify(publisherRepository).findById(expectedPublisher.getId());
        }

        @Test
        @DisplayName("Test findById should throw exception when publisher does not exist")
        void testFindById_ShouldThrowException_WhenPublisherDoesNotExist() {
            // Arrange
            Long id = 1L;
            when(publisherRepository.findById(id)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> publisherService.findById(id));
            verify(publisherRepository).findById(id);
        }
    }

    @Nested
    class TestCreate {
        @Test
        @DisplayName("Test create should return publisher when publisher is created")
        void testCreate_ShouldReturnPublisher_WhenPublisherIsCreated() {
            // Arrange

            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.save(expectedPublisherDto)).thenReturn(expectedPublisherDto);

            // Act
            PublisherDto actualPublisherDto = publisherService.create(expectedPublisherDto);

            // Assert
            assertEquals(expectedPublisherDto, actualPublisherDto);
            verify(publisherRepository).save(expectedPublisherDto);
        }

        @Test
        @DisplayName("Test create should throw BusinessException when publisher already exists")
        void testCreate_ShouldThrowBusinessException_WhenPublisherAlreadyExists() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.findBySlug(expectedPublisher.getSlug()))
                    .thenReturn(Optional.of(expectedPublisherDto));

            // Act & Assert
            assertThrows(BusinessException.class, () -> publisherService.create(expectedPublisherDto));
            verify(publisherRepository).findBySlug(expectedPublisher.getSlug());
        }
    }

    @Nested
    class TestUpdate {
        @Test
        @DisplayName("Test update should return publisher when publisher is updated")
        void testUpdate_ShouldReturnPublisher_WhenPublisherIsUpdated() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.findBySlug(expectedPublisher.getSlug()))
                    .thenReturn(Optional.of(expectedPublisherDto));
            when(publisherRepository.save(expectedPublisherDto)).thenReturn(expectedPublisherDto);

            // Act
            PublisherDto actualPublisherDto = publisherService.update(expectedPublisherDto);

            // Assert
            assertAll(
                    () -> assertEquals(expectedPublisherDto.id(), actualPublisherDto.id()),
                    () -> assertEquals(expectedPublisherDto.name(), actualPublisherDto.name()),
                    () -> assertEquals(expectedPublisherDto.slug(), actualPublisherDto.slug()));
            verify(publisherRepository).save(expectedPublisherDto);
        }

        @Test
        @DisplayName("Test update should throw exception when publisher does not exist")
        void testUpdate_ShouldThrowException_WhenPublisherDoesNotExist() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.findBySlug(expectedPublisher.getSlug())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> publisherService.update(expectedPublisherDto));
            verify(publisherRepository).findBySlug(expectedPublisher.getSlug());
        }
    }

    @Nested
    class TestDelete {
        @Test
        @DisplayName("Test delete should return publisher when publisher is deleted")
        void testDelete_ShouldReturnPublisher_WhenPublisherIsDeleted() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.findById(expectedPublisher.getId()))
                    .thenReturn(Optional.of(expectedPublisherDto));

            // Act
            publisherService.delete(expectedPublisherDto.id());

            // Assert
            verify(publisherRepository).delete(expectedPublisherDto.id());
        }

        @Test
        @DisplayName("Test delete should throw exception when publisher does not exist")
        void testDelete_ShouldThrowException_WhenPublisherDoesNotExist() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);

            when(publisherRepository.findById(expectedPublisher.getId())).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(ResourceNotFoundException.class, () -> publisherService.delete(expectedPublisherDto.id()));
            verify(publisherRepository).findById(expectedPublisher.getId());
        }
    }
}
