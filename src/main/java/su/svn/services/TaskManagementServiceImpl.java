/*
 * TaskManagementServiceImpl.java
 * This file was last modified at 2019-02-10 22:46 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.db.MessageDao;
import su.svn.db.StatusDao;
import su.svn.db.TaskDao;
import su.svn.models.Message;
import su.svn.models.Status;
import su.svn.models.Task;
import su.svn.models.dto.TaskChangeStatusDTO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static su.svn.utils.UniformResource.getLocation;

@Stateless
public class TaskManagementServiceImpl implements TaskManagementService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskManagementServiceImpl.class);

    @EJB
    private TaskDao taskDao;

    @EJB
    private MessageDao messageDao;

    @EJB
    private StatusDao statusDao;

    @EJB
    private ResponseStorageService storage;

    boolean checkStatus(long currentStatus, long newStatus) {
        return 0 == newStatus || 1 == (newStatus - currentStatus);
    }

    Message changeStatus(StringBuffer requestURL, TaskChangeStatusDTO task)
    {
            Optional<Task> optionalIncident = taskDao.findByIdWithDetails(task.getId());

            if ( ! optionalIncident.isPresent()) {
                throw new PersistenceException("can't find task id " + task.getId() + '!');
            }
            Task updatedTask = optionalIncident.get();

            if (0 != task.getStatusId()) {
                Optional<Status> optionalStatus = statusDao.findById(task.getStatusId());

                if ( ! optionalStatus.isPresent()) {
                    throw new PersistenceException("can't find status id " + task.getStatusId() + '!');
                }

                if ( ! checkStatus(updatedTask.getStatus().getId(), task.getStatusId()) ) {
                    throw new PersistenceException("bad status change!");
                }
                updatedTask.setStatus(optionalStatus.get());
            }

            Message message = new Message();
            message.setText(task.getMessage());
            messageDao.save(message);
            updatedTask.getMessages().add(message);
            storage.update(requestURL, updatedTask);

            return message;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response toWork(StringBuffer requestURL, TaskChangeStatusDTO task)
    {
        try {
            changeStatus(requestURL, task);

            return Response.ok(URI.create(requestURL.toString())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try to work Task: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response addMessage(StringBuffer requestURL, TaskChangeStatusDTO task)
    {
        try {
            Message message = changeStatus(requestURL, task);

            return Response.ok(getLocation(requestURL, message.getId())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try add message to Task: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response resolution(StringBuffer requestURL, TaskChangeStatusDTO task)
    {
        try {
            changeStatus(requestURL, task);

            return Response.ok(getLocation(requestURL, task.getId())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try to close Task: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
