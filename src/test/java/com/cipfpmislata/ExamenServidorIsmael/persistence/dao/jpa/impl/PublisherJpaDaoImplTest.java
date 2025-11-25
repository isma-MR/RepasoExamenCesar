package com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import com.cipfpmislata.ExamenServidorIsmael.domain.model.Publisher;
import com.cipfpmislata.ExamenServidorIsmael.domain.service.dto.PublisherDto;
import com.cipfpmislata.ExamenServidorIsmael.mapper.PublisherMapper;
import com.cipfpmislata.ExamenServidorIsmael.persistence.TestConfig;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.PublisherJpaDao;
import com.cipfpmislata.ExamenServidorIsmael.persistence.dao.jpa.entity.PublisherJpaEntity;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PublisherJpaDaoImplTest {

        @Autowired
        private PublisherJpaDao publisherJpaDao;

        @Nested
        class TestFindAll {
                @Test
                @DisplayName("Test findAll should return list of publishers when publishers exist")
                void testFindAll_ShouldReturnListOfPublishers_WhenPublishersExist() {
                        // Arrange
                        List<Publisher> expectedPublishers = List.of(
                                        new Publisher(1L, "Publisher 1", "publisher-1"),
                                        new Publisher(2L, "Publisher 2", "publisher-2"));
                        List<PublisherDto> expectedPublishersDto = expectedPublishers.stream()
                                        .map(PublisherMapper.getInstance()::fromPublisherToPublisherDto).toList();
                        List<PublisherJpaEntity> expectedPublishersJpaEntity = expectedPublishersDto.stream()
                                        .map(PublisherMapper.getInstance()::fromPublisherDtoToPublisherJpaEntity)
                                        .toList();
                        // Act
                        List<PublisherJpaEntity> actualPublishers = publisherJpaDao.findAll();

                        // Assert
                        assertAll(
                                        () -> assertEquals(expectedPublishersJpaEntity.get(0).getId(),
                                                        actualPublishers.get(0).getId()),
                                        () -> assertEquals(expectedPublishersJpaEntity.get(0).getName(),
                                                        actualPublishers.get(0).getName()),
                                        () -> assertEquals(expectedPublishersJpaEntity.get(0).getSlug(),
                                                        actualPublishers.get(0).getSlug()),
                                        () -> assertEquals(expectedPublishersJpaEntity.get(1).getId(),
                                                        actualPublishers.get(1).getId()),
                                        () -> assertEquals(expectedPublishersJpaEntity.get(1).getName(),
                                                        actualPublishers.get(1).getName()),
                                        () -> assertEquals(expectedPublishersJpaEntity.get(1).getSlug(),
                                                        actualPublishers.get(1).getSlug()));
                }
        }

        @Nested
        class TestFindById {
                @Test
                @DisplayName("Test findById should return publisher when publisher exists")
                void testFindById_ShouldReturnPublisher_WhenPublisherExists() {
                        // Arrange
                        Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "publisher-1");
                        PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                                        .fromPublisherToPublisherDto(expectedPublisher);
                        PublisherJpaEntity expectedPublisherJpaEntity = PublisherMapper.getInstance()
                                        .fromPublisherDtoToPublisherJpaEntity(
                                                        expectedPublisherDto);
                        // Act
                        Optional<PublisherJpaEntity> actualPublisher = publisherJpaDao.findPublisherById(1L);

                        // Assert
                        assertTrue(actualPublisher.isPresent());
                        assertAll(
                                        () -> assertEquals(expectedPublisherJpaEntity.getId(),
                                                        actualPublisher.get().getId()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getName(),
                                                        actualPublisher.get().getName()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getSlug(),
                                                        actualPublisher.get().getSlug()));
                }
        }

        @Nested
        class TestFindBySlug {
                @Test
                @DisplayName("Test findBySlug should return publisher when publisher exists")
                void testFindBySlug_ShouldReturnPublisher_WhenPublisherExists() {
                        // Arrange
                        Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "publisher-1");
                        PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                                        .fromPublisherToPublisherDto(expectedPublisher);
                        PublisherJpaEntity expectedPublisherJpaEntity = PublisherMapper.getInstance()
                                        .fromPublisherDtoToPublisherJpaEntity(
                                                        expectedPublisherDto);
                        // Act
                        Optional<PublisherJpaEntity> actualPublisher = publisherJpaDao
                                        .findPublisherBySlug("publisher-1");

                        // Assert
                        assertTrue(actualPublisher.isPresent());
                        assertAll(
                                        () -> assertEquals(expectedPublisherJpaEntity.getId(),
                                                        actualPublisher.get().getId()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getName(),
                                                        actualPublisher.get().getName()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getSlug(),
                                                        actualPublisher.get().getSlug()));
                }
        }

        @Nested
        class TestInsert {
                @Test
                @DisplayName("Test insert should return publisher when publisher is inserted")
                void testInsert_ShouldReturnPublisher_WhenPublisherIsInserted() {
                        // Arrange
                        Publisher newPublisher = new Publisher(null, "New Publisher", "new-publisher");
                        PublisherDto newPublisherDto = PublisherMapper.getInstance()
                                        .fromPublisherToPublisherDto(newPublisher);
                        PublisherJpaEntity newPublisherJpaEntity = PublisherMapper.getInstance()
                                        .fromPublisherDtoToPublisherJpaEntity(
                                                        newPublisherDto);
                        // Act
                        int totalPublishersBeforeInsert = publisherJpaDao.findAll().size();
                        PublisherJpaEntity actualPublisher = publisherJpaDao.insert(newPublisherJpaEntity);
                        int totalPublishersAfterInsert = publisherJpaDao.findAll().size();

                        // Assert
                        assertAll(
                                        () -> assertTrue(actualPublisher.getId() > 0),
                                        () -> assertEquals(newPublisherJpaEntity.getName(), actualPublisher.getName()),
                                        () -> assertEquals(newPublisherJpaEntity.getSlug(), actualPublisher.getSlug()),
                                        () -> assertEquals(totalPublishersBeforeInsert + 1,
                                                        totalPublishersAfterInsert));
                }
        }

        @Nested
        class TestUpdate {
                @Test
                @DisplayName("Test update should return publisher when publisher is updated")
                void testUpdate_ShouldReturnPublisher_WhenPublisherIsUpdated() {
                        // Arrange
                        Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "publisher-1");
                        PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                                        .fromPublisherToPublisherDto(expectedPublisher);
                        PublisherJpaEntity expectedPublisherJpaEntity = PublisherMapper.getInstance()
                                        .fromPublisherDtoToPublisherJpaEntity(
                                                        expectedPublisherDto);
                        // Act
                        PublisherJpaEntity actualPublisher = publisherJpaDao.update(expectedPublisherJpaEntity);

                        // Assert
                        assertAll(
                                        () -> assertEquals(expectedPublisherJpaEntity.getId(), actualPublisher.getId()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getName(),
                                                        actualPublisher.getName()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getSlug(),
                                                        actualPublisher.getSlug()));
                }
        }

        @Autowired
        private jakarta.persistence.EntityManager entityManager;

        @Nested
        class TestDelete {
                @Test
                @DisplayName("Test delete should return void when publisher is deleted")
                void testDelete_ShouldReturnVoid_WhenPublisherIsDeleted() {
                        // Arrange
                        Publisher expectedPublisher = new Publisher(1L, "Publisher 1", "publisher-1");
                        PublisherDto expectedPublisherDto = PublisherMapper.getInstance()
                                        .fromPublisherToPublisherDto(expectedPublisher);
                        PublisherJpaEntity expectedPublisherJpaEntity = PublisherMapper.getInstance()
                                        .fromPublisherDtoToPublisherJpaEntity(
                                                        expectedPublisherDto);

                        // Act
                        int totalPublishersBeforeDelete = publisherJpaDao.findAll().size();
                        publisherJpaDao.delete(1L);
                        int totalPublishersAfterDelete = publisherJpaDao.findAll().size();

                        // Assert
                        assertAll(
                                        () -> assertEquals(expectedPublisherJpaEntity.getId(),
                                                        expectedPublisher.getId()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getName(),
                                                        expectedPublisher.getName()),
                                        () -> assertEquals(expectedPublisherJpaEntity.getSlug(),
                                                        expectedPublisher.getSlug()),
                                        () -> assertEquals(totalPublishersBeforeDelete - 1,
                                                        totalPublishersAfterDelete));
                }
        }
}
