/*
 * PrimaryGroupDaoJpa.java
 * This file was last modified at 2019-02-03 17:09 by Victor N. Skurikhin.
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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static su.svn.models.PrimaryGroup.*;
import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
@TransactionAttribute(SUPPORTS)
public class PrimaryGroupDaoJpa implements PrimaryGroupDao
{
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrimaryGroupDaoJpa.class);

    public PrimaryGroupDaoJpa() { /* None */}

    PrimaryGroupDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Optional<PrimaryGroup> findById(Long id)
    {
        try {
            return Optional.ofNullable(em.find(PrimaryGroup.class, id));
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return Optional.empty();
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
    @TransactionAttribute(REQUIRED)
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
    @TransactionAttribute(REQUIRED)
    public boolean delete(Long id)
    {
        try {
            PrimaryGroup merged = em.merge(findById(id).orElseThrow(NoResultException::new));
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
