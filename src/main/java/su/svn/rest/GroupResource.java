/*
 * GroupResource.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import su.svn.db.GroupDao;
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
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource extends CRUDResource<Group, GroupDao>
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private GroupDao dao;

    @EJB
    private ResponseStorageService storage;

    @Override
    public GroupDao getDao()
    {
        return dao;
    }

    @Override
    public HttpServletRequest getHttpServletRequest()
    {
        return servletRequest;
    }

    @POST
    public Response create(Group entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @GET
    public Response readAll()
    {
        return storage.readAllGroups();
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readGroupById(id.longValue());
    }

    @GET
    @Path("/{id}/users")
    public Response readWithUsers(@PathParam("id") Integer id)
    {
        return storage.readGroupById(id.longValue());
        // return Response.ok(getDao().findByIdWithUsers(id.longValue())).build();
    }

    @PUT
    public Response update(Group entity)
    {
        return storage.update(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.deleteGroup(id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
