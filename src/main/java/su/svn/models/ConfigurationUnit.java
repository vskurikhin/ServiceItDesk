/*
 * ConfigurationUnit.java
 * This file was last modified at 2019-02-09 20:12 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import su.svn.services.adapters.GroupAdapter;
import su.svn.services.adapters.UserAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;

import java.util.Objects;

import static su.svn.models.ConfigurationUnit.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cm_cunit")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT cu FROM ConfigurationUnit cu"
              + " JOIN FETCH cu.admin"
              + " JOIN FETCH cu.owner"
              + " JOIN FETCH cu.group"
              + " JOIN FETCH cu.type"
              + " ORDER BY cu.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_NAME,
        query = "SELECT DISTINCT cu FROM ConfigurationUnit cu"
              + " JOIN FETCH cu.admin"
              + " JOIN FETCH cu.owner"
              + " JOIN FETCH cu.group"
              + " JOIN FETCH cu.type"
              + " WHERE cu.name LIKE :name"
              + " ORDER BY cu.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT DISTINCT cu FROM ConfigurationUnit cu"
              + " JOIN FETCH cu.admin"
              + " JOIN FETCH cu.owner"
              + " JOIN FETCH cu.group"
              + " JOIN FETCH cu.type"
              + " WHERE cu.description LIKE :desc"
              + " ORDER BY cu.id"
    ),
    @NamedQuery(
        name = FIND_BY_ID_WITH_DETAILS,
        query = "SELECT DISTINCT cu FROM ConfigurationUnit cu"
              + " JOIN FETCH cu.admin"
              + " JOIN FETCH cu.owner"
              + " JOIN FETCH cu.group"
              + " JOIN FETCH cu.type"
              + " WHERE cu.id = :id"
    ),
})
public class ConfigurationUnit implements DataSet
{
    public static final String FIND_ALL = "ConfigurationUnit.findAll";
    public static final String FIND_ALL_WHERE_NAME = "ConfigurationUnit.findAllWhereName";
    public static final String FIND_ALL_WHERE_DESC = "ConfigurationUnit.findAllWhereDescription";
    public static final String FIND_BY_ID_WITH_DETAILS = "ConfigurationUnit.findByIdWithDetails";

    @Id
    @SequenceGenerator(name = "cunit_identifier", sequenceName = "cunit_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cunit_identifier")
    @Column(name = "cunit_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "cunit_name", nullable = false, unique = true)
    private String name;

    // TODO private String environ;

    @Basic
    @Column(name = "description")
    private String description;

    @JsonbTypeAdapter(UserAdapter.class)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_user_id", nullable = false)
    private User admin;

    @JsonbTypeAdapter(UserAdapter.class)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User owner;

    @JsonbTypeAdapter(GroupAdapter.class)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private ConfigurationType type;

    public static boolean isValidForSave(ConfigurationUnit configurationUnit)
    {
        if (Objects.isNull(configurationUnit)) {
            return false;
        }
        if (Objects.isNull(configurationUnit.id)) {
            return false;
        }
        if (Objects.isNull(configurationUnit.admin)) {
            return false;
        }
        if (Objects.isNull(configurationUnit.owner)) {
            return false;
        }
        return !Objects.isNull(configurationUnit.name);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
