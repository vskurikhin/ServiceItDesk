/*
 * UserResource.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import su.svn.db.PrimaryGroupDao;
import su.svn.db.UserDao;
import su.svn.models.PrimaryGroup;
import su.svn.models.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.exceptions.ExceptionsFabric.getWebApplicationException;
import static su.svn.rest.config.RestApplication.USER_RESOURCE;

@Stateless
@Path("/v1" + USER_RESOURCE)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource extends CRUDResource<User, UserDao>
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private UserDao dao;

    @EJB
    private PrimaryGroupDao groupDao;

    @Override
    public UserDao getDao()
    {
        return dao;
    }

    @Override
    public HttpServletRequest getHttpServletRequest()
    {
        return servletRequest;
    }

    @POST
    public Response create(User entity)
    {
        PrimaryGroup group = entity.getGroup();

        if (0 == group.getId()) {
            groupDao.save(group);
        }
        else {
            entity.setGroup(groupDao.findById(group.getId()));
        }

        return createSave(entity);
    }

    @GET
    public Response readAll()
    {
        return Response.ok(getDao().findAllWithDetails()).build();
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        User entity = getDao().findByIdWithDetails(id.longValue());

        if (null != entity) {
            return Response.ok(entity).build();
        }

        throw getWebApplicationException(new Throwable("Not Found!"));
    }

    @PUT
    public Response update(User entity)
    {
        PrimaryGroup group = entity.getGroup();

        if (0 == group.getId()) {
            groupDao.save(group);
        }
        else {
            entity.setGroup(groupDao.findById(group.getId()));
        }

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
