package su.svn.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.models.Group;

import javax.persistence.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.TestData.*;
import static su.svn.db.GroupDaoJpa.SELECT_ALL;
import static su.svn.db.GroupDaoJpa.SELECT_WHERE_DESC;
import static su.svn.db.GroupDaoJpa.SELECT_WHERE_NAME;

class GroupDaoJpaTest
{
    GroupDaoJpa dao;

    @Test
    @DisplayName("is instantiated with new GroupDaoJpa()")
    void isInstantiatedWithNew() {
        new GroupDaoJpa();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            dao = new GroupDaoJpa();
        }

        @Test
        @DisplayName("default values in GroupDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", null);
        }

        @Test
        @DisplayName("use setter and getter for EntityManager")
        void testGetSetEntityManager()
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mnf-pu-test");
            EntityManager entityManager = emf.createEntityManager();

            dao.setEntityManager(entityManager);
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
            assertEquals(entityManager, dao.getEntityManager());
        }
    }

    @Nested
    @DisplayName("when mock EntityManager")
    class WhenMock
    {
        private EntityManager entityManager;

        @BeforeEach
        void mockEntityManager()
        {
            entityManager = mock(EntityManager.class);
            dao = new GroupDaoJpa(entityManager);
        }

        @Test
        @DisplayName("Constructor injected values in GroupDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id from table with success")
        @Test
        void findById_success()
        {
            Group expected = new Group();
            expected.setId(TEST_ID1);
            expected.setName(TEST_NAME);
            expected.setDescription(TEST_DESCRIPTION);

            when(entityManager.find(Group.class, TEST_ID1)).thenReturn(expected);

            Group test = dao.findById(TEST_ID1);
            assertEquals(expected, test);
        }

        @DisplayName("find by id from table and return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(Group.class, TEST_ID9)).thenReturn(null);

            Group test = dao.findById(TEST_ID9);
            assertNull(test);
        }

        @DisplayName("find by id from table had throw IllegalArgumentException")
        @Test
        void findById_exception()
        {
            when(entityManager.find(Group.class, TEST_ID9)).thenThrow(IllegalArgumentException.class);

            Group test = dao.findById(TEST_ID9);
            assertNull(test);
        }

        @DisplayName("find all from empty table")
        @Test
        void findAll_empty()
        {
            List<Group> expected = dao.emptyList();
            TypedQuery<Group> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_ALL, Group.class)).thenReturn(mockedQuery);

            List<Group> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find all had throw IllegalArgumentException")
        @Test
        void findAll_exception()
        {
            List<Group> expected = dao.emptyList();
            TypedQuery<Group> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_ALL, Group.class)).thenReturn(mockedQuery);

            List<Group> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find by name from empty table")
        @Test
        void findByName_empty()
        {
            List<Group> expected = new LinkedList<>();
            TypedQuery<Group> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("name", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_WHERE_NAME, Group.class)).thenReturn(mockedQuery);

            List<Group> test = dao.findByName(TEST_NAME);
            assertEquals(expected, test);
        }

        @DisplayName("find by name had throw IllegalArgumentException")
        @Test
        void findByName_exception()
        {
            List<Group> expected = dao.emptyList();
            TypedQuery<Group> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("name", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_WHERE_NAME, Group.class)).thenReturn(mockedQuery);

            List<Group> test = dao.findByName(TEST_NAME);
            assertEquals(expected, test);
        }

        @DisplayName("find by description from empty table")
        @Test
        void findByDescription_empty()
        {
            List<Group> expected = new LinkedList<>();
            TypedQuery<Group> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createQuery(SELECT_WHERE_DESC, Group.class)).thenReturn(mockedQuery);

            List<Group> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
        }

        @DisplayName("find by description had throw IllegalArgumentException")
        @Test
        void findByDescription_exception()
        {
            List<Group> expected = dao.emptyList();
            TypedQuery<Group> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createQuery(SELECT_WHERE_DESC, Group.class)).thenReturn(mockedQuery);

            List<Group> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
        }
    }
}