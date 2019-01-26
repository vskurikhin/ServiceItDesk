/*
 * StatusDaoJpaTest.java
 * This file was last modified at 2019-01-26 18:15 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.models.Status;
import su.svn.utils.db.JpaDedicatedEntityManagerTest;
import su.svn.utils.logging.TestAppender;

import javax.persistence.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.TestData.*;
import static su.svn.db.StatusDaoJpa.*;

@DisplayName("Class StatusDaoJpaTest")
class StatusDaoJpaTest
{
    StatusDaoJpa dao;

    @Test
    @DisplayName("is instantiated with new StatusDaoJpa()")
    void isInstantiatedWithNew()
    {
        new StatusDaoJpa();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            dao = new StatusDaoJpa();
        }

        @Test
        @DisplayName("default values in StatusDaoJpa()")
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
            dao = new StatusDaoJpa(entityManager);
            appender = TestAppender.create();
        }

        @Test
        @DisplayName("constructor injected values in StatusDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id with success")
        @Test
        void findById_success()
        {
            Status expected = new Status();
            expected.setId(TEST_ID1);
            expected.setStatus(TEST_STATUS);
            expected.setDescription(TEST_DESCRIPTION);

            when(entityManager.find(Status.class, TEST_ID1)).thenReturn(expected);

            Status test = dao.findById(TEST_ID1);
            assertEquals(expected, test);
        }

        @DisplayName("find by id return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Status.class, TEST_ID9)).thenReturn(null);

            Status test = dao.findById(TEST_ID9);
            assertNull(test);
        }

        @DisplayName("find by id was an IllegalArgumentException")
        @Test
        void findById_exception()
        {
            when(entityManager.find(Status.class, TEST_ID9)).thenThrow(IllegalArgumentException.class);

            Status test = dao.findById(TEST_ID9);
            assertNull(test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("and find all in empty table")
        @Test
        void findAll_empty()
        {
            List<Status> expected = Collections.emptyList();
            TypedQuery<Status> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_ALL, Status.class)).thenReturn(mockedQuery);

            List<Status> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find all was an IllegalArgumentException")
        @Test
        void findAll_exception()
        {
            List<Status> expected = Collections.emptyList();
            TypedQuery<Status> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_ALL, Status.class)).thenReturn(mockedQuery);

            List<Status> test = dao.findAll();
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by status in empty table")
        @Test
        void findByName_empty()
        {
            List<Status> expected = new LinkedList<>();
            TypedQuery<Status> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("status", TEST_STATUS)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_WHERE_STATUS, Status.class)).thenReturn(mockedQuery);

            List<Status> test = dao.findByStatus(TEST_STATUS);
            assertEquals(expected, test);
        }

        @DisplayName("find by status was an IllegalArgumentException")
        @Test
        void findByName_exception()
        {
            List<Status> expected = Collections.emptyList();
            TypedQuery<Status> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("status", TEST_STATUS)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_WHERE_STATUS, Status.class)).thenReturn(mockedQuery);

            List<Status> test = dao.findByStatus(TEST_STATUS);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by description in empty table")
        @Test
        void findByDescription_empty()
        {
            List<Status> expected = new LinkedList<>();
            TypedQuery<Status> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_WHERE_DESC, Status.class)).thenReturn(mockedQuery);

            List<Status> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
        }

        @DisplayName("find by description was an IllegalArgumentException")
        @Test
        void findByDescription_exception()
        {
            List<Status> expected = Collections.emptyList();
            TypedQuery<Status> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_WHERE_DESC, Status.class)).thenReturn(mockedQuery);

            List<Status> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with persist entity was a PersistenceException")
        @Test
        void save_persist_exception()
        {
            Status expected = new Status();
            doThrow(PersistenceException.class).when(entityManager).persist(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with flush was a PersistenceException")
        @Test
        void save_flush_exception()
        {
            doThrow(PersistenceException.class).when(entityManager).flush();

            Status expected = new Status();
            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with merge was a PersistenceException")
        @Test
        void save_merge_exception()
        {
            Status expected = TEST_STATUS1;
            doThrow(PersistenceException.class).when(entityManager).merge(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("delete was a PersistenceException")
        @Test
        void delete_exception()
        {
            Status expected = TEST_STATUS1;
            when(entityManager.find(Status.class, expected.getId())).thenReturn(expected);
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
            dao = new StatusDaoJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            Status test = new Status();
            test.setStatus(TEST_STATUS);
            test.setDescription(TEST_DESCRIPTION);
            runInTransaction(() -> dao.save(test));
            assertEquals(test, dao.findById(test.getId()));
        }

        @DisplayName("merge the detached object when save")
        @Test
        void save_megre()
        {
            Status test = new Status();
            test.setStatus(TEST_STATUS);
            test.setDescription(TEST_DESCRIPTION);
            runInTransaction(() -> dao.save(test));
            test.setStatus(TEST_STATUS + TEST_STR1);
            test.setDescription(TEST_DESCRIPTION + TEST_STR2);
            runInTransaction(() -> dao.save(test));
            assertEquals(1, dao.findAll().size());
            assertTrue(dao.findByStatus(test.getStatus()).contains(test));
        }

        @Test
        void delete()
        {
            Status test = new Status();
            test.setStatus(TEST_STATUS);
            test.setDescription(TEST_DESCRIPTION);
            runInTransaction(() -> dao.save(test));
            runInTransaction(() -> dao.delete(test.getId()));
            assertNull(dao.findById(test.getId()));
            assertTrue(dao.findAll().isEmpty());
        }
    }
}