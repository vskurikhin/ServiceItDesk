/*
 * GroupResource.java
 * This file was last modified at 2019-02-04 23:32 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import su.svn.models.Group;
import su.svn.services.ResponseStorageService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.rest.config.RestApplication.GROUP_RESOURCE;

@Stateless
@Path("/v1" + GROUP_RESOURCE)
public class GroupResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response create(Group entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response readAll()
    {
        return storage.readAllGroups();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readById(Group.class, id.longValue());
    }

    @GET
    @Path("/{id}/users")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response readWithUsers(@PathParam("id") Integer id)
    {
        return storage.readGroupByIdWithUsers(id.longValue());
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response update(Group entity)
    {
        return storage.update(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(Group.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
