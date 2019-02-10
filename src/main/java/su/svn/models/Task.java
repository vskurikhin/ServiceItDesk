/*
 * Task.java
 * This file was last modified at 2019-02-10 22:11 by Victor N. Skurikhin.
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

import static su.svn.models.Task.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "pm_task")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT t FROM Task t JOIN FETCH t.consumer JOIN FETCH t.status ORDER BY t.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TITLE,
        query = "SELECT DISTINCT t FROM Task t"
              + " JOIN FETCH t.consumer"
              + " JOIN FETCH t.status"
              + " WHERE t.title LIKE :title"
              + " ORDER BY t.id"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_DESC,
        query = "SELECT DISTINCT t FROM Task t"
              + " JOIN FETCH t.consumer"
              + " JOIN FETCH t.status"
              + " WHERE t.description LIKE :desc"
              + " ORDER BY t.id"
    ),
    @NamedQuery(
        name = FIND_BY_ID_WITH_DETAILS,
        query = "SELECT DISTINCT t FROM Task t"
              + " JOIN FETCH t.consumer"
              + " JOIN FETCH t.status"
              + " LEFT JOIN FETCH t.messages"
              + " WHERE t.id = :id"
    ),
})
public class Task implements DataSet
{
    public static final String FIND_ALL = "Task.findAll";
    public static final String FIND_ALL_WHERE_TITLE = "Task.findAllWhereTitle";
    public static final String FIND_ALL_WHERE_DESC = "Task.findAllWhereDescription";
    public static final String FIND_BY_ID_WITH_DETAILS = "Task.findByIdWithDetails";

    @Id
    @SequenceGenerator(name = "task_identifier", sequenceName = "task_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "task_identifier")
    @Column(name = "task_id", nullable = false, unique = true)
    private Long id = 0L;

    @Basic
    @Column(name = "task_title", nullable = false, unique = true)
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
    @JoinTable(name = "pm_task_record",
        joinColumns = @JoinColumn(name = "incident_id"),
        inverseJoinColumns = @JoinColumn(name = "message_id")
    )
    private Collection<Message> messages = new TreeSet<>();

    public static boolean isValidForSave(Task task)
    {
        if (Objects.isNull(task)) {
            return false;
        }
        if (Objects.isNull(task.id)) {
            return false;
        }
        if (Objects.isNull(task.description)) {
            return false;
        }
        if (Objects.isNull(task.consumer)) {
            return false;
        }
        if (Objects.isNull(task.status)) {
            return false;
        }
        return !Objects.isNull(task.title);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
