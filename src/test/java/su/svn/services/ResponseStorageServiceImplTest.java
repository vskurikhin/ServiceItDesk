package su.svn.services;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import su.svn.db.GroupDaoJpa;
import su.svn.db.PrimaryGroupDaoJpa;
import su.svn.db.UserDaoJpa;
import su.svn.models.DataSet;
import su.svn.models.Group;
import su.svn.models.PrimaryGroup;
import su.svn.models.User;

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
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(WeldJunit5Extension.class)
class ResponseStorageServiceImplTest
{
    private static Query mockedQuery;

    private static Supplier<DataSet> dataSetSupplier;

    private static Supplier<DataSet> createDataSetGroupSupplier()
    {
        return Group::new;
    }

    @BeforeAll
    static void setMockedQuery()
    {
        mockedQuery = mock(Query.class);
        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(Collections.emptyList());
    }

    @SuppressWarnings("unchecked") // still needed :( but just once :)
    public static <T> TypedQuery<T> mockTypedQuery() {
        TypedQuery<T> typedQuery = mock(TypedQuery.class);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());
        when(typedQuery.getSingleResult()).thenReturn((T) dataSetSupplier);

        return typedQuery;
    }

    public EntityManager getEntityManager()
    {
        return Persistence.createEntityManagerFactory("mnf-pu-test").createEntityManager();
    }

    @WeldSetup
    public WeldInitiator weld = WeldInitiator
        .from(ResponseStorageServiceImpl.class, GroupDaoJpa.class, UserDaoJpa.class, PrimaryGroupDaoJpa.class)
        .setEjbFactory(getEjbFactory())
        .setPersistenceContextFactory(getPCFactory())
        .build();

    @Test
    void createGroup(ResponseStorageService storage)
    {
        Group entity = new Group();
        entity.setName("test");
        Response response = storage.create(new StringBuffer("test"), entity);
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
    void readAllGroups(ResponseStorageService storage)
    {
        Response response = storage.readAllGroups();
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readGroupById(ResponseStorageService storage)
    {
        dataSetSupplier = createDataSetGroupSupplier();
        Response response = storage.readGroupById(0L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    @Test
    void readGroupByIdWithUsers(ResponseStorageService storage)
    {
        dataSetSupplier = createDataSetGroupSupplier();
        Response response = storage.readGroupById(0L);
        assertEquals(Response.Status.OK, response.getStatusInfo());
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
    void updateUser(ResponseStorageService storage)
    {
        User entity = new User();
        entity.setGroup(new PrimaryGroup());
        entity.setId(1L);
        entity.setName("test");
        Response response = storage.update(new StringBuffer("test"), entity);
        assertEquals(Response.Status.OK, response.getStatusInfo());
    }

    private Function<InjectionPoint, Object> getEjbFactory()
    {
        return ip -> {
            switch (ip.getAnnotated().getBaseType().getTypeName()) {
                case "su.svn.db.GroupDao":
                    return weld.select(GroupDaoJpa.class).get();
                case "su.svn.db.UserDao":
                    return weld.select(UserDaoJpa.class).get();
                case "su.svn.db.PrimaryGroupDao":
                    return weld.select(PrimaryGroupDaoJpa.class).get();
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
            }

            @Override
            public <T> T merge(T entity) {
                return null;
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
                return (T) dataSetSupplier.get();
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
                return mockTypedQuery();
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
                return mockTypedQuery();
            }

            @Override
            public Query createNamedQuery(String name) {
                return mockedQuery;
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