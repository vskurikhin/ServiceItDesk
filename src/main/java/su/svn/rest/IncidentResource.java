/*
 * IncidentResource.java
 * This file was last modified at 2019-02-03 16:10 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import su.svn.models.Incident;
import su.svn.services.ResponseStorageService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.rest.config.RestApplication.INCIDENT_RESOURCE;

@Stateless
@Path("/v1" + INCIDENT_RESOURCE)
@Produces(MediaType.APPLICATION_JSON)
public class IncidentResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @POST
    public Response create(Incident entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @POST
    @Path("/consumer/status")
    public Response createWithAdminAndOwner(Incident entity)
    {
        return storage.createIncident(servletRequest.getRequestURL(), entity);
    }

    @GET
    public Response readAll()
    {
        return storage.readAll(Incident.class);
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readById(Incident.class, id.longValue());
    }

    @PUT
    public Response update(Incident entity)
    {
        return storage.update(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(Incident.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
