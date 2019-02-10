/*
 * ConfigurationType.java
 * This file was last modified at 2019-02-09 20:08 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Objects;

import static su.svn.models.ConfigurationType.FIND_ALL;
import static su.svn.models.ConfigurationType.FIND_ALL_WHERE_DESC;
import static su.svn.models.ConfigurationType.FIND_ALL_WHERE_NAME;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cm_ctype")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT ct FROM ConfigurationType ct ORDER BY ct.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_NAME,
        query = "SELECT DISTINCT ct FROM ConfigurationType ct WHERE ct.name LIKE :name ORDER BY ct.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT DISTINCT ct FROM ConfigurationType ct WHERE ct.description LIKE :desc ORDER BY ct.id"
    ),
})
public class ConfigurationType implements DataSet
{
    public static final String FIND_ALL = "ConfigurationType.findAll";
    public static final String FIND_ALL_WHERE_NAME = "ConfigurationType.findAllWhereName";
    public static final String FIND_ALL_WHERE_DESC = "ConfigurationType.findAllWhereDescription";

    @Id
    @SequenceGenerator(name = "ctype_identifier", sequenceName = "ctype_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ctype_identifier")
    @Column(name = "ctype_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "ctype_name", nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    public static boolean isValidForSave(ConfigurationType configurationType)
    {
        if (Objects.isNull(configurationType)) {
            return false;
        }
        if (Objects.isNull(configurationType.id)) {
            return false;
        }
        return !Objects.isNull(configurationType.name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
