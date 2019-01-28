/*
 * StatusDao.java
 * This file was last modified at 2019-01-26 11:07 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Status;

import java.util.List;

public interface StatusDao extends Dao<Status, Long>
{
    List<Status> findByStatus(String value);

    List<Status> findByDescription(String value);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
