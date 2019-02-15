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
        title = "Configuration management database RESTful API",
        description = "This is a sample configuration management database service.",
        version = "1.0.0",
        termsOfService = "share and care",
        contact = @Contact(
            name = "Victor", email = "vskurikhin@gmail.com",
            url = "https://itdesk.svn.su"),
        license = @License(
            name = "This is free and unencumbered software released into the public domain.",
            url = "http://unlicense.org")),
    tags = {@Tag(
        name = "Configuration types Resource",
        description = "RESTful API to interact with configuration types resource."
    )},
    basePath = "/ServiceItDesk/rest/api",
    schemes = {SwaggerDefinition.Scheme.HTTP}
)
@Api(tags = "Operations about Configuration Unit Resource")
public class ConfigurationTypeResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation("Add a new Configuration Type to the CMDB")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 406, message = "Not Acceptable")
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Configuration Type object that needs to be added to the CMDB", required = true
    )})
    public Response create(ConfigurationType entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation("Find ALL Configuration Types")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Response readAll()
    {
        return storage.readAll(ConfigurationType.class);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    @ApiOperation(value = "Find Configuration Type by ID",
        notes = "Returns a single Configuration Type",
        response = ConfigurationType.class,
        authorizations = @Authorization(value = "api_key")
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Configuration Type to return", dataType = "int", paramType = "path", required = true
    )})
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readById(ConfigurationType.class, id.longValue());
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation("Update an existing Configuration Types")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 406, message = "Not Acceptable")
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Configuration Type object that needs to be updated in the CMDB", required = true
    )})
    public Response update(ConfigurationType entity)
    {
        return storage.update(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    @ApiOperation(value = "Deletes a Configuration")
    @ApiResponses({ @ApiResponse(code = 204, message = "No Content") })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Configuration Type to delete", dataType = "int", paramType = "path", required = true
    )})
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(ConfigurationType.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
