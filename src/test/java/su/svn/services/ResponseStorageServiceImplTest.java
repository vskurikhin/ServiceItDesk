/*
 * ResponseStorageServiceImplTest.java
 * This file was last modified at 2019-02-10 19:38 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.db.*;
import su.svn.models.*;
import su.svn.utils.logging.TestAppender;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static su.svn.TestData.*;

@ExtendWith(WeldJunit5Extension.class)
class ResponseStorageServiceImplTest
{
    private TestAppender appender; // TODO

    private static Query mockedQuery;

    private static Supplier<DataSet> dataSetSupplier;

    @SuppressWarnings("unchecked")
    public static <T> T supply(Class<T> tClass) {
        switch (tClass.getName()) {
            case "su.svn.models.ConfigurationType":
                return (T) createConfigurationType1();
            case "su.svn.models.ConfigurationUnit":
                return (T) createConfigurationUnit1();
            case "su.svn.models.Group":
                return (T) createGroup1();
            case "su.svn.models.Incident":
                return (T) createIncident1();
            case "su.svn.models.Message":
                return (T) createMessage1();
            case "su.svn.models.PrimaryGroup":
                return (T) createPrimaryGroup1();
            case "su.svn.models.Status":
                return (T) createStatus1();
            case "su.svn.models.Task":
                return (T) createTask1();
            case "su.svn.models.User":
                return (T) createUser1();
            default:
                return null;
        }
    }

    private static Function<Class<?>, ?> classToData;

    @BeforeAll
    static void setMockedQuery()
    {
        classToData = aClass -> supply(aClass); // TODO
        mockedQuery = mock(Query.class);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.emptyList());
        when(mockedQuery.getSingleResult()).thenReturn(dataSetSupplier);
    }


    @SuppressWarnings("unchecked") // still needed :( but just once :)
    static <T> TypedQuery<T> mockTypedQuery(Class<T> tClass) {
        TypedQuery<T> typedQuery = mock(TypedQuery.class);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());
        when(typedQuery.getSingleResult()).thenReturn((T) classToData.apply(tClass));

        return typedQuery;
    }

    public EntityManager getEntityManager()
    {
        return Persistence.createEntityManagerFactory("mnf-pu-test").createEntityManager();
    }

    @WeldSetup
    public WeldInitiator weld = WeldInitiator
        .from(ResponseStorageServiceImpl.class, ConfigurationTypeDaoJpa.class, ConfigurationUnitDaoJpa.class,
            GroupDaoJpa.class, IncidentDaoJpa.class, MessageDaoJpa.class, PrimaryGroupDaoJpa.class, StatusDaoJpa.class,
            TaskDaoJpa.class, UserDaoJpa.class)
        .setEjbFactory(getEjbFactory())
        .setPersistenceContextFactory(getPCFactory())
        .build();

    @Test
    void createConfigurationUnit(ResponseStorageService storage)
    {
        /* TODO
        User user = new User();
        user.setName("test");
        ConfigurationUnit entity = new ConfigurationUnit();
        entity.setName("test");
        entity.setAdmin(user);
        entity.setOwner(user);
        Response response = storage.createConfigurationUnit(new StringBuffer("test"), entity);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
        */
    }

    @Test
    void createGroup(ResponseStorageService storage)
    {
        Group entity = new Group();
        entity.setName("test");
        Response response = storage.create(new StringBuffer("test"), entity);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    void createExistingGroup(ResponseStorageService storage)
    {
        Group entity = new Group();
        entity.setId(1L);
        Response response = storage.create(new StringBuffer("test"), entity);
        assertEquals(Response.Status.NOT_ACCEPTABLE, response.getStatusInfo());
    }

    @Test
    void createIncident(ResponseStorageService storage)
    {
        User user = new User();
        user.setName("test");
        Status status = new Status();
        status.setStatus("test");
        Incident entity = new Incident();
        entity.setTitle("test");
        entity.setDescription("test");
        entity.setConsumer(user);
        entity.setStatus(status);
        Response response = storage.createIncident(new StringBuffer("test"), entity);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    void createTask(ResponseStorageService storage)
    {
        User user = new User();
        user.setName("test");
        Status status = new Status();
        status.setStatus("test");
        Task entity = new Task();
        entity.setTitle("test");
        entity.setDescription("test");
        entity.setConsumer(user);
        entity.setStatus(status);
        Response response = storage.createTask(new StringBuffer("test"), entity);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    void createUser(ResponseStorageService storage)
    {
        User entity = new User();
        entity.setGroup(new PrimaryGroup());
        entity.setName("test");
        Response response = storage.create(new StringBuffer("test"), entity);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    void createUserAndGroup(ResponseStorageService storage)
    {
        classToData = clazz -> supply(clazz);
        dataSetSupplier = PrimaryGroup::new;
        User entity = new User();
        PrimaryGroup group = new PrimaryGroup();
        group.setName("test");
        entity.setGroup(group);
        entity.setName("test");
        Response response = storage.createUser(new StringBuffer("test"), entity);
        assertEquals(Response.Status.CREATED, response.getStatusInfo());
    }

    @Test
    void createExistingUser(ResponseStorageService storage)
    {
        PrimaryGroup group = new PrimaryGroup();
        group.setName("test");
        User entity = new User();
        entity.setId(1L);
        entity.setName("test");
        entity.setGroup(group);
        Response response = storage.createUser(new StringBuffer("test"), entity);
        assertEquals(Response.Status.NOT_ACCEPTABLE, response.getStatusInfo());
    }

    @Test
    void createUserNullGroup(ResponseStorageService storage)
    {
        appender = TestAppender.create();
        User entity = new User();
        entity.setName("test");
        Response response = storage.createUser(new StringBuffer("test"), entity);
        assertEquals(Response.Status.NOT_ACCEPTABLE, response.getStatusInfo());
        assertTrue(appender.getMessages().size() > 0);
    }

    @Test
    void createUserGroupNullName(ResponseStorageService storage)
    {
        User entity = new User();
        PrimaryGroup group = new PrimaryGroup();
        entity.setGroup(group);
        Response response = storage.createUser(new StringBuffer("test"), entity);
        assertEquals(Response.Status.NOT_ACCEPTABLE, response.getStatusInfo());
    }

    @Test
    void readAllConfigurationType(ResponseStorageService storage)
    {
        dataSetSupplier = ConfigurationType::new;
        Response response = storage.readAll(ConfigurationType.class);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readAllConfigurationUnit(ResponseStorageService storage)
    {
        dataSetSupplier = ConfigurationUnit::new;
        Response response = storage.readAllConfigurationUnit();
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readAllGroups(ResponseStorageService storage)
    {
        dataSetSupplier = Group::new;
        Response response = storage.readAllGroups();
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readAllIncidents(ResponseStorageService storage)
    {
        dataSetSupplier = Incident::new;
        Response response = storage.readAll(Incident.class);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readAllMessages(ResponseStorageService storage)
    {
        dataSetSupplier = Message::new;
        Response response = storage.readAll(Message.class);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readAllStatuses(ResponseStorageService storage)
    {
        dataSetSupplier = Status::new;
        Response response = storage.readAll(Status.class);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readAllTasks(ResponseStorageService storage)
    {
        dataSetSupplier = Task::new;
        Response response = storage.readAll(Task.class);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readAllUsers(ResponseStorageService storage)
    {
        dataSetSupplier = User::new;
        Response response = storage.readAll(User.class);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readConfigurationTypeById(ResponseStorageService storage)
    {
        classToData = clazz -> supply(clazz);
        dataSetSupplier = ConfigurationType::new;
        Response response = storage.readById(ConfigurationType.class, 1L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readConfigurationTypeById_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readById(ConfigurationType.class, 1L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readConfigurationUnitById(ResponseStorageService storage)
    {
        classToData = clazz -> supply(clazz);
        dataSetSupplier = ConfigurationUnit::new;
        Response response = storage.readById(ConfigurationUnit.class, 0L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readConfigurationUnitById_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readById(ConfigurationUnit.class, 0L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readGroupById(ResponseStorageService storage)
    {
        classToData = clazz -> supply(clazz);
        dataSetSupplier = Group::new;
        Response response = storage.readById(Group.class, 0L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readGroupById_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readById(Group.class, 0L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readGroupByIdWithUsers(ResponseStorageService storage)
    {
        dataSetSupplier = Group::new;
        Response response = storage.readGroupByIdWithUsers(0L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readGroupByIdWithUsers_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readGroupByIdWithUsers(0L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readIncidentById(ResponseStorageService storage)
    {
        classToData = clazz -> supply(clazz);
        dataSetSupplier = Incident::new;
        Response response = storage.readById(Incident.class, 1L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readIncidentById_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readById(Incident.class, 1L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readMessageById(ResponseStorageService storage)
    {
        classToData = aClass -> supply(aClass);
        dataSetSupplier = Message::new;
        Response response = storage.readById(Message.class, 1L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readMessageById_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readById(Message.class, 1L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readStatusById(ResponseStorageService storage)
    {
        classToData = clazz -> supply(clazz);
        dataSetSupplier = Status::new;
        Response response = storage.readById(Status.class, 1L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void  readStatusById_null(ResponseStorageService storage)
    {
        dataSetSupplier = () -> null;
        Response response = storage.readById(Status.class, 1L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readTaskById(ResponseStorageService storage)
    {
        dataSetSupplier = Task::new;
        Response response = storage.readById(Task.class, 1L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void  readTaskById_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readById(Task.class, 1L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readUserById(ResponseStorageService storage)
    {
        dataSetSupplier = User::new;
        Response response = storage.readById(User.class, 1L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readUserByIdZero(ResponseStorageService storage)
    {
        dataSetSupplier = User::new;
        Response response = storage.readById(User.class, 0L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readUserById_null(ResponseStorageService storage)
    {
        classToData = clazz -> null;
        dataSetSupplier = () -> null;
        Response response = storage.readById(User.class, 1L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
    }

    @Test
    void readUserById_ButReturnStatus(ResponseStorageService storage)
    {
        /* TODO
        dataSetSupplier = Status::new;
        Response response = storage.readById(User.class, 1L);
        assertEquals(Response.Status.NOT_FOUND, response.getStatusInfo());
        */
    }

    @Test
    void updateGroup(ResponseStorageService storage)
    {
        Group entity = new Group();
        entity.setId(1L);
        entity.setName("test");
        Response response = storage.update(new StringBuffer("test"), entity);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void updateNotExistingGroup(ResponseStorageService storage)
    {
        Group entity = new Group();
        entity.setId(0L);
        entity.setName("test");
        Response response = storage.update(new StringBuffer("test"), entity);
        assertEquals(Response.Status.NOT_ACCEPTABLE, response.getStatusInfo());
    }

    @Test
    void updateUser(ResponseStorageService storage)
    {
        User entity = new User();
        entity.setGroup(new PrimaryGroup());
        entity.setId(1L);
        entity.setName("test");
        Response response = storage.update(new StringBuffer("test"), entity);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void delete(ResponseStorageService storage)
    {
        dataSetSupplier = Group::new;
        Response response = storage.delete(Group.class, 0L);
        assertEquals(Response.Status.NO_CONTENT, response.getStatusInfo());
    }

    @Test
    void delete_null(ResponseStorageService storage)
    {
        dataSetSupplier = () -> null;
        Response response = storage.delete(Group.class, 0L);
        assertEquals(Response.Status.NO_CONTENT, response.getStatusInfo());
    }

    private Function<InjectionPoint, Object> getEjbFactory()
    {
        return ip -> {
            switch (ip.getAnnotated().getBaseType().getTypeName()) {
                case "su.svn.db.ConfigurationTypeDao":
                    return weld.select(ConfigurationTypeDaoJpa.class).get();
                case "su.svn.db.ConfigurationUnitDao":
                    return weld.select(ConfigurationUnitDaoJpa.class).get();
                case "su.svn.db.GroupDao":
                    return weld.select(GroupDaoJpa.class).get();
                case "su.svn.db.IncidentDao":
                    return weld.select(IncidentDaoJpa.class).get();
                case "su.svn.db.MessageDao":
                    return weld.select(MessageDaoJpa.class).get();
                case "su.svn.db.PrimaryGroupDao":
                    return weld.select(PrimaryGroupDaoJpa.class).get();
                case "su.svn.db.StatusDao":
                    return weld.select(StatusDaoJpa.class).get();
                case "su.svn.db.TaskDao":
                    return weld.select(TaskDaoJpa.class).get();
                case "su.svn.db.UserDao":
                    return weld.select(UserDaoJpa.class).get();
                default:
                    return null;
            }
        };
    }

    private static Function<InjectionPoint, Object> getPCFactory()
    {
        return ip -> new EntityManager() {
            @Override
            public <T> T unwrap(Class<T> cls) {
                return null;
            }

            @Override
            public void setProperty(String propertyName, Object value) {
            }

            @Override
            public void setFlushMode(FlushModeType flushMode) {
            }

            @Override
            public void remove(Object entity) {
            }

            @Override
            public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
            }

            @Override
            public void refresh(Object entity, LockModeType lockMode) {
            }

            @Override
            public void refresh(Object entity, Map<String, Object> properties) {
            }

            @Override
            public void refresh(Object entity) {
            }

            @Override
            public void persist(Object entity) {
                if (Objects.isNull(entity)) {
                    throw new IllegalArgumentException("attempt to create event with null entity");
                }
            }

            @Override
            public <T> T merge(T entity) {
                return entity;
            }

            @Override
            public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
            }

            @Override
            public void lock(Object entity, LockModeType lockMode) {
            }

            @Override
            public void joinTransaction() {
            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public boolean isJoinedToTransaction() {
                return false;
            }

            @Override
            public EntityTransaction getTransaction() {
                return null;
            }

            @Override
            public <T> T getReference(Class<T> entityClass, Object primaryKey) {
                return null;
            }

            @Override
            public Map<String, Object> getProperties() {
                return null;
            }

            @Override
            public Metamodel getMetamodel() {
                return null;
            }

            @Override
            public LockModeType getLockMode(Object entity) {
                return null;
            }

            @Override
            public FlushModeType getFlushMode() {
                return null;
            }

            @Override
            public EntityManagerFactory getEntityManagerFactory() {
                return null;
            }

            @Override
            public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
                return null;
            }

            @Override
            public EntityGraph<?> getEntityGraph(String graphName) {
                return null;
            }

            @Override
            public Object getDelegate() {
                return null;
            }

            @Override
            public CriteriaBuilder getCriteriaBuilder() {
                return null;
            }

            @Override
            public void flush() {
            }

            @Override
            public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
                return null;
            }

            @Override
            public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
                return null;
            }

            @Override
            public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
                return null;
            }

            @Override
            public <T> T find(Class<T> entityClass, Object primaryKey) {
                //noinspection unchecked
                return (T) classToData.apply(entityClass);
            }

            @Override
            public void detach(Object entity) {
            }

            @Override
            public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
                return null;
            }

            @Override
            public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
                return null;
            }

            @Override
            public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
                return null;
            }

            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                return mockTypedQuery(resultClass);
            }

            @Override
            public Query createQuery(CriteriaDelete deleteQuery) {
                return null;
            }

            @Override
            public Query createQuery(CriteriaUpdate updateQuery) {
                return null;
            }

            @Override
            public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
                return null;
            }

            @Override
            public Query createQuery(String qlString) {
                return mockedQuery;
            }

            @Override
            public Query createNativeQuery(String sqlString, String resultSetMapping) {
                return null;
            }

            @Override
            public Query createNativeQuery(String sqlString, Class resultClass) {
                return null;
            }

            @Override
            public Query createNativeQuery(String sqlString) {
                return null;
            }

            @Override
            public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
                return null;
            }

            @Override
            public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
                return mockTypedQuery(resultClass);
            }

            @Override
            public Query createNamedQuery(String name) {
                return null;
            }

            @Override
            public EntityGraph<?> createEntityGraph(String graphName) {
                return null;
            }

            @Override
            public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
                return null;
            }

            @Override
            public boolean contains(Object entity) {
                return false;
            }

            @Override
            public void close() {
            }

            @Override
            public void clear() {
            }
        };
    }
}