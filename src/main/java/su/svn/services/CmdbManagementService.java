/*
 * CmdbManagementService.java
 * This file was last modified at 2019-02-04 00:34 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import su.svn.models.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

public interface CmdbManagementService
{
    void getGroup(HttpServletRequest req, HttpServletResponse resp);

    void getGroups(HttpServletRequest req, HttpServletResponse resp);

    void getUser(HttpServletRequest req, HttpServletResponse resp);

    void getUsers(HttpServletRequest req, HttpServletResponse resp);

    void putGroup(HttpServletRequest req, HttpServletResponse resp);

    void putUser(HttpServletRequest req, HttpServletResponse resp);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
