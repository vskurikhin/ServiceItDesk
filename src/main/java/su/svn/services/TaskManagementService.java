/*
 * TaskManagementService.java
 * This file was last modified at 2019-02-10 22:43 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import su.svn.models.dto.IncidentChangeStatusDTO;
import su.svn.models.dto.TaskChangeStatusDTO;

import javax.ws.rs.core.Response;

public interface TaskManagementService
{
    Response toWork(StringBuffer requestURL, TaskChangeStatusDTO task);

    Response addMessage(StringBuffer requestURL, TaskChangeStatusDTO task);

    Response resolution(StringBuffer requestURL, TaskChangeStatusDTO task);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
