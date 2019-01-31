/*
 * MessageDaoJpa.java
 * This file was last modified at 2019-01-26 18:10 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Message;

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
import static su.svn.models.Message.FIND_ALL;
import static su.svn.models.Message.FIND_ALL_WHERE_TEXT;

@Stateless
@TransactionAttribute(SUPPORTS)
public class MessageDaoJpa implements MessageDao
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDaoJpa.class);

    /* public static final String SELECT_ALL = "SELECT m FROM Message m";
    public static final String SELECT_WHERE_TEXT = SELECT_ALL + " WHERE m.text LIKE :text"; */

    public MessageDaoJpa() { /* None */}

    public MessageDaoJpa(EntityManager entityManager)
    {
        em = entityManager;
    }

    @Override
    public Optional<Message> findById(Long id)
    {
        try {
            return Optional.ofNullable(em.find(Message.class, id));
        }
        catch (IllegalArgumentException e) {
            LOGGER.error("Can't search by id: {} because had the exception {}", id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Message> findAll()
    {
        try {
            return em.createNamedQuery(FIND_ALL, Message.class).getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all because had the exception ", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<Message> findByText(String value)
    {
        try {
            return em.createNamedQuery(FIND_ALL_WHERE_TEXT, Message.class)
                .setParameter("text", value)
                .getResultList();
        }
        catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search by text: {} because had the exception {}", value, e);
            return Collections.emptyList();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean save(Message entity)
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
            LOGGER.error("Can't save message with id: {} because had the exception {}", entity.getId(), e);
            return false;
        }

        LOGGER.info("Save message with id: {}", entity.getId());
        return true;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        try {
            Message merged = em.merge(findById(id).orElseThrow(NoResultException::new));
            em.remove(merged);
            LOGGER.info("Delete message with id: {}", merged.getId());
            return true;
        }
        catch (IllegalArgumentException | PersistenceException e) {
            LOGGER.error("Can't save message with id: {} because had the exception {}", id, e);
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
