/*
 * Status.java
 * This file was last modified at 2019-02-03 10:53 by Victor N. Skurikhin.
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

import static su.svn.models.Status.FIND_ALL;
import static su.svn.models.Status.FIND_ALL_WHERE_DESC;
import static su.svn.models.Status.FIND_ALL_WHERE_STATUS;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "pm_status")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT s FROM Status s"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_STATUS,
        query = "SELECT s FROM Status s WHERE s.status LIKE :status"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT s FROM Status s WHERE s.description LIKE :desc"
    ),
})
public class Status implements DataSet
{
    public static final String FIND_ALL = "Status.findAll";
    public static final String FIND_ALL_WHERE_STATUS = "Status.findAllWhereStatus";
    public static final String FIND_ALL_WHERE_DESC = "Status.findAllWhereDescription";

    @Id
    @SequenceGenerator(name = "status_identifier", sequenceName = "status_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "status_identifier")
    @Column(name = "status_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "status", nullable = false, unique = true)
    private String status;

    @Basic
    @Column(name = "description")
    private String description;

    public static boolean isValidForSave(Status status)
    {
        if (Objects.isNull(status)) {
            return false;
        }
        if (Objects.isNull(status.id)) {
            return false;
        }
        return !Objects.isNull(status.status);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
