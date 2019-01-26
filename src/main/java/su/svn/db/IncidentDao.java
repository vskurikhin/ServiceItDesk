/*
 * IncidentDao.java
 * This file was last modified at 2019-01-26 17:11 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Incident;

import java.util.List;

public interface IncidentDao extends Dao<Incident, Long>
{
    List<Incident> findByName(String value);

    List<Incident> findByDescription(String value);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
