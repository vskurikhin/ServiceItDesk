/*
 * Incident.java
 * This file was last modified at 2019-02-09 20:13 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import su.svn.services.adapters.UserAdapter;

import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.persistence.*;

import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

import static su.svn.models.Incident.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "pm_incident")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT i FROM Incident i JOIN FETCH i.consumer JOIN FETCH i.status ORDER BY i.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TITLE,
        query = "SELECT DISTINCT i FROM Incident i"
              + " JOIN FETCH i.consumer"
              + " JOIN FETCH i.status"
              + " WHERE i.title LIKE :title"
              + " ORDER BY i.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT DISTINCT i FROM Incident i"
              + " JOIN FETCH i.consumer"
              + " JOIN FETCH i.status"
              + " WHERE i.description LIKE :desc"
              + " ORDER BY i.id"
    ),
    @NamedQuery(
        name = FIND_BY_ID_WITH_DETAILS,
        query = "SELECT DISTINCT i FROM Incident i"
              + " JOIN FETCH i.consumer"
              + " JOIN FETCH i.status"
              + " LEFT JOIN FETCH i.messages"
              + " WHERE i.id = :id"
    ),
})
public class Incident implements DataSet
{
    public static final String FIND_ALL = "Incident.findAll";
    public static final String FIND_ALL_WHERE_TITLE = "Incident.findAllWhereTitle";
    public static final String FIND_ALL_WHERE_DESC = "Incident.findAllWhereDescription";
    public static final String FIND_BY_ID_WITH_DETAILS = "Incident.findByIdWithDetails";

    @Id
    @SequenceGenerator(name = "incident_identifier", sequenceName = "incident_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "incident_identifier")
    @Column(name = "incident_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "incident_title", nullable = false)
    private String title;

    @Basic
    @Column(name = "description_text", nullable = false)
    private String description;

    @JsonbTypeAdapter(UserAdapter.class)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "consumer_user_id", nullable = false)
    private User consumer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "pm_inc_record",
        joinColumns = @JoinColumn(name = "incident_id"),
        inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private Collection<Message> messages = new TreeSet<>();

    public static boolean isValidForSave(Incident incident)
    {
        if (Objects.isNull(incident)) {
            return false;
        }
        if (Objects.isNull(incident.id)) {
            return false;
        }
        if (Objects.isNull(incident.consumer)) {
            return false;
        }
        if (Objects.isNull(incident.status)) {
            return false;
        }
        if (Objects.isNull(incident.description)) {
            return false;
        }
        return !Objects.isNull(incident.title);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
