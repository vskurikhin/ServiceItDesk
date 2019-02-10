/*
 * PrimaryGroup.java
 * This file was last modified at 2019-02-09 20:15 by Victor N. Skurikhin.
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

import static su.svn.models.PrimaryGroup.FIND_ALL;
import static su.svn.models.PrimaryGroup.FIND_ALL_WHERE_DESC;
import static su.svn.models.PrimaryGroup.FIND_ALL_WHERE_NAME;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cm_group")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT g FROM Group g ORDER BY g.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_NAME,
        query = "SELECT DISTINCT g FROM Group g WHERE g.name LIKE :name ORDER BY g.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT DISTINCT g FROM Group g WHERE g.description LIKE :desc ORDER BY g.id"
    ),
})
public class PrimaryGroup implements DataSet
{
    public static final String FIND_ALL = "PrimaryGroup.findAll";
    public static final String FIND_ALL_WHERE_NAME = "PrimaryGroup.findAllWhereName";
    public static final String FIND_ALL_WHERE_DESC = "PrimaryGroup.findAllWhereDescription";

    @Id
    @SequenceGenerator(name = "group_identifier", sequenceName = "group_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "group_identifier")
    @Column(name = "group_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    public static boolean isValidForSave(PrimaryGroup group)
    {
        if (Objects.isNull(group)) {
            return false;
        }
        if (Objects.isNull(group.id)) {
            return false;
        }
        return !Objects.isNull(group.name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
