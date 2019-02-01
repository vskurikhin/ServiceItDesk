/*
 * Dao.java
 * This file was last modified at 2019.01.23 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import su.svn.models.DataSet;

import javax.ws.rs.core.Response;

public interface ResponseStorageService
{
    <E extends DataSet> Response create(StringBuffer requestURL, E entity);

    Response readAllGroups();

    Response readGroupById(Long id);

    Response readGroupByIdWithUsers(Long id);

    Response readAllUsers();

    Response readUserById(Long id);

    <E extends DataSet> Response update(StringBuffer requestURL, E entity);

    <E extends DataSet> Response delete(Class<E> clazz, Long id);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
