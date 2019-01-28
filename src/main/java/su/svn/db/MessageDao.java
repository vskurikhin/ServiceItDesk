/*
 * MessageDao.java
 * This file was last modified at 2019.01.23 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.db;

import su.svn.models.Message;

import java.util.List;

public interface MessageDao extends Dao<Message, Long>
{
    List<Message> findByText(String value);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
