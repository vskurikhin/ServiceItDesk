/*
 * ConfigurationUnitDaoJpa.java
 * This file was last modified at 2019-01-26 18:12 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.ConfigurationUnit;

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
import static su.svn.models.ConfigurationUnit.FIND_ALL;
import static su.svn.models.ConfigurationUnit.FIND_ALL_WHERE_DESC;
import static su.svn.models.ConfigurationUnit.FIND_ALL_WHERE_NAME;

@Stateless
@TransactionAttribute(SUPPORTS)
public class ConfigurationUnitDaoJpa implements ConfigurationUnitDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUnitDaoJpa.class);

    /* public static final String SELECT_ALL = "SELECT cu FROM ConfigurationUnit cu";
    public static final String SELECT_WHERE_NAME = SELECT_ALL + " WHERE cu.name LIKE :name";
    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE cu.description LIKE :desc"; */

    public ConfigurationUnitDaoJpa() { /* None */}

    public ConfigurationUnitDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Optional<ConfigurationUnit> findById(Long id)
    {
        try {
            return Optional.ofNullable(em.find(ConfigurationUnit.class, id));
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<ConfigurationUnit> findAll()
    {
        try {
            return em.createNamedQuery(FIND_ALL, ConfigurationUnit.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ConfigurationUnit> findByName(String name)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_NAME, ConfigurationUnit.class)
                .setParameter("name", name)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by name: {} because had the exception {}", name, e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ConfigurationUnit> findByDescription(String desc)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_DESC, ConfigurationUnit.class)
                .setParameter("desc", desc)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by description: {} because had the exception {}", desc, e);
            return Collections.emptyList();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean save(ConfigurationUnit entity)
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
            LOGGER.error("Can't save cunit with id: {} because had the exception {}", entity.getId(), e);
            return false;
        }

        LOGGER.info("Save cunit with id: {}", entity.getId());
        return true;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        try {
            ConfigurationUnit merged = em.merge(findById(id).orElseThrow(NoResultException::new));
            em.remove(merged);
            LOGGER.info("Delete cunit with id: {}", merged.getId());
            return true;
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save cunit with id: {} because had the exception {}", id, e);
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
