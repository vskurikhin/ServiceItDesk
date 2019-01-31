/*
 * TaskDaoJpa.java
 * This file was last modified at 2019-01-26 18:09 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@TransactionAttribute(SUPPORTS)
public class TaskDaoJpa implements TaskDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    public static final String SELECT_ALL = "SELECT t FROM Task t";

    public static final String SELECT_WHERE_TITLE = SELECT_ALL + " WHERE t.title LIKE :title";

    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE t.description LIKE :desc";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskDaoJpa.class);

    public TaskDaoJpa() { /* None */}

    public TaskDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Optional<Task> findById(Long id)
    {
        try {
            return Optional.ofNullable(em.find(Task.class, id));
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Task> findAll()
    {
        try {
            return em.createQuery(SELECT_ALL, Task.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Task> findByTitle(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_TITLE, Task.class)
                .setParameter("title", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by title: {} because had the exception {}", value, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Task> findByDescription(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_DESC, Task.class)
                .setParameter("desc", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by description: {} because had the exception {}", value, e);
            return Collections.emptyList();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean save(Task entity)
    {
        try {
            if (null == entity.getId() || 0 == entity.getId()) {
                em.persist(entity);
                em.flush();
            } else {
                em.merge(entity);
            }
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save task with id: {} because had the exception {}", entity.getId(), e);
            return false;
        }

        LOGGER.info("Save task with id: {}", entity.getId());
        return true;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        try {
            Task merged = em.merge(findById(id).orElseThrow(NoResultException::new));
            em.remove(merged);
            LOGGER.info("Delete task with id: {}", merged.getId());
            return true;
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save task with id: {} because had the exception {}", id, e);
            return false;
        }
    }

    EntityManager getEntityManager()
    {
        return em;
    }

    void setEntityManager(EntityManager entityManager)
    {
        this.em = entityManager;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF