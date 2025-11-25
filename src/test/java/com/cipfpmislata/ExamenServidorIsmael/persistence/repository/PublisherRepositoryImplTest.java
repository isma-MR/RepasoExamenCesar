package com.cipfpmislata.ExamenServidorIsmael.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.exception.ResourceNotFoundException;
import com.cipfpmislata.ExamenServidorIsmael.mapper.PublisherMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.PublisherJpaEntity;

@ExtendWith(MockitoExtension.class)
public class PublisherRepositoryImplTest {

    @Mock
    private PublisherJpaDao publisherJpaDao;

    @InjectMocks
    private PublisherRepositoryImpl publisherRepositoryImpl;

    @Nested
    class findAll {
        @Test
        @DisplayName("findAll should return a list of publishers")
        void findAll_ShouldReturnListOfPublishers_WhenPublishersExist() {
            List<Publisher> publisherList = List.of(new Publisher(1L, "Publisher 1", "Slug 1"),
                    new Publisher(2L, "Publisher 2", "Slug 2"));

            List<PublisherDto> publisherDtoList = publisherList.stream()
                    .map(PublisherMapper.getInstance()::fromPublisherToPublisherDto).toList();
            List<PublisherJpaEntity> publisherJpaEntityList = publisherDtoList.stream()
                    .map(PublisherMapper.getInstance()::fromPublisherDtoToPublisherJpaEntity).toList();
            when(publisherJpaDao.findAll()).thenReturn(publisherJpaEntityList);

            List<PublisherDto> result = publisherRepositoryImpl.findAll();

            assertEquals(publisherDtoList, result);
            verify(publisherJpaDao).findAll();
        }

        @Test
        @DisplayName("findAll should return an empty list when no publishers exist")
        void findAll_ShouldReturnEmptyList_WhenNoPublishersExist() {
            when(publisherJpaDao.findAll()).thenReturn(List.of());

            List<PublisherDto> result = publisherRepositoryImpl.findAll();

            assertTrue(result.isEmpty());
            verify(publisherJpaDao).findAll();
        }
    }

    @Nested
    class findById {
        @Test
        @DisplayName("findById should return a publisher")
        void findById_ShouldReturnPublisher_WhenPublisherExists() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);
            PublisherJpaEntity expectedPublisherJpaEntity = PublisherMapper.getInstance()
                    .fromPublisherDtoToPublisherJpaEntity(expectedPublisherDto);

            // Act
            when(publisherJpaDao.findPublisherById(expectedPublisher.getId()))
                    .thenReturn(Optional.of(expectedPublisherJpaEntity));

            // Assert
            Optional<PublisherDto> result = publisherRepositoryImpl.findById(expectedPublisher.getId());

            assertEquals(expectedPublisherDto, result.get());
            verify(publisherJpaDao).findPublisherById(expectedPublisher.getId());
        }

        @Test
        @DisplayName("findById should return an empty optional when publisher does not exist")
        void findById_ShouldReturnEmptyOptional_WhenPublisherDoesNotExist() {
            // Arrange
            Long id = 1L;
            when(publisherJpaDao.findPublisherById(id)).thenReturn(Optional.empty());

            // Act
            Optional<PublisherDto> result = publisherRepositoryImpl.findById(id);

            // Assert
            assertTrue(result.isEmpty());
            verify(publisherJpaDao).findPublisherById(id);
        }
    }

    @Nested
    class findBySlug {
        @Test
        @DisplayName("findBySlug should return a publisher")
        void findBySlug_ShouldReturnPublisher_WhenPublisherExists() {
            // Arrange
            Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "Slug 1");
            PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                    .fromPublisherToPublisherDto(expectedPublisher);
            PublisherJpaEntity expectedPublisherJpaEntity = PublisherMapper.getInstance()
                    .fromPublisherDtoToPublisherJpaEntity(expectedPublisherDto);

            when(publisherJpaDao.findPublisherBySlug(expectedPublisher.getSlug()))
                    .thenReturn(Optional.of(expectedPublisherJpaEntity));

            // Act
            Optional<PublisherDto> result = publisherRepositoryImpl.findBySlug(expectedPublisher.getSlug());

            // Assert
            assertEquals(expectedPublisherDto, result.get());
            verify(publisherJpaDao).findPublisherBySlug(expectedPublisher.getSlug());
        }

        @Test
        @DisplayName("findBySlug should return an empty optional when publisher does not exist")
        void findBySlug_ShouldReturnEmptyOptional_WhenPublisherDoesNotExist() {
            // Arrange
            String slug = "Slug 1";
            when(publisherJpaDao.findPublisherBySlug(slug)).thenReturn(Optional.empty());

            // Act
            Optional<PublisherDto> result = publisherRepositoryImpl.findBySlug(slug);

            // Assert
            assertTrue(result.isEmpty());
            verify(publisherJpaDao).findPublisherBySlug(slug);
        }
    }

    @Nested
    class save {
        @Test
        @DisplayName("save should insert a new publisher")
        void save_ShouldInsertNewPublisher_WhenPublisherDoesNotExist() {

            // Arrange
            Publisher publisher = new Publisher(null, "Publisher 1", "Slug 1");
            PublisherDto publisherDto = PublisherMapper.getInstance().fromPublisherToPublisherDto(publisher);
            PublisherJpaEntity publisherJpaEntity = PublisherMapper.getInstance()
                    .fromPublisherDtoToPublisherJpaEntity(publisherDto);
            when(publisherJpaDao.insert(publisherJpaEntity)).thenReturn(publisherJpaEntity);

            // Act
            PublisherDto result = publisherRepositoryImpl.save(publisherDto);

            // Assert
            assertAll(
                    () -> assertEquals(publisherDto.id(), result.id()),
                    () -> assertEquals(publisherDto.name(), result.name()),
                    () -> assertEquals(publisherDto.slug(), result.slug()));
            verify(publisherJpaDao).insert(publisherJpaEntity);
        }

        @Test
        @DisplayName("save should update an existing publisher")
        void save_ShouldUpdateExistingPublisher_WhenPublisherExists() {
            // Arrange
            PublisherDto publisherDto = new PublisherDto(1L, "Publisher 1", "Slug 1");
            PublisherJpaEntity publisherJpaEntity = PublisherMapper.getInstance()
                    .fromPublisherDtoToPublisherJpaEntity(publisherDto);
            when(publisherJpaDao.update(publisherJpaEntity)).thenReturn(publisherJpaEntity);

            // Act
            PublisherDto result = publisherRepositoryImpl.save(publisherDto);

            // Assert
            assertEquals(publisherDto, result);
            verify(publisherJpaDao).update(publisherJpaEntity);
        }
    }

    @Nested
    class delete {
        @Test
        @DisplayName("delete should delete a publisher")
        void delete_ShouldDeletePublisher_WhenPublisherExists() {

            // Arrange
            Long id = 1L;

            // Act
            publisherRepositoryImpl.delete(id);

            // Assert
            verify(publisherJpaDao).delete(id);
        }
    }
}
