/*
 * IncidentManagementService.java
 * This file was last modified at 2019-02-10 16:49 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import su.svn.models.dto.IncidentChangeStatusDTO;

import javax.ws.rs.core.Response;

public interface IncidentManagementService
{
    Response toWork(StringBuffer requestURL, IncidentChangeStatusDTO incident);

    Response addMessage(StringBuffer requestURL, IncidentChangeStatusDTO incident);

    Response resolution(StringBuffer requestURL, IncidentChangeStatusDTO incident);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
