/*
 * Status.java
 * This file was last modified at 2019-01-26 11:04 by Victor N. Skurikhin.
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "cm_status")
public class Status implements DataSet
{
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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
