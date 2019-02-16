/*
 * TaskResource.java
 * This file was last modified at 2019-02-16 19:16 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import io.swagger.annotations.*;
import su.svn.models.Task;
import su.svn.models.dto.TaskChangeStatusDTO;
import su.svn.services.ResponseStorageService;
import su.svn.services.TaskManagementService;
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
@Path("/v1" + Constants.Rest.TASK_RESOURCE)
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
public class TaskResource
{
    @EJB
    private ResponseStorageService storage;

    @EJB
    private TaskManagementService management;

    @POST
    @RolesAllowed({
        Constants.Security.ROLE_ACTUARY, Constants.Security.ROLE_ADMIN, Constants.Security.ROLE_COORDINATOR
    })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Create a Task",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "create", description = "allows creating a Task")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Task object that needs to be created", required = true
    )})
    public Response create(Task entity, @Context HttpServletRequest request)
    {
        return storage.create(request.getRequestURL(), entity);
    }

    @POST
    @Path("/consumer/status")
    @RolesAllowed({ Constants.Security.ROLE_ADMIN, Constants.Security.ROLE_COORDINATOR })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Create a Task with a consumer and status",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "create", description = "allows creating a Task")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 201, message = "Created"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Task object that needs to be created", required = true
    )})
    public Response createWithOwnerAndStatus(Task entity, @Context HttpServletRequest request)
    {
        return storage.createTask(request.getRequestURL(), entity);
    }

    @GET
    @PermitAll
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation("Find ALL Incidents")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    public Response readAll()
    {
        return storage.readAllTasks();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(value = "Find Task by ID",
        notes = "Returns a single Task",
        response = Task.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Task to return", dataType = "int", paramType = "path", required = true
    )})
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readTaskById(id.longValue());
    }

    @GET
    @Path("/{id}/messages" )
    @PermitAll
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(value = "Find Task by ID with own messages",
        notes = "Returns a single Task with own messages",
        response = Task.class
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 404, message = "Not Found")
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Task to return", dataType = "int", paramType = "path", required = true
    )})
    public Response readWithMessages(@PathParam("id") Integer id)
    {
        return storage.readTaskByIdWithMessages(id.longValue());
    }

    @PUT
    @RolesAllowed({ Constants.Security.ROLE_ADMIN, Constants.Security.ROLE_COORDINATOR })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Update an existing Task",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "update", description = "allows updating the Task")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Task object that needs to be updated", required = true
    )})
    public Response update(Task entity, @Context HttpServletRequest request)
    {
        return storage.updateTask(request.getRequestURL(), entity);
    }

    @PUT
    @Path("/{id}/to-work")
    @RolesAllowed({ Constants.Security.ROLE_ADMIN, Constants.Security.ROLE_COORDINATOR })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Take the existing Task to work",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "update", description = "allows updating the Group")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Task object that needs to be updated", required = true
    )})
    public Response toWork(TaskChangeStatusDTO entity, @Context HttpServletRequest request)
    {
        return management.toWork(request.getRequestURL(), entity);
    }

    @PUT
    @Path("/{id}/add-message")
    @RolesAllowed({ Constants.Security.ROLE_ADMIN, Constants.Security.ROLE_COORDINATOR })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Add message to the existing Task",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "update", description = "allows updating the Task")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Task object that needs to be updated", required = true
    )})
    public Response addMessage(TaskChangeStatusDTO entity, @Context HttpServletRequest request)
    {
        return management.addMessage(request.getRequestURL(), entity);
    }

    @PUT
    @Path("/{id}/resolution")
    @RolesAllowed({ Constants.Security.ROLE_ADMIN, Constants.Security.ROLE_COORDINATOR })
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Resolve the existing Task",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "update", description = "allows updating the Task")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 406, message = "Not Acceptable"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "entity", value = "The Task object that needs to be updated", required = true
    )})
    public Response resolution(TaskChangeStatusDTO entity, @Context HttpServletRequest request)
    {
        return management.resolution(request.getRequestURL(), entity);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ Constants.Security.ROLE_ADMIN, Constants.Security.ROLE_COORDINATOR })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @ApiOperation(
        value = "Deletes an Incident",
        authorizations = @Authorization(value = "Bearer", scopes = {
            @AuthorizationScope(scope = "delete", description = "allows deleting of Task")
        })
    )
    @ApiResponses({
        @ApiResponse(code = 204, message = "No Content"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiImplicitParams({@ApiImplicitParam(
        name = "id", value = "ID of Task to delete", dataType = "int", paramType = "path", required = true
    )})
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(Task.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
