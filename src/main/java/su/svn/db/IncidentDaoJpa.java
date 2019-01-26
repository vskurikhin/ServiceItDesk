/*
 * IncidentDaoJpa.java
 * This file was last modified at 2019-01-26 17:22 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Incident;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@TransactionAttribute(SUPPORTS)
public class IncidentDaoJpa implements IncidentDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    public static final String SELECT_ALL = "SELECT i FROM Incident i";

    public static final String SELECT_WHERE_NAME = SELECT_ALL + " WHERE i.title LIKE :name";

    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE i.description LIKE :desc";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentDaoJpa.class);

    public IncidentDaoJpa() { /* None */}

    public IncidentDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    public List<Incident> emptyList() {
        //noinspection unchecked
        return Collections.EMPTY_LIST;
    }

    @Override
    public Incident findById(Long id)
    {
        try {
            return em.find(Incident.class, id);
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return null;
        }
    }

    @Override
    public List<Incident> findAll()
    {
        try {
            return em.createQuery(SELECT_ALL, Incident.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return emptyList();
        }
    }

    @Override
    public List<Incident> findByName(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_NAME, Incident.class)
                .setParameter("name", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by name: {} because had the exception {}", value, e);
            return emptyList();
        }
    }

    @Override
    public List<Incident> findByDescription(String value)
    {
        try {
            return em.createQuery(SELECT_WHERE_DESC, Incident.class)
                .setParameter("desc", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by description: {} because had the exception {}", value, e);
            return emptyList();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean save(Incident entity)
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
            LOGGER.error("Can't save incident with id: {} because had the exception {}", entity.getId(), e);
            return false;
        }

        LOGGER.info("Save incident with id: {}", entity.getId());
        return true;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        try {
            Incident merged = em.merge(findById(id));
            em.remove(merged);
            LOGGER.info("Delete incident with id: {}", merged.getId());
            return true;
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save incident with id: {} because had the exception {}", id, e);
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
