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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.stream.Collectors;

import static su.svn.exceptions.ExceptionsFabric.getWebApplicationException;
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
        return createSave(entity);
    }

    @GET
    public Response readAll()
    {
        return Response.ok(
            getDao().findAll()
                .stream()
                .peek(g -> g.setUsers(null))
                .collect(Collectors.toList())
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        Group entity = getDao().findById(id.longValue());

        if (null != entity) {
            entity.setUsers(null);
            return Response.ok(entity).build();
        }

        throw getWebApplicationException(new Throwable("Not Found!"));
    }

    @GET
    @Path("/{id}/users")
    public Response readWithUsers(@PathParam("id") Integer id)
    {
        Group entity = getDao().findByIdWithUsers(id.longValue());

        if (null != entity) {
            return Response.ok(entity).build();
        }

        throw getWebApplicationException(new Throwable("Not Found!"));
    }

    @PUT
    public Response update(Group entity)
    {
        return updateSave(entity);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return deleteById(id);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
