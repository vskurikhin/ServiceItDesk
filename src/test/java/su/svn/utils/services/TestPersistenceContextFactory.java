/*
 * TestPersistenceContextFactory.java
 * This file was last modified at 2019-02-04 00:03 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.utils.services;

import su.svn.models.*;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestPersistenceContextFactory
{
    protected static Query mockedQuery;

    protected static Supplier<DataSet> dataSetSupplier;

    protected static Query mockQuery()
    {
        Query query = mock(Query.class);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());
        when(query.getSingleResult()).thenReturn(dataSetSupplier);

        return query;
    }

    @SuppressWarnings("unchecked") // still needed :( but just once :)
    protected static <T> TypedQuery<T> mockTypedQuery() {
        TypedQuery<T> typedQuery = mock(TypedQuery.class);
        when(typedQuery.setParameter(anyString(), any())).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(Collections.emptyList());
        when(typedQuery.getSingleResult()).thenReturn((T) dataSetSupplier.get());

        return typedQuery;
    }

    public EntityManager getEntityManager()
    {
        return Persistence.createEntityManagerFactory("mnf-pu-test").createEntityManager();
    }

    protected static Function<InjectionPoint, Object> getPCFactory()
    {
        return ip -> new EntityManager() {
            @Override
            public <T> T unwrap(Class<T> cls) {
                throw new UnsupportedOperationException();
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
                throw new UnsupportedOperationException("getTransaction in getPCFactory");
            }

            @Override
            public <T> T getReference(Class<T> entityClass, Object primaryKey) {
                throw new UnsupportedOperationException("getReference in getPCFactory");
            }

            @Override
            public Map<String, Object> getProperties() {
                throw new UnsupportedOperationException("getProperties in getPCFactory");
            }

            @Override
            public Metamodel getMetamodel() {
                throw new UnsupportedOperationException("getMetamodel in getPCFactory");
            }

            @Override
            public LockModeType getLockMode(Object entity) {
                throw new UnsupportedOperationException("getLockMode in getPCFactory");
            }

            @Override
            public FlushModeType getFlushMode() {
                throw new UnsupportedOperationException("getFlushMode in getPCFactory");
            }

            @Override
            public EntityManagerFactory getEntityManagerFactory() {
                throw new UnsupportedOperationException("getEntityManagerFactory in getPCFactory");
            }

            @Override
            public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
                throw new UnsupportedOperationException("getEntityGraphs in getPCFactory");
            }

            @Override
            public EntityGraph<?> getEntityGraph(String graphName) {
                throw new UnsupportedOperationException("getEntityGraph in getPCFactory");
            }

            @Override
            public Object getDelegate() {
                throw new UnsupportedOperationException("getDelegate in getPCFactory");
            }

            @Override
            public CriteriaBuilder getCriteriaBuilder() {
                throw new UnsupportedOperationException("getCriteriaBuilder in getPCFactory");
            }

            @Override
            public void flush() {
            }

            @Override
            public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
                throw new UnsupportedOperationException();
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
                throw new UnsupportedOperationException();
            }

            @Override
            public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
                throw new UnsupportedOperationException();
            }

            @Override
            public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
                return mockTypedQuery();
            }

            @Override
            public Query createQuery(CriteriaDelete deleteQuery) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Query createQuery(CriteriaUpdate updateQuery) {
                throw new UnsupportedOperationException();
            }

            @Override
            public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Query createQuery(String qlString) {
                return mockedQuery;
            }

            @Override
            public Query createNativeQuery(String sqlString, String resultSetMapping) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Query createNativeQuery(String sqlString, Class resultClass) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Query createNativeQuery(String sqlString) {
                throw new UnsupportedOperationException();
            }

            @Override
            public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
                throw new UnsupportedOperationException("createNamedStoredProcedureQuery in getPCFactory");
            }

            @Override
            public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
                return mockTypedQuery();
            }

            @Override
            public Query createNamedQuery(String name) {
                throw new UnsupportedOperationException("createNamedQuery in getPCFactory");
            }

            @Override
            public EntityGraph<?> createEntityGraph(String graphName) {
                throw new UnsupportedOperationException("createEntityGraph in getPCFactory");
            }

            @Override
            public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
                throw new UnsupportedOperationException();
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