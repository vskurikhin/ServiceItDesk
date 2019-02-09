/*
 * GroupDTO.java
 * This file was last modified at 2019-02-09 19:53 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.models.dto;

import su.svn.models.Group;
import su.svn.models.PrimaryGroup;

public class GroupDTO
{
    public static PrimaryGroup create(Group g)
    {
        return new PrimaryGroup(g.getId(), g.getName(), g.getDescription());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
