/*
 * IncidentResource.java
 * This file was last modified at 2019-02-10 23:01 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import su.svn.models.Incident;
import su.svn.models.dto.IncidentChangeStatusDTO;
import su.svn.services.IncidentManagementService;
import su.svn.services.ResponseStorageService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.shared.Constants.Rest.INCIDENT_RESOURCE;

@Stateless
@Path("/v1" + INCIDENT_RESOURCE)
public class IncidentResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @EJB
    private IncidentManagementService management;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response create(Incident entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/consumer/status")
    public Response createWithAdminAndOwner(Incident entity)
    {
        return storage.createIncident(servletRequest.getRequestURL(), entity);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response readAll()
    {
        return storage.readAllIncidents();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readIncidentById(id.longValue());
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/messages")
    public Response readWithMessages(@PathParam("id") Integer id)
    {
        return storage.readIncidentByIdWithMessages(id.longValue());
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response update(Incident entity)
    {
        return storage.updateIncident(servletRequest.getRequestURL(), entity);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/to-work")
    public Response toWork(IncidentChangeStatusDTO entity)
    {
        return management.toWork(servletRequest.getRequestURL(), entity);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/add-message")
    public Response addMessage(IncidentChangeStatusDTO entity)
    {
        return management.addMessage(servletRequest.getRequestURL(), entity);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/resolution")
    public Response resolution(IncidentChangeStatusDTO entity)
    {
        return management.resolution(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(Incident.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
