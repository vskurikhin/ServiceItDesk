/*
 * CRUDResource.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.db.Dao;
import su.svn.models.DataSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.net.URI;

import static su.svn.exceptions.ExceptionsFabric.getWebApplicationException;
import static su.svn.utils.UniformResource.getRequestURL;

public abstract class CRUDResource<E extends DataSet, D extends Dao<E, Long>>
{
    public abstract D getDao();

    public abstract HttpServletRequest getHttpServletRequest();

    private static final Logger LOGGER = LoggerFactory.getLogger(CRUDResource.class);

    public URI getLocation(E entity)
    {
        return URI.create(getRequestURL(getHttpServletRequest()) + '/' + entity.getId());
    }

    // POST
    public Response createSave(E entity)
    {
        if (0 != entity.getId()) {
            LOGGER.warn("Try create with id: {} != 0", entity.getId());
            entity.setId(0L);
        }

        if (getDao().save(entity)) {
            return Response.created(getLocation(entity)).build();
        }
        throw getWebApplicationException(new Throwable("Error create!"));
    }

    // GET
    public Response findAll()
    {
        return Response.ok(getDao().findAll()).build();
    }

    // GET
    // Path("/{id}")
    public Response findById(Integer id)
    {
        E entity = getDao().findById(id.longValue());

        if (null != entity) {
            return Response.ok(entity).build();
        }

        throw getWebApplicationException(new Throwable("Not Found!"));
    }

    // PUT
    public Response updateSave(E entity)
    {
        if (getDao().save(entity)) {
            return Response.ok(getLocation(entity)).build();
        }
        throw getWebApplicationException(new Throwable("Error update!"));
    }

    // DELETE
    // Path("/{id}")
    public Response deleteById(Integer id)
    {
        if (getDao().delete(id.longValue())) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        throw getWebApplicationException(new Throwable("Error delete!"));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
