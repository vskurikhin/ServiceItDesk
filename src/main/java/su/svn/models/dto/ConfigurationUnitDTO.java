/*
 * ConfigurationUnitDTO.java
 * This file was last modified at 2019-02-09 20:03 by Victor N. Skurikhin.
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
public class ConfigurationUnitDTO implements DataSet
{
    private Long id = 0L;

    private String name;

    private String description;

    private User admin;

    private User owner;

    private PrimaryGroup group;

    private ConfigurationType type;

    public static ConfigurationUnitDTO create(ConfigurationUnit i)
    {
        User admin = new User();
        admin.setId(i.getAdmin().getId());
        admin.setName(i.getAdmin().getName());
        admin.setDescription(i.getAdmin().getDescription());

        User owner = new User();
        owner.setId(i.getAdmin().getId());
        owner.setName(i.getAdmin().getName());
        owner.setDescription(i.getAdmin().getDescription());

        PrimaryGroup group = GroupDTO.create(i.getGroup());

        return new ConfigurationUnitDTO(i.getId(), i.getName(), i.getDescription(), admin,  owner, group, i.getType());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
