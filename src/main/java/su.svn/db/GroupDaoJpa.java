/*
 * GroupDaoJpa.java
 * This file was last modified at 2019.01.23 20:05 by Victor N. Skurikhin.
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
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@TransactionAttribute(SUPPORTS)
public class GroupDaoJpa implements GroupDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    public static final String SELECT_ALL = "SELECT g FROM Group g";

    public static final String SELECT_WHERE_NAME = SELECT_ALL + " WHERE g.name LIKE :name";

    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE g.description LIKE :desc";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDaoJpa.class);

    public GroupDaoJpa() { /* None */}

    public GroupDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public List<Group> findByName(String value)
    {
        return em.createQuery(SELECT_WHERE_NAME, Group.class)
            .setParameter("name", value)
            .getResultList();
    }

    @Override
    public List<Group> findByDescription(String value)
    {
        return em.createQuery(SELECT_WHERE_DESC, Group.class)
            .setParameter("desc", value)
            .getResultList();
    }

    @Override
    public List<Group> findAll()
    {
        return em.createQuery(SELECT_ALL, Group.class).getResultList();
    }

    @Override
    public Group findById(Long id)
    {
        return em.find(Group.class, id);
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
        catch (EntityExistsException | IllegalArgumentException | TransactionRequiredException e) {
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
        return false;
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
