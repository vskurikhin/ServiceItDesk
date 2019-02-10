/*
 * TaskDao.java
 * This file was last modified at 2019-02-10 20:38 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao extends Dao<Task, Long>
{
    Optional<Task> findByIdWithDetails(Long id);

    List<Task> findByTitle(String value);

    List<Task> findByDescription(String value);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
