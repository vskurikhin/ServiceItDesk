/*
 * IncidentDaoJpa.java
 * This file was last modified at 2019-01-26 18:11 by Victor N. Skurikhin.
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
import java.util.Optional;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static su.svn.models.Incident.FIND_ALL;
import static su.svn.models.Incident.FIND_ALL_WHERE_DESC;
import static su.svn.models.Incident.FIND_ALL_WHERE_TITLE;

@Stateless
@TransactionAttribute(SUPPORTS)
public class IncidentDaoJpa implements IncidentDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentDaoJpa.class);

    /* public static final String SELECT_ALL = "SELECT i FROM Incident i";
    public static final String SELECT_WHERE_NAME = SELECT_ALL + " WHERE i.title LIKE :name";
    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE i.description LIKE :desc"; */

    public IncidentDaoJpa() { /* None */}

    public IncidentDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Optional<Incident> findById(Long id)
    {
        try {
            return Optional.ofNullable(em.find(Incident.class, id));
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Incident> findAll()
    {
        try {
            return em.createNamedQuery(FIND_ALL, Incident.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Incident> findByTitle(String title)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_TITLE, Incident.class)
                .setParameter("title", title)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by name: {} because had the exception {}", title, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Incident> findByDescription(String value)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_DESC, Incident.class)
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
            Incident merged = em.merge(findById(id).orElseThrow(NoResultException::new));
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
