/*
 * GroupResource.java
 * This file was last modified at 2019-02-16 19:16 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import io.swagger.annotations.*;
import su.svn.models.Group;
import su.svn.services.ResponseStorageService;
import su.svn.shared.Constants;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/v1" + Constants.Rest.GROUP_RESOURCE)
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
        name = "Operations about ITIL",
        description = "RESTful API to interact with Process management resource."
    )},
    host = "localhost:8080",
    basePath = "/ServiceItDesk/rest/api",
    schemes = {SwaggerDefinition.Scheme.HTTP}
)
@Api(tags = "Operations about ITIL")
public class GroupResource
{
    @EJB
    private ResponseStorageService storage;

    @POST
    @RolesAllowed(Constants.Security.ROLE_SUPERUSER)
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Add a new Group to the CMDB",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "create", description = "allows creating the Group")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Group object that needs to be added to the CMDB", required = true
    )})
    public Response create(Group entity, @Context HttpServletRequest servletRequest)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @GET
    @PermitAll
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation("Find ALL Groups")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    public Response readAll()
    {
        return storage.readAllGroups();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Find Group by ID",
        response = Group.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Group to return", dataType = "int", paramType = "path", required = true
    )})
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readById(Group.class, id.longValue());
    }

    @GET
    @Path("/{id}/users")
    @PermitAll
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Find Group by ID with own Users",
        response = Group.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Group to return", dataType = "int", paramType = "path", required = true
    )})
    public Response readWithUsers(@PathParam("id") Integer id)
    {
        return storage.readGroupByIdWithUsers(id.longValue());
    }

    @PUT
    @RolesAllowed(Constants.Security.ROLE_SUPERUSER)
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Update an existing Group",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "create", description = "allows creating the Group")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Group object that needs to be updated in the CMDB", required = true
    )})
    public Response update(Group entity, @Context HttpServletRequest servletRequest)
    {
        return storage.update(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed(Constants.Security.ROLE_SUPERUSER)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Deletes a Group",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "delete", description = "allows deleting of Group")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Group to delete", dataType = "int", paramType = "path", required = true
    )})
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(Group.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
