/*
 * ConfigurationTypeDaoJpaTest.java
 * This file was last modified at 2019-01-26 18:17 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.models.ConfigurationType;
import su.svn.utils.db.JpaDedicatedEntityManagerTest;
import su.svn.utils.logging.TestAppender;

import javax.persistence.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.TestData.*;
import static su.svn.models.ConfigurationType.FIND_ALL;
import static su.svn.models.ConfigurationType.FIND_ALL_WHERE_DESC;
import static su.svn.models.ConfigurationType.FIND_ALL_WHERE_NAME;

@DisplayName("Class ConfigurationTypeDaoJpaTest")
class ConfigurationTypeDaoJpaTest
{
    ConfigurationTypeDaoJpa dao;

    @Test
    @DisplayName("is instantiated with new ConfigurationTypeDaoJpa()")
    void isInstantiatedWithNew()
    {
        new ConfigurationTypeDaoJpa();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            dao = new ConfigurationTypeDaoJpa();
        }

        @Test
        @DisplayName("default values in ConfigurationTypeDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", null);
        }

        @Test
        @DisplayName("use setter and getter for EntityManager")
        void testGetSetEntityManager()
        {
            EntityManager entityManager = mock(EntityManager.class);
            dao.setEntityManager(entityManager);
            assertEquals(entityManager, dao.getEntityManager());
        }
    }

    @Nested
    @DisplayName("when mock EntityManager")
    class WhenMock
    {
        private EntityManager entityManager;

        private TestAppender appender;

        @BeforeEach
        void mockEntityManager()
        {
            entityManager = mock(EntityManager.class);
            dao = new ConfigurationTypeDaoJpa(entityManager);
            appender = TestAppender.create();
        }

        @Test
        @DisplayName("constructor injected values in ConfigurationTypeDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id with success")
        @Test
        void findById_success()
        {
            ConfigurationType expected = new ConfigurationType();
            expected.setId(TEST_ID1);
            expected.setName(TEST_NAME);
            expected.setDescription(TEST_DESCRIPTION);

            when(entityManager.find(ConfigurationType.class, TEST_ID1)).thenReturn(expected);

            Optional<ConfigurationType> test = dao.findById(TEST_ID1);
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("find by id return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(ConfigurationType.class, TEST_ID9)).thenReturn(null);

            Optional<ConfigurationType> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
        }

        @DisplayName("find by id was an IllegalArgumentException")
        @Test
        void findById_exception()
        {
            when(entityManager.find(ConfigurationType.class, TEST_ID9)).thenThrow(IllegalArgumentException.class);

            Optional<ConfigurationType> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("and find all in empty table")
        @Test
        void findAll_empty()
        {
            List<ConfigurationType> expected = Collections.emptyList();
            TypedQuery<ConfigurationType> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL, ConfigurationType.class)).thenReturn(mockedQuery);

            List<ConfigurationType> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find all was an IllegalArgumentException")
        @Test
        void findAll_exception()
        {
            List<ConfigurationType> expected = Collections.emptyList();
            TypedQuery<ConfigurationType> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL, ConfigurationType.class)).thenReturn(mockedQuery);

            List<ConfigurationType> test = dao.findAll();
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by name in empty table")
        @Test
        void findByName_empty()
        {
            List<ConfigurationType> expected = new LinkedList<>();
            TypedQuery<ConfigurationType> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("name", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_NAME, ConfigurationType.class)).thenReturn(mockedQuery);

            List<ConfigurationType> test = dao.findByName(TEST_NAME);
            assertEquals(expected, test);
        }

        @DisplayName("find by name was an IllegalArgumentException")
        @Test
        void findByName_exception()
        {
            List<ConfigurationType> expected = Collections.emptyList();
            TypedQuery<ConfigurationType> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("name", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_NAME, ConfigurationType.class)).thenReturn(mockedQuery);

            List<ConfigurationType> test = dao.findByName(TEST_NAME);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by description in empty table")
        @Test
        void findByDescription_empty()
        {
            List<ConfigurationType> expected = new LinkedList<>();
            TypedQuery<ConfigurationType> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_DESC, ConfigurationType.class)).thenReturn(mockedQuery);

            List<ConfigurationType> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
        }

        @DisplayName("find by description was an IllegalArgumentException")
        @Test
        void findByDescription_exception()
        {
            List<ConfigurationType> expected = Collections.emptyList();
            TypedQuery<ConfigurationType> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_DESC, ConfigurationType.class)).thenReturn(mockedQuery);

            List<ConfigurationType> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with persist entity was a PersistenceException")
        @Test
        void save_persist_exception()
        {
            ConfigurationType expected = new ConfigurationType();
            doThrow(PersistenceException.class).when(entityManager).persist(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with flush was a PersistenceException")
        @Test
        void save_flush_exception()
        {
            doThrow(PersistenceException.class).when(entityManager).flush();

            ConfigurationType expected = new ConfigurationType();
            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with merge was a PersistenceException")
        @Test
        void save_merge_exception()
        {
            ConfigurationType expected = TEST_CONFIGURATION_TYPE1;
            doThrow(PersistenceException.class).when(entityManager).merge(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("delete was a PersistenceException")
        @Test
        void delete_exception()
        {
            ConfigurationType expected = TEST_CONFIGURATION_TYPE1;
            when(entityManager.find(ConfigurationType.class, expected.getId())).thenReturn(expected);
            when(entityManager.merge(expected)).thenReturn(expected);
            doThrow(PersistenceException.class).when(entityManager).remove(expected);

            assertFalse(dao.delete(expected.getId()));
            assertTrue(appender.getMessages().size() > 0);
        }
    }

    @Nested
    @DisplayName("JPA H2 create/update tests")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            dao = new ConfigurationTypeDaoJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            ConfigurationType expected = new ConfigurationType();
            expected.setName(TEST_NAME);
            expected.setDescription(TEST_DESCRIPTION);
            runInTransaction(() -> dao.save(expected));
            Optional<ConfigurationType> test = dao.findById(expected.getId());
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("merge the detached object when save")
        @Test
        void save_megre()
        {
            ConfigurationType test = new ConfigurationType();
            test.setName(TEST_NAME);
            test.setDescription(TEST_DESCRIPTION);
            runInTransaction(() -> dao.save(test));
            test.setName(TEST_NAME + TEST_STR1);
            test.setDescription(TEST_DESCRIPTION + TEST_STR2);
            runInTransaction(() -> dao.save(test));
            assertEquals(1, dao.findAll().size());
            assertTrue(dao.findByName(test.getName()).contains(test));
        }

        @Test
        void delete()
        {
            ConfigurationType test = new ConfigurationType();
            test.setName(TEST_NAME);
            test.setDescription(TEST_DESCRIPTION);
            runInTransaction(() -> dao.save(test));
            runInTransaction(() -> dao.delete(test.getId()));
            Optional<ConfigurationType> none = dao.findById(test.getId());
            assertFalse(none.isPresent());
            assertTrue(dao.findAll().isEmpty());
        }
    }
}