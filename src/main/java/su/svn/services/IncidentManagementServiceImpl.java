/*
 * IncidentManagementServiceImpl.java
 * This file was last modified at 2019-02-10 16:47 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.db.*;
import su.svn.models.*;
import su.svn.models.dto.IncidentChangeStatusDTO;

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
public class IncidentManagementServiceImpl implements IncidentManagementService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(IncidentManagementServiceImpl.class);

    @EJB
    private IncidentDao incidentDao;

    @EJB
    private MessageDao messageDao;

    @EJB
    private StatusDao statusDao;

    @EJB
    private ResponseStorageService storage;

    boolean checkStatus(long currentStatus, long newStatus) {
        return 0 == newStatus || 1 == (newStatus - currentStatus);
    }

    Message changeStatus(StringBuffer requestURL, IncidentChangeStatusDTO incident)
    {
            Optional<Incident> optionalIncident = incidentDao.findByIdWithDetails(incident.getId());

            if ( ! optionalIncident.isPresent()) {
                throw new PersistenceException("can't find incident id " + incident.getId() + '!');
            }
            Incident updatedIncident = optionalIncident.get();

            if (0 != incident.getStatusId()) {
                Optional<Status> optionalStatus = statusDao.findById(incident.getStatusId());

                if ( !optionalStatus.isPresent()) {
                    throw new PersistenceException("can't find status id " + incident.getStatusId() + '!');
                }

                if ( ! checkStatus(updatedIncident.getStatus().getId(), incident.getStatusId()) ) {
                    throw new PersistenceException("bad status change!");
                }
                updatedIncident.setStatus(optionalStatus.get());
            }

            Message message = new Message();
            message.setText(incident.getMessage());
            messageDao.save(message);
            updatedIncident.getMessages().add(message);
            storage.update(requestURL, updatedIncident);

            return message;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response toWork(StringBuffer requestURL, IncidentChangeStatusDTO incident)
    {
        try {
            changeStatus(requestURL, incident);

            return Response.ok(URI.create(requestURL.toString())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try to work Incident: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response addMessage(StringBuffer requestURL, IncidentChangeStatusDTO incident)
    {
        try {
            Message message = changeStatus(requestURL, incident);

            return Response.ok(getLocation(requestURL, message.getId())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try add message to Incident: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response resolution(StringBuffer requestURL, IncidentChangeStatusDTO incident)
    {
        try {
            changeStatus(requestURL, incident);

            return Response.ok(getLocation(requestURL, incident.getId())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try to close Incident: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
