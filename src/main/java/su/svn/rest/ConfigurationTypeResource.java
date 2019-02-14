/*
 * ConfigurationTypeResource.java
 * This file was last modified at 2019-02-14 21:21 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import io.swagger.annotations.*;
import su.svn.models.ConfigurationType;
import su.svn.services.ResponseStorageService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.shared.Constants.Rest.CONFIGURATION_TYPE_RESOURCE;

@Stateless
@Path("/v1" + CONFIGURATION_TYPE_RESOURCE)
@SwaggerDefinition(
    info = @Info(
        title = "Swagger-generated RESTful API",
        description = "RESTful Description XXX",
        version = "1.0.0",
        termsOfService = "share and care",
        contact = @Contact(
            name = "Victor", email = "vskurikhin@gmail.com",
            url = "https://svn.su"),
        license = @License(
            name = "This is free and unencumbered software released into the public domain.",
            url = "http://unlicense.org")),
    tags = {
        @Tag(name = "XXX Resource", description = "RESTful API to interact with Annuity Pay resource.")
    },
    basePath = "/ServiceItDesk/rest/api/v1" + CONFIGURATION_TYPE_RESOURCE,
    schemes = {SwaggerDefinition.Scheme.HTTP},
    externalDocs = @ExternalDocs(
        value = "Developing a Swagger-enabled REST API using WebSphere Developer Tools",
        url = "https://tinyurl.com/swagger-wlp")
)
@Api(tags = "XXX Resource")
public class ConfigurationTypeResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation("create")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK")
    })
    public Response create(ConfigurationType entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response readAll()
    {
        return storage.readAll(ConfigurationType.class);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    @ApiOperation("read")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK")
    })
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "Number of XXX", dataType = "int", paramType = "path"),
    })
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readById(ConfigurationType.class, id.longValue());
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response update(ConfigurationType entity)
    {
        return storage.update(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(ConfigurationType.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
