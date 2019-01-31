/*
 * MessageDaoJpaTest.java
 * This file was last modified at 2019-01-26 18:14 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.models.Message;
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
import static su.svn.models.Message.FIND_ALL;
import static su.svn.models.Message.FIND_ALL_WHERE_TEXT;

@DisplayName("Class MessageDaoJpaTest")
class MessageDaoJpaTest
{
    MessageDaoJpa dao;

    @Test
    @DisplayName("is instantiated with new MessageDaoJpa()")
    void isInstantiatedWithNew()
    {
        new MessageDaoJpa();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            dao = new MessageDaoJpa();
        }

        @Test
        @DisplayName("default values in MessageDaoJpa()")
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
            dao = new MessageDaoJpa(entityManager);
            appender = TestAppender.create();
        }

        @Test
        @DisplayName("constructor injected values in MessageDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id with success")
        @Test
        void findById_success()
        {
            Message expected = new Message();
            expected.setId(TEST_ID1);
            expected.setText(TEST_TEXT);

            when(entityManager.find(Message.class, TEST_ID1)).thenReturn(expected);

            Optional<Message> test = dao.findById(TEST_ID1);
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("find by id return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Message.class, TEST_ID9)).thenReturn(null);

            Optional<Message> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
        }

        @DisplayName("find by id was an IllegalArgumentException")
        @Test
        void findById_exception()
        {
            when(entityManager.find(Message.class, TEST_ID9)).thenThrow(IllegalArgumentException.class);

            Optional<Message> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("and find all in empty table")
        @Test
        void findAll_empty()
        {
            List<Message> expected = Collections.emptyList();
            TypedQuery<Message> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL, Message.class)).thenReturn(mockedQuery);

            List<Message> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find all was an IllegalArgumentException")
        @Test
        void findAll_exception()
        {
            List<Message> expected = Collections.emptyList();
            TypedQuery<Message> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL, Message.class)).thenReturn(mockedQuery);

            List<Message> test = dao.findAll();
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by text in empty table")
        @Test
        void findByText_empty()
        {
            List<Message> expected = new LinkedList<>();
            TypedQuery<Message> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("text", TEST_TEXT)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_TEXT, Message.class)).thenReturn(mockedQuery);

            List<Message> test = dao.findByText(TEST_TEXT);
            assertEquals(expected, test);
        }

        @DisplayName("find by text was an IllegalArgumentException")
        @Test
        void findByText_exception()
        {
            List<Message> expected = Collections.emptyList();
            TypedQuery<Message> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("text", TEST_TEXT)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_TEXT, Message.class)).thenReturn(mockedQuery);

            List<Message> test = dao.findByText(TEST_TEXT);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with persist entity was a PersistenceException")
        @Test
        void save_persist_exception()
        {
            Message expected = new Message();
            doThrow(PersistenceException.class).when(entityManager).persist(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with flush was a PersistenceException")
        @Test
        void save_flush_exception()
        {
            doThrow(PersistenceException.class).when(entityManager).flush();

            Message expected = new Message();
            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with merge was a PersistenceException")
        @Test
        void save_merge_exception()
        {
            Message expected = TEST_MESSAGE1;
            doThrow(PersistenceException.class).when(entityManager).merge(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("delete was a PersistenceException")
        @Test
        void delete_exception()
        {
            Message expected = TEST_MESSAGE1;
            when(entityManager.find(Message.class, expected.getId())).thenReturn(expected);
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
            dao = new MessageDaoJpa(entityManager);
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            Message expected = new Message();
            expected.setText(TEST_TEXT);
            runInTransaction(() -> dao.save(expected));
            Optional<Message> test = dao.findById(expected.getId());
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("merge the detached object when save")
        @Test
        void save_megre()
        {
            Message test = new Message();
            test.setText(TEST_TEXT);
            runInTransaction(() -> dao.save(test));
            test.setText(TEST_TEXT + TEST_STR1);
            runInTransaction(() -> dao.save(test));
            assertEquals(1, dao.findAll().size());
            assertTrue(dao.findByText(test.getText()).contains(test));
        }

        @Test
        void delete()
        {
            Message test = new Message();
            test.setText(TEST_TEXT);
            runInTransaction(() -> dao.save(test));
            runInTransaction(() -> dao.delete(test.getId()));
            Optional<Message> none = dao.findById(test.getId());
            assertFalse(none.isPresent());
            assertTrue(dao.findAll().isEmpty());
        }
    }
}