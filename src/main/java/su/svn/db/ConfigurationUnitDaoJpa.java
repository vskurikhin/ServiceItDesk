/*
 * ConfigurationUnitDaoJpa.java
 * This file was last modified at 2019-02-03 17:08 by Victor N. Skurikhin.
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

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static su.svn.models.ConfigurationUnit.*;
import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
@TransactionAttribute(SUPPORTS)
public class ConfigurationUnitDaoJpa implements ConfigurationUnitDao
{
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUnitDaoJpa.class);

    public ConfigurationUnitDaoJpa() { /* None */}

    ConfigurationUnitDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Optional<ConfigurationUnit> findById(Long id)
    {
        return findByIdWithDetails(id);
    }

    @Override
    public List<ConfigurationUnit> findAll()
    {
        try {
            return em.createNamedQuery(FIND_ALL, ConfigurationUnit.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception {}", e.toString());
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<ConfigurationUnit> findByIdWithDetails(Long id)
    {
        try {
            return Optional.of(
                em.createNamedQuery(FIND_BY_ID_WITH_DETAILS, ConfigurationUnit.class)
                    .setParameter("id", id)
                    .getSingleResult()
            );
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e.toString());
            return Optional.empty();
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
    @TransactionAttribute(REQUIRED)
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
    @TransactionAttribute(REQUIRED)
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
