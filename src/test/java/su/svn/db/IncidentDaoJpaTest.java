/*
 * IncidentDaoJpaTest.java
 * This file was last modified at 2019-02-03 10:01 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.models.Incident;
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
import static su.svn.models.Incident.FIND_ALL;
import static su.svn.models.Incident.FIND_ALL_WHERE_DESC;
import static su.svn.models.Incident.FIND_ALL_WHERE_TITLE;
//TODO
//import static su.svn.db.IncidentDaoJpa.SELECT_ALL;
//import static su.svn.db.IncidentDaoJpa.SELECT_WHERE_DESC;
//import static su.svn.db.IncidentDaoJpa.SELECT_WHERE_NAME;

@DisplayName("Class IncidentDaoJpaTest")
class IncidentDaoJpaTest
{
    IncidentDaoJpa dao;

    @Test
    @DisplayName("is instantiated with new IncidentDaoJpa()")
    void isInstantiatedWithNew()
    {
        new IncidentDaoJpa();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            dao = new IncidentDaoJpa();
        }

        @Test
        @DisplayName("default values in IncidentDaoJpa()")
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
            dao = new IncidentDaoJpa(entityManager);
            appender = TestAppender.create();
        }

        @Test
        @DisplayName("constructor injected values in IncidentDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id with success")
        @Test
        void findById_success()
        {
            Incident expected = new Incident();
            expected.setId(TEST_ID1);
            expected.setTitle(TEST_TITLE);
            expected.setDescription(TEST_DESCRIPTION);

            when(entityManager.find(Incident.class, TEST_ID1)).thenReturn(expected);

            Optional<Incident> test = dao.findById(TEST_ID1);
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("find by id return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Incident.class, TEST_ID9)).thenReturn(null);

            Optional<Incident> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
        }

        @DisplayName("find by id was an IllegalArgumentException")
        @Test
        void findById_exception()
        {
            when(entityManager.find(Incident.class, TEST_ID9)).thenThrow(IllegalArgumentException.class);

            Optional<Incident> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("and find all in empty table")
        @Test
        void findAll_empty()
        {
            List<Incident> expected = Collections.emptyList();
            TypedQuery<Incident> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL, Incident.class)).thenReturn(mockedQuery);

            List<Incident> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find all was an IllegalArgumentException")
        @Test
        void findAll_exception()
        {
            List<Incident> expected = Collections.emptyList();
            TypedQuery<Incident> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL, Incident.class)).thenReturn(mockedQuery);

            List<Incident> test = dao.findAll();
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by name in empty table")
        @Test
        void findByName_empty()
        {
            List<Incident> expected = new LinkedList<>();
            TypedQuery<Incident> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("title", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_TITLE, Incident.class)).thenReturn(mockedQuery);

            List<Incident> test = dao.findByTitle(TEST_NAME);
            assertEquals(expected, test);
        }

        @DisplayName("find by name was an IllegalArgumentException")
        @Test
        void findByName_exception()
        {
            List<Incident> expected = Collections.emptyList();
            TypedQuery<Incident> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("title", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_TITLE, Incident.class)).thenReturn(mockedQuery);

            List<Incident> test = dao.findByTitle(TEST_NAME);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by description in empty table")
        @Test
        void findByDescription_empty()
        {
            List<Incident> expected = new LinkedList<>();
            TypedQuery<Incident> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_DESC, Incident.class)).thenReturn(mockedQuery);

            List<Incident> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
        }

        @DisplayName("find by description was an IllegalArgumentException")
        @Test
        void findByDescription_exception()
        {
            List<Incident> expected = Collections.emptyList();
            TypedQuery<Incident> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_DESC, Incident.class)).thenReturn(mockedQuery);

            List<Incident> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with persist entity was a PersistenceException")
        @Test
        void save_persist_exception()
        {
            Incident expected = new Incident();
            doThrow(PersistenceException.class).when(entityManager).persist(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with flush was a PersistenceException")
        @Test
        void save_flush_exception()
        {
            doThrow(PersistenceException.class).when(entityManager).flush();

            Incident expected = new Incident();
            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with merge was a PersistenceException")
        @Test
        void save_merge_exception()
        {
            Incident expected = TEST_INCIDENT1;
            doThrow(PersistenceException.class).when(entityManager).merge(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("delete was a PersistenceException")
        @Test
        void delete_exception()
        {
            Incident expected = TEST_INCIDENT1;
            when(entityManager.find(Incident.class, expected.getId())).thenReturn(expected);
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
            dao = new IncidentDaoJpa(entityManager);
        }

        @SuppressWarnings("Duplicates")
        private void saveNewTask(Incident test)
        {
            test.setId(0L);
            test.getConsumer().setId(0L);
            test.getConsumer().getGroup().setId(0L);
            runInTransaction(() -> {
                PrimaryGroupDao groupDao = new PrimaryGroupDaoJpa(entityManager);
                groupDao.save(test.getConsumer().getGroup());

                UserDao userDao = new UserDaoJpa(entityManager);
                userDao.save(test.getConsumer());

                StatusDao statusDao = new StatusDaoJpa(entityManager);
                statusDao.save(test.getStatus());

                dao.save(test);
            });
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            Incident expected = createIncident1();
            saveNewTask(expected);
            Optional<Incident> test = dao.findById(expected.getId());
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("merge the detached object when save")
        @Test
        void save_megre()
        {
            Incident test = createIncident1();
            saveNewTask(test);

            test.setTitle(TEST_TITLE + TEST_STR1);
            test.setDescription(TEST_DESCRIPTION + TEST_STR2);
            runInTransaction(() -> dao.save(test));

            List<Incident> tests = dao.findAll();
            assertEquals(1, tests.size());
            assertTrue(dao.findByTitle(test.getTitle()).contains(test));
        }

        @Test
        void delete()
        {
            Incident test = createIncident1();
            saveNewTask(test);
            runInTransaction(() -> dao.delete(test.getId()));
            Optional<Incident> none = dao.findById(test.getId());
            assertFalse(none.isPresent());
            assertTrue(dao.findAll().isEmpty());
        }
    }
}