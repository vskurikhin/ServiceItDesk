/*
 * ConfigurationUnitDaoJpaTest.java
 * This file was last modified at 2019-01-26 18:13 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.models.ConfigurationUnit;
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
import static su.svn.models.ConfigurationUnit.FIND_ALL;
import static su.svn.models.ConfigurationUnit.FIND_ALL_WHERE_DESC;
import static su.svn.models.ConfigurationUnit.FIND_ALL_WHERE_NAME;
//import static su.svn.db.ConfigurationUnitDaoJpa.SELECT_ALL;
//import static su.svn.db.ConfigurationUnitDaoJpa.SELECT_WHERE_DESC;
//import static su.svn.db.ConfigurationUnitDaoJpa.SELECT_WHERE_NAME;

@DisplayName("Class ConfigurationUnitDaoJpaTest")
class ConfigurationUnitDaoJpaTest
{
    ConfigurationUnitDaoJpa dao;

    @Test
    @DisplayName("is instantiated with new ConfigurationUnitDaoJpa()")
    void isInstantiatedWithNew()
    {
        new ConfigurationUnitDaoJpa();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            dao = new ConfigurationUnitDaoJpa();
        }

        @Test
        @DisplayName("default values in ConfigurationUnitDaoJpa()")
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
            dao = new ConfigurationUnitDaoJpa(entityManager);
            appender = TestAppender.create();
        }

        @Test
        @DisplayName("constructor injected values in ConfigurationUnitDaoJpa()")
        void defaults()
        {
            assertThat(dao).hasFieldOrPropertyWithValue("em", entityManager);
        }

        @DisplayName("find by id with success")
        @Test
        void findById_success()
        {
            ConfigurationUnit expected = new ConfigurationUnit();
            expected.setId(TEST_ID1);
            expected.setName(TEST_NAME);
            expected.setDescription(TEST_DESCRIPTION);

            when(entityManager.find(ConfigurationUnit.class, TEST_ID1)).thenReturn(expected);

            Optional<ConfigurationUnit> test = dao.findById(TEST_ID1);
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("find by id return null")
        @Test
        void findById_null()
        {
            when(entityManager.find(ConfigurationUnit.class, TEST_ID9)).thenReturn(null);

            Optional<ConfigurationUnit> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
        }

        @DisplayName("find by id was an IllegalArgumentException")
        @Test
        void findById_exception()
        {
            when(entityManager.find(ConfigurationUnit.class, TEST_ID9)).thenThrow(IllegalArgumentException.class);

            Optional<ConfigurationUnit> test = dao.findById(TEST_ID9);
            assertFalse(test.isPresent());
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("and find all in empty table")
        @Test
        void findAll_empty()
        {
            List<ConfigurationUnit> expected = Collections.emptyList();
            TypedQuery<ConfigurationUnit> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL, ConfigurationUnit.class)).thenReturn(mockedQuery);

            List<ConfigurationUnit> test = dao.findAll();
            assertEquals(expected, test);
        }

        @DisplayName("find all was an IllegalArgumentException")
        @Test
        void findAll_exception()
        {
            List<ConfigurationUnit> expected = Collections.emptyList();
            TypedQuery<ConfigurationUnit> mockedQuery = mockTypedQuery();
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL, ConfigurationUnit.class)).thenReturn(mockedQuery);

            List<ConfigurationUnit> test = dao.findAll();
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by name in empty table")
        @Test
        void findByName_empty()
        {
            List<ConfigurationUnit> expected = new LinkedList<>();
            TypedQuery<ConfigurationUnit> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("name", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_NAME, ConfigurationUnit.class)).thenReturn(mockedQuery);

            List<ConfigurationUnit> test = dao.findByName(TEST_NAME);
            assertEquals(expected, test);
        }

        @DisplayName("find by name was an IllegalArgumentException")
        @Test
        void findByName_exception()
        {
            List<ConfigurationUnit> expected = Collections.emptyList();
            TypedQuery<ConfigurationUnit> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("name", TEST_NAME)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_NAME, ConfigurationUnit.class)).thenReturn(mockedQuery);

            List<ConfigurationUnit> test = dao.findByName(TEST_NAME);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("find by description in empty table")
        @Test
        void findByDescription_empty()
        {
            List<ConfigurationUnit> expected = new LinkedList<>();
            TypedQuery<ConfigurationUnit> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenReturn(expected);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_DESC, ConfigurationUnit.class)).thenReturn(mockedQuery);

            List<ConfigurationUnit> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
        }

        @DisplayName("find by description was an IllegalArgumentException")
        @Test
        void findByDescription_exception()
        {
            List<ConfigurationUnit> expected = Collections.emptyList();
            TypedQuery<ConfigurationUnit> mockedQuery = mockTypedQuery();
            when(mockedQuery.setParameter("desc", TEST_DESCRIPTION)).thenReturn(mockedQuery);
            when(mockedQuery.getResultList()).thenThrow(PersistenceException.class);
            when(entityManager.createNamedQuery(FIND_ALL_WHERE_DESC, ConfigurationUnit.class)).thenReturn(mockedQuery);

            List<ConfigurationUnit> test = dao.findByDescription(TEST_DESCRIPTION);
            assertEquals(expected, test);
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with persist entity was a PersistenceException")
        @Test
        void save_persist_exception()
        {
            ConfigurationUnit expected = new ConfigurationUnit();
            doThrow(PersistenceException.class).when(entityManager).persist(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with flush was a PersistenceException")
        @Test
        void save_flush_exception()
        {
            doThrow(PersistenceException.class).when(entityManager).flush();

            ConfigurationUnit expected = new ConfigurationUnit();
            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("save with merge was a PersistenceException")
        @Test
        void save_merge_exception()
        {
            ConfigurationUnit expected = TEST_CONFIGURATION_UNIT1;
            doThrow(PersistenceException.class).when(entityManager).merge(expected);

            assertFalse(dao.save(expected));
            assertTrue(appender.getMessages().size() > 0);
        }

        @DisplayName("delete was a PersistenceException")
        @Test
        void delete_exception()
        {
            ConfigurationUnit expected = TEST_CONFIGURATION_UNIT1;
            when(entityManager.find(ConfigurationUnit.class, expected.getId())).thenReturn(expected);
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
            dao = new ConfigurationUnitDaoJpa(entityManager);
        }

        private void saveNewGroupAndConfigurationUnit(ConfigurationUnit test)
        {
            test.setId(0L);
            test.getAdmin().setId(0L);
            test.getAdmin().setName(TEST_NAME + "_ADMIN");
            test.getAdmin().getGroup().setId(0L);
            test.getAdmin().getGroup().setName(TEST_NAME + "_ADMINS");
            test.getOwner().setId(0L);
            test.getOwner().setName(TEST_NAME + "_OWNER");
            test.getOwner().getGroup().setId(0L);
            test.getOwner().getGroup().setName(TEST_NAME + "_OWNER_GROUP");
            test.getGroup().setId(0L);
            runInTransaction(() -> {
                PrimaryGroupDao primaryGroupDao = new PrimaryGroupDaoJpa(entityManager);
                GroupDao groupDao = new GroupDaoJpa(entityManager);
                UserDao userDao = new UserDaoJpa(entityManager);
                ConfigurationTypeDao typeDao = new ConfigurationTypeDaoJpa(entityManager);
                primaryGroupDao.save(test.getAdmin().getGroup());
                userDao.save(test.getAdmin());
                primaryGroupDao.save(test.getOwner().getGroup());
                userDao.save(test.getOwner());
                groupDao.save(test.getGroup());
                typeDao.save(test.getType());
                dao.save(test);
            });
        }

        @DisplayName("persists new when save")
        @Test
        void save_persists()
        {
            ConfigurationUnit expected = createConfigurationUnit1();
            saveNewGroupAndConfigurationUnit(expected);
            Optional<ConfigurationUnit> test = dao.findById(expected.getId());
            assertTrue(test.isPresent());
            assertEquals(expected, test.get());
        }

        @DisplayName("merge the detached object when save")
        @Test
        void save_megre()
        {
            System.out.println("save_megre" + dao.findAll());
            ConfigurationUnit test = createConfigurationUnit1(); // will change test cunit
            saveNewGroupAndConfigurationUnit(test);
            test.setName(TEST_NAME + TEST_STR1);
            test.setDescription(TEST_DESCRIPTION + TEST_STR2);
            runInTransaction(() -> dao.save(test));
            assertEquals(1, dao.findAll().size());
            assertTrue(dao.findByName(test.getName()).contains(test));
        }

        @Test
        void delete()
        {
            ConfigurationUnit test = new ConfigurationUnit();
            test.setName(TEST_NAME);
            test.setDescription(TEST_DESCRIPTION);
            runInTransaction(() -> dao.save(test));
            runInTransaction(() -> dao.delete(test.getId()));
            Optional<ConfigurationUnit> none = dao.findById(test.getId());
            assertFalse(none.isPresent());
            assertTrue(dao.findAll().isEmpty());
        }
    }
}