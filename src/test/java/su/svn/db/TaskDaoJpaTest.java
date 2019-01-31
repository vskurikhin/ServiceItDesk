/*
 * TaskDaoJpaTest.java
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
import su.svn.models.Task;
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
import static su.svn.db.TaskDaoJpa.SELECT_ALL;
import static su.svn.db.TaskDaoJpa.SELECT_WHERE_DESC;
import static su.svn.db.TaskDaoJpa.SELECT_WHERE_TITLE;

@DisplayName("Class TaskDaoJpaTest")
class TaskDaoJpaTest
{
    TaskDaoJpa dao;

    @Test
    @DisplayName("is instantiated with new TaskDaoJpa()")
    void isInstantiatedWithNew()
    {
        new TaskDaoJpa();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            dao = new TaskDaoJpa();
        }

        @Test
        @DisplayName("default values in TaskDaoJpa()")
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
            dao = new TaskDaoJpa(entityManager);
            appender = TestAppender.create();
        }

        @Test
        @DisplayName("constructor injected values in TaskDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id with success")
        @Test
        void findById_success()
        {
            Task expected = new Task();
            expected.setId(TEST_ID1);
            expected.setTitle(TEST_TITLE);
            expected.setDescription(TEST_DESCRIPTION);

            when(entityManager.find(Task.class, TEST_ID1)).thenReturn(expected);

            Optional<Task> test = dao.findById(TEST_ID1);
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("find by id return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Task.class, TEST_ID9)).thenReturn(null);

            Optional<Task> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
        }

        @DisplayName("find by id was an IllegalArgumentException")
        @Test
        void findById_exception()
        {
            when(entityManager.find(Task.class, TEST_ID9)).thenThrow(IllegalArgumentException.class);

            Optional<Task> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("and find all in empty table")
        @Test
        void findAll_empty()
        {
            List<Task> expected = Collections.emptyList();
            TypedQuery<Task> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_ALL, Task.class)).thenReturn(mockedQuery);

            List<Task> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find all was an IllegalArgumentException")
        @Test
        void findAll_exception()
        {
            List<Task> expected = Collections.emptyList();
            TypedQuery<Task> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_ALL, Task.class)).thenReturn(mockedQuery);

            List<Task> test = dao.findAll();
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by name in empty table")
        @Test
        void findByTitle_empty()
        {
            List<Task> expected = new LinkedList<>();
            TypedQuery<Task> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("title", TEST_TITLE)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_WHERE_TITLE, Task.class)).thenReturn(mockedQuery);

            List<Task> test = dao.findByTitle(TEST_TITLE);
            assertEquals(expected, test);
        }

        @DisplayName("find by name was an IllegalArgumentException")
        @Test
        void findByTitle_exception()
        {
            List<Task> expected = Collections.emptyList();
            TypedQuery<Task> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("title", TEST_TITLE)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_WHERE_TITLE, Task.class)).thenReturn(mockedQuery);

            List<Task> test = dao.findByTitle(TEST_TITLE);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by description in empty table")
        @Test
        void findByDescription_empty()
        {
            List<Task> expected = new LinkedList<>();
            TypedQuery<Task> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_WHERE_DESC, Task.class)).thenReturn(mockedQuery);

            List<Task> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
        }

        @DisplayName("find by description was an IllegalArgumentException")
        @Test
        void findByDescription_exception()
        {
            List<Task> expected = Collections.emptyList();
            TypedQuery<Task> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_WHERE_DESC, Task.class)).thenReturn(mockedQuery);

            List<Task> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with persist entity was a PersistenceException")
        @Test
        void save_persist_exception()
        {
            Task expected = new Task();
            doThrow(PersistenceException.class).when(entityManager).persist(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with flush was a PersistenceException")
        @Test
        void save_flush_exception()
        {
            doThrow(PersistenceException.class).when(entityManager).flush();

            Task expected = new Task();
            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with merge was a PersistenceException")
        @Test
        void save_merge_exception()
        {
            Task expected = TEST_TASK1;
            doThrow(PersistenceException.class).when(entityManager).merge(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("delete was a PersistenceException")
        @Test
        void delete_exception()
        {
            Task expected = TEST_TASK1;
            when(entityManager.find(Task.class, expected.getId())).thenReturn(expected);
            when(entityManager.merge(expected)).thenReturn(expected);
            doThrow(PersistenceException.class).when(entityManager).remove(expected);

            assertFalse(dao.delete(expected.getId()));
            assertTrue(appender.getMessages().size() > 0);
        }
    }

    @SuppressWarnings("Duplicates")
    @Nested
    @DisplayName("JPA H2 create/update tests")
    class JpaH2CreateUpdateTests extends JpaDedicatedEntityManagerTest
    {
        @BeforeEach
        void createNew()
        {
            dao = new TaskDaoJpa(entityManager);
        }

        private void saveNewTask(Task test)
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
            Task expected = createTask1();
            saveNewTask(expected);
            Optional<Task> test = dao.findById(expected.getId());
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("merge the detached object when save")
        @Test
        void save_megre()
        {
            Task test = createTask1(); // will change test user
            saveNewTask(test);


            test.setTitle(TEST_TITLE + TEST_STR1);
            test.setDescription(TEST_DESCRIPTION + TEST_STR2);
            runInTransaction(() -> dao.save(test));

            List<Task> tests = dao.findAll();
            assertEquals(1, tests.size());
            assertTrue(dao.findByTitle(test.getTitle()).contains(test));
        }

        @Test
        void delete()
        {
            Task test = createTask1();
            saveNewTask(test);
            runInTransaction(() -> dao.delete(test.getId()));
            Optional<Task> none = dao.findById(test.getId());
            assertFalse(none.isPresent());
            assertTrue(dao.findAll().isEmpty());
        }
    }
}