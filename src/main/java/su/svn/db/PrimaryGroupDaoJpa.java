/*
 * PrimaryGroupDaoJpa.java
 * This file was last modified at 2019-01-26 18:11 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.models.PrimaryGroup;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static su.svn.models.Group.*;

@Stateless
@TransactionAttribute(SUPPORTS)
public class PrimaryGroupDaoJpa implements PrimaryGroupDao
{
    // public static final String SELECT_ALL = "SELECT g FROM PrimaryGroup g";

    // public static final String SELECT_WHERE_NAME = SELECT_ALL + " WHERE g.name LIKE :name";

    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimaryGroupDaoJpa.class);

    public PrimaryGroupDaoJpa() { /* None */}

    public PrimaryGroupDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public PrimaryGroup findById(Long id)
    {
        try {
            return em.find(PrimaryGroup.class, id);
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return null;
        }
    }

    @Override
    public List<PrimaryGroup> findAll()
    {
        try {
            return em.createNamedQuery(FIND_ALL, PrimaryGroup.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<PrimaryGroup> findByName(String value)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_NAME, PrimaryGroup.class)
                .setParameter("name", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by name: {} because had the exception {}", value, e);
            return Collections.emptyList();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean save(PrimaryGroup entity)
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
            LOGGER.error("Can't save primary group with id: {} because had the exception {}", entity.getId(), e);
            return false;
        }

        LOGGER.info("Save group with id: {}", entity.getId());
        return true;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        try {
            PrimaryGroup merged = em.merge(findById(id));
            em.remove(merged);
            LOGGER.info("Delete group with id: {}", merged.getId());
            return true;
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save group with id: {} because had the exception {}", id, e);
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
