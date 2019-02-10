/*
 * TaskAddMessageDTO.java
 * This file was last modified at 2019-02-10 22:37 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import su.svn.models.DataSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaskAddMessageDTO implements DataSet
{
    private Long id = 0L;

    private String message;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
