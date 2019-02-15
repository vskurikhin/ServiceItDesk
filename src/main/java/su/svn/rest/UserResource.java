/*
 * UserResource.java
 * This file was last modified at 2019-02-14 21:15 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import io.swagger.annotations.*;
import su.svn.models.User;
import su.svn.services.ResponseStorageService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.shared.Constants.Rest.USER_RESOURCE;

@Stateless
@Path("/v1" + USER_RESOURCE)
@SwaggerDefinition(
    info = @Info(
        title = "Process management RESTful API",
        description = "This is a sample process management service.",
        version = "1.0.0",
        termsOfService = "share and care",
        contact = @Contact(
            name = "Victor", email = "vskurikhin@gmail.com",
            url = "https://itdesk.svn.su"),
        license = @License(
            name = "This is free and unencumbered software released into the public domain.",
            url = "http://unlicense.org")),
    tags = {@Tag(
        name = "Users Resource",
        description = "RESTful API to interact with users resource."
    )},
    basePath = "/ServiceItDesk/rest/api",
    schemes = {SwaggerDefinition.Scheme.HTTP}
)
public class UserResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response create(User entity)
    {
        return storage.createUser(servletRequest.getRequestURL(), entity);
    }

    @POST
    @Path("/group")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createWithGroup(User entity)
    {
        return storage.createUser(servletRequest.getRequestURL(), entity);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response readAll()
    {
        return storage.readAll(User.class);
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readById(User.class, id.longValue());
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response update(User entity)
    {
        return storage.updateUser(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(User.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
