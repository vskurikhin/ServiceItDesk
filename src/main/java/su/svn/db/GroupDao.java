/*
 * GroupDao.java
 * This file was last modified at 2019.01.23 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao extends Dao<Group, Long>
{
    Optional<Group> findByIdWithUsers(Long id);

    List<Group> findByName(String value);

    List<Group> findByDescription(String value);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
