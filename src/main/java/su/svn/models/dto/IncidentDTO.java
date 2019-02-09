/*
 * IncidentDTO.java
 * This file was last modified at 2019-02-09 19:41 by Victor N. Skurikhin.
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
public class IncidentDTO implements DataSet
{
    private Long id = 0L;

    private String title;

    private String description;

    private User consumer;

    private Status status;

    public static IncidentDTO create(Incident i)
    {
        User consumer = new User();
        consumer.setId(i.getConsumer().getId());
        consumer.setName(i.getConsumer().getName());
        consumer.setDescription(i.getConsumer().getDescription());

        return new IncidentDTO(i.getId(), i.getTitle(), i.getDescription(), consumer, i.getStatus());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
