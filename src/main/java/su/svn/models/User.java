/*
 * User.java
 * This file was last modified at 2019-02-03 14:22 by Victor N. Skurikhin.
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

import static su.svn.models.User.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cm_user")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT u FROM User u JOIN FETCH u.group"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_NAME,
        query = "SELECT u FROM User u JOIN FETCH u.group WHERE u.name LIKE :name"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT u FROM User u JOIN FETCH u.group WHERE u.description LIKE :desc"
    ),
    @NamedQuery(
        name = FIND_BY_ID_WITH_DETAILS,
        query = "SELECT DISTINCT u FROM User u JOIN FETCH u.group WHERE u.id = :id"
    ),
})
public class User implements DataSet
{
    public static final String FIND_ALL = "User.findAll";
    public static final String FIND_ALL_WHERE_NAME = "User.findAllWhereName";
    public static final String FIND_ALL_WHERE_DESC = "User.findAllWhereDescription";
    public static final String FIND_BY_ID_WITH_DETAILS = "User.findByIdWithDetails";

    @Id
    @SequenceGenerator(name = "user_identifier", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_identifier")
    @Column(name = "user_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "user_name", nullable = false, unique = true)
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "primary_group_id", nullable = false)
    private PrimaryGroup group;

    public static boolean isValidForSave(User user)
    {
        if (Objects.isNull(user)) {
            return false;
        }
        if (Objects.isNull(user.id)) {
            return false;
        }
        if (Objects.isNull(user.group)) {
            return false;
        }
        return !Objects.isNull(user.name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
