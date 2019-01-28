/*
 * Message.java
 * This file was last modified at 2019.01.24 23:59 by Victor N. Skurikhin.
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
@Table(name = "pm_message")
public class Message implements DataSet
{
    @Id
    @SequenceGenerator(name = "message_identifier", sequenceName = "message_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "message_identifier")
    @Column(name = "message_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "message_text", nullable = false)
    private String text;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
