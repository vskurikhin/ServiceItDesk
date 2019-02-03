/*
 * ConfigurationUnitResource.java
 * This file was last modified at 2019-02-03 11:46 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import su.svn.models.ConfigurationUnit;
import su.svn.services.ResponseStorageService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static su.svn.rest.config.RestApplication.CONFIGURATION_UNIT_RESOURCE;

@Stateless
@Path("/v1" + CONFIGURATION_UNIT_RESOURCE)
@Produces(MediaType.APPLICATION_JSON)
public class ConfigurationUnitResource
{
    @Context
    private HttpServletRequest servletRequest;

    @EJB
    private ResponseStorageService storage;

    @POST
    public Response create(ConfigurationUnit entity)
    {
        return storage.create(servletRequest.getRequestURL(), entity);
    }

    @POST
    @Path("/admin/owner")
    public Response createWithAdminAndOwner(ConfigurationUnit entity)
    {
        return storage.createConfigurationUnit(servletRequest.getRequestURL(), entity);
    }

    @GET
    public Response readAll()
    {
        return storage.readAllConfigurationUnits();
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Integer id)
    {
        return storage.readConfigurationUnitById(id.longValue());
    }

    @PUT
    public Response update(ConfigurationUnit entity)
    {
        return storage.update(servletRequest.getRequestURL(), entity);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id)
    {
        return storage.delete(ConfigurationUnit.class, id.longValue());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF