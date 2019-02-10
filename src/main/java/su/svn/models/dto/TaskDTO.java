/*
 * TaskDTO.java
 * This file was last modified at 2019-02-10 20:30 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import su.svn.models.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TaskDTO implements DataSet
{
    private Long id = 0L;

    private String title;

    private String description;

    private User consumer;

    private Status status;

    public static TaskDTO create(Task t)
    {
        User consumer = new User();
        consumer.setId(t.getConsumer().getId());
        consumer.setName(t.getConsumer().getName());
        consumer.setDescription(t.getConsumer().getDescription());

        return new TaskDTO(t.getId(), t.getTitle(), t.getDescription(), consumer, t.getStatus());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
