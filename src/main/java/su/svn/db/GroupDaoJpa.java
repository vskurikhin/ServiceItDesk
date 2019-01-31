/*
 * GroupDaoJpa.java
 * This file was last modified at 2019-01-26 18:11 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Group;

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
import static su.svn.models.Group.*;

@Stateless
@TransactionAttribute(SUPPORTS)
public class GroupDaoJpa implements GroupDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDaoJpa.class);

    public GroupDaoJpa() { /* None */}

    public GroupDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Group findById(Long id)
    {
        try {
            return em.find(Group.class, id);
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return null;
        }
    }

    @Override
    public List<Group> findAll()
    {
        try {
            return em.createNamedQuery(FIND_ALL, Group.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Group> findByIdWithUsers(Long id)
    {
        try {
            return Optional.of(
                em.createNamedQuery(FIND_BY_ID_WITH_USERS, Group.class)
                  .setParameter("id", id)
                  .getSingleResult()
            );
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return null;
        }
    }

    @Override
    public List<Group> findByName(String value)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_NAME, Group.class)
                .setParameter("name", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by name: {} because had the exception {}", value, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Group> findByDescription(String value)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_DESC, Group.class)
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
    public boolean save(Group entity)
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
            LOGGER.error("Can't save group with id: {} because had the exception {}", entity.getId(), e);
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
            Group merged = em.merge(findById(id));
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
