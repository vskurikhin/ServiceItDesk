/*
 * TaskResource.java
 * This file was last modified at 2019-02-10 22:59 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import su.svn.models.Task;
import su.svn.models.dto.TaskChangeStatusDTO;
import su.svn.services.ResponseStorageService;
import su.svn.services.TaskManagementService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.shared.Constants.Rest.TASK_RESOURCE;

@Stateless
@Path("/v1" + TASK_RESOURCE)
public class TaskResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @EJB
    private TaskManagementService management;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response create(Task entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/consumer/status")
    public Response createWithAdminAndOwner(Task entity)
    {
        return storage.createTask(servletRequest.getRequestURL(), entity);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response readAll()
    {
        return storage.readAllTasks();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readTaskById(id.longValue());
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/messages")
    public Response readWithMessages(@PathParam("id") Integer id)
    {
        return storage.readTaskByIdWithMessages(id.longValue());
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response update(Task entity)
    {
        return storage.updateTask(servletRequest.getRequestURL(), entity);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/to-work")
    public Response toWork(TaskChangeStatusDTO entity)
    {
        return management.toWork(servletRequest.getRequestURL(), entity);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/add-message")
    public Response addMessage(TaskChangeStatusDTO entity)
    {
        return management.addMessage(servletRequest.getRequestURL(), entity);
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}/resolution")
    public Response resolution(TaskChangeStatusDTO entity)
    {
        return management.resolution(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(Task.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
