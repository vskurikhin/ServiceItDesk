/*
 * IncidentDao.java
 * This file was last modified at 2019-02-09 12:31 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Incident;

import java.util.List;
import java.util.Optional;

public interface IncidentDao extends Dao<Incident, Long>
{
    Optional<Incident> findByIdWithDetails(Long id);

    List<Incident> findByTitle(String value);

    List<Incident> findByDescription(String value);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
