/*
 * ConfigurationUnit.java
 * This file was last modified at 2019-01-26 19:46 by Victor N. Skurikhin.
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

import static su.svn.models.ConfigurationUnit.*;

/*  public static final String PERSISTENCE_UNIT_NAME = "jpa";
    public static final String SELECT_ALL = "SELECT cu FROM ConfigurationUnit cu";
    public static final String SELECT_WHERE_NAME = SELECT_ALL + " WHERE cu.name LIKE :name";
    public static final String SELECT_WHERE_DESC = SELECT_ALL + " WHERE cu.description LIKE :desc";
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cm_cunit")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT cu FROM ConfigurationUnit cu"
              + " JOIN FETCH cu.admin"
              + " JOIN FETCH cu.owner"
              + " JOIN FETCH cu.group"
              + " JOIN FETCH cu.type"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_NAME,
        query = "SELECT cu FROM ConfigurationUnit cu"
              + " JOIN FETCH cu.admin"
              + " JOIN FETCH cu.owner"
              + " JOIN FETCH cu.group"
              + " JOIN FETCH cu.type"
              + " WHERE cu.name LIKE :name"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT cu FROM ConfigurationUnit cu"
              + " JOIN FETCH cu.admin"
              + " JOIN FETCH cu.owner"
              + " JOIN FETCH cu.group"
              + " JOIN FETCH cu.type"
              + " WHERE cu.description LIKE :desc"
    ),
    @NamedQuery(
        name = FIND_BY_ID_WITH_DETAILS,
        query = "SELECT cu FROM ConfigurationUnit cu"
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_user_id", nullable = false)
    private User admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_user_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private ConfigurationType type;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
