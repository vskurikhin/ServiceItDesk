/*
 * StatusDaoJpa.java
 * This file was last modified at 2019-01-26 18:10 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Status;

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
public class StatusDaoJpa implements StatusDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    public static final String SELECT_ALL = "SELECT s FROM Status s";

    public static final String SELECT_WHERE_STATUS = SELECT_ALL + " WHERE s.status LIKE :status";

    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE s.description LIKE :desc";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusDaoJpa.class);

    public StatusDaoJpa() { /* None */}

    public StatusDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Optional<Status> findById(Long id)
    {
        try {
            return Optional.ofNullable(em.find(Status.class, id));
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Status> findAll()
    {
        try {
            return em.createQuery(SELECT_ALL, Status.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Status> findByStatus(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_STATUS, Status.class)
                .setParameter("status", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by status: {} because had the exception {}", value, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Status> findByDescription(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_DESC, Status.class)
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
    public boolean save(Status entity)
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
            LOGGER.error("Can't save status with id: {} because had the exception {}", entity.getId(), e);
            return false;
        }

        LOGGER.info("Save status with id: {}", entity.getId());
        return true;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        try {
            Status merged = em.merge(findById(id).orElseThrow(NoResultException::new));
            em.remove(merged);
            LOGGER.info("Delete status with id: {}", merged.getId());
            return true;
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save status with id: {} because had the exception {}", id, e);
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
