/*
 * ResponseStorageServiceImpl.java
 * This file was last modified at 2019-02-10 22:04 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.db.*;
import su.svn.models.*;
import su.svn.models.dto.ConfigurationUnitDTO;
import su.svn.models.dto.GroupDTO;
import su.svn.models.dto.IncidentDTO;
import su.svn.models.dto.TaskDTO;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static su.svn.exceptions.ExceptionsFabric.getWebApplicationException;
import static su.svn.utils.UniformResource.getLocation;

@Stateless
public class ResponseStorageServiceImpl implements ResponseStorageService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseStorageServiceImpl.class);

    @EJB
    private ConfigurationTypeDao configurationTypeDao;

    @EJB
    private ConfigurationUnitDao configurationUnitDao;

    @EJB
    private GroupDao groupDao;

    @EJB
    private IncidentDao incidentDao;

    @EJB
    private MessageDao messageDao;

    @EJB
    private PrimaryGroupDao primaryGroupDao;

    @EJB
    private StatusDao statusDao;

    @EJB
    private TaskDao taskDao;

    @EJB
    private UserDao userDao;

    @SuppressWarnings("unchecked") // Dao type reduction
    private <E extends DataSet, D extends Dao<E, Long>> D getDao(Class<E> clazz)
    {
        if (clazz == ConfigurationType.class) {
            return (D) configurationTypeDao;
        }
        if (clazz == ConfigurationUnit.class) {
            return (D) configurationUnitDao;
        }
        if (clazz == Group.class) {
            return (D) groupDao;
        }
        if (clazz == Incident.class) {
            return (D) incidentDao;
        }
        if (clazz == Message.class) {
            return (D) messageDao;
        }
        if (clazz == PrimaryGroup.class) {
            return (D) primaryGroupDao;
        }
        if (clazz == Status.class) {
            return (D) statusDao;
        }
        if (clazz == Task.class) {
            return (D) taskDao;
        }
        if (clazz == User.class) {
            return (D) userDao;
        }
        throw getWebApplicationException(new Throwable("DAO case error!"));
    }

    private <E extends DataSet, D extends Dao<E, Long>> D getDao(E entity)
    {
        //noinspection unchecked
        return (D) getDao(entity.getClass());
    }

    @Override
    public <E extends DataSet> Response create(StringBuffer requestURL, E entity)
    {
        try {
            if (0 != entity.getId()) {
                throw new PersistenceException("id isn't equal 0!");
            }
            if ( ! getDao(entity).save(entity)) {
                throw new PersistenceException("can't save it!");
            }

            return Response.created(getLocation(requestURL, entity.getId())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create {}: {} ", entity.getClass().getCanonicalName(), e.toString());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    private void prepareConfigurationUnit(ConfigurationUnit unit)
    {
        Optional<User> admin = userDao.findById(unit.getAdmin().getId());
        if ( ! admin.isPresent()) {
            throw new PersistenceException("can't find admin user by id " + unit.getGroup().getId() + '!');
        }
        unit.setAdmin(admin.get());

        Optional<User> owher = userDao.findById(unit.getOwner().getId());
        if ( ! owher.isPresent()) {
            throw new PersistenceException("can't find owner user by id " + unit.getGroup().getId() + '!');
        }
        unit.setOwner(owher.get());

        Optional<Group> group = groupDao.findById(unit.getGroup().getId());
        if ( ! group.isPresent()) {
            throw new PersistenceException("can't find group by id " + unit.getGroup().getId() + '!');
        }
        unit.setGroup(group.get());

        Optional<ConfigurationType> type = configurationTypeDao.findById(unit.getType().getId());
        if ( ! type.isPresent()) {
            throw new PersistenceException("can't find configuration type by id " + unit.getGroup().getId() + '!');
        }
        unit.setType(type.get());

        if ( ! ConfigurationUnit.isValidForSave(unit)) {
            throw new PersistenceException("Configuration unit isn't valid for save!");
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response createConfigurationUnit(StringBuffer requestURL, ConfigurationUnit cu)
    {
        try {
            prepareConfigurationUnit(cu);

            return create(requestURL, cu);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create ConfigurationUnit: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    private void prepareIncident(Incident incident)
    {
        Optional<User> consumer = userDao.findById(incident.getConsumer().getId());
        if ( ! consumer.isPresent()) {
            throw new PersistenceException("can't find consumer user by id " + incident.getConsumer().getId() + '!');
        }
        incident.setConsumer(consumer.get());

        Optional<Status> status = statusDao.findById(incident.getStatus().getId());
        if ( ! status.isPresent()) {
            throw new PersistenceException("can't find status by id " + incident.getStatus().getId() + '!');
        }
        incident.setStatus(status.get());

        if ( ! Incident.isValidForSave(incident)) {
            throw new PersistenceException("incident isn't valid for save!");
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response createIncident(StringBuffer requestURL, Incident incident)
    {
        try {
            prepareIncident(incident);

            return create(requestURL, incident);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create Incident: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    private void prepareTask(Task task)
    {
        Optional<User> consumer = userDao.findById(task.getConsumer().getId());
        if ( ! consumer.isPresent()) {
            throw new PersistenceException("can't find consumer user by id " + task.getConsumer().getId() + '!');
        }
        task.setConsumer(consumer.get());

        Optional<Status> status = statusDao.findById(task.getStatus().getId());
        if ( ! status.isPresent()) {
            throw new PersistenceException("can't find status by id " + task.getStatus().getId() + '!');
        }
        task.setStatus(status.get());

        if ( ! Task.isValidForSave(task)) {
            throw new PersistenceException("task isn't valid for save!");
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response createTask(StringBuffer requestURL, Task task)
    {
        try {
            prepareTask(task);

            return create(requestURL, task);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create Task: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    private void prepareUser(User user)
    {
        Optional<PrimaryGroup> group = primaryGroupDao.findById(user.getGroup().getId());
        if ( ! group.isPresent()) {
            throw new PersistenceException("can't find group by id " + user.getGroup().getId() + '!');
        }
        user.setGroup(group.get());

        if ( ! User.isValidForSave(user)) {
            throw new PersistenceException("user isn't valid for save!");
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public Response createUser(StringBuffer requestURL, User user)
    {
        try {
            prepareUser(user);

            return create(requestURL, user);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create User: {} with exception: ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    public <E extends DataSet> Response readAll(Class<E> clazz)
    {
        return Response.ok(getDao(clazz).findAll()).build();
    }

    @Override
    public Response readAllConfigurationUnit()
    {
        return Response.ok(
            configurationUnitDao.findAll()
                .stream()
                .map(ConfigurationUnitDTO::create)
                .collect(Collectors.toList())
        ).build();
    }

    @Override
    public Response readAllGroups()
    {
        return Response.ok(
            groupDao.findAll()
                .stream()
                .map(GroupDTO::create)
                .collect(Collectors.toList())
        ).build();
    }

    @Override
    public Response readAllIncidents()
    {
        return Response.ok(
            incidentDao.findAll()
                .stream()
                .map(IncidentDTO::create)
                .collect(Collectors.toList())
        ).build();
    }

    @Override
    public Response readAllTasks()
    {
        return Response.ok(
            taskDao.findAll()
                .stream()
                .map(TaskDTO::create)
                .collect(Collectors.toList())
        ).build();
    }

    @Override
    public  <E extends DataSet> Response readById(Class<E> clazz, Long id)
    {
        try {
            Optional<E> optionalEntity = getDao(clazz).findById(id);

            if ( ! optionalEntity.isPresent()) {
                throw new PersistenceException("it isn't present!");
            }
            E entity = optionalEntity.get();

            if (entity.getClass() != clazz) {
                throw new ClassCastException(entity.getClass().getCanonicalName() + " to " + clazz.getCanonicalName() );
            }

            return Response.ok(entity).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Did not find the {} with id == {}: {}", clazz.getCanonicalName(), id, e.toString());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response readGroupByIdWithUsers(Long id)
    {
        try {
            Optional<Group> optionalEntity = groupDao.findByIdWithUsers(id);

            if ( ! optionalEntity.isPresent()) {
                throw new PersistenceException("it isn't present!");
            }
            Group entity = optionalEntity.get();

            return Response.ok(entity).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Did not find the Group with id == {}: {}", id, e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response readIncidentById(Long id)
    {
        try {
            Optional<Incident> optionalEntity = incidentDao.findById(id);

            if ( ! optionalEntity.isPresent()) {
                throw new PersistenceException("it isn't present!");
            }
            Incident entity = optionalEntity.get();

            return Response.ok(IncidentDTO.create(entity)).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Did not find the Incident with id == {}: {}", id, e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response readIncidentByIdWithMessages(Long id)
    {
        try {
            Optional<Incident> optionalEntity = incidentDao.findByIdWithDetails(id);

            if ( ! optionalEntity.isPresent()) {
                throw new PersistenceException("it isn't present!");
            }
            Incident entity = optionalEntity.get();

            return Response.ok(entity).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Did not find the Incident with id == {}: {}", id, e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response readTaskById(Long id)
    {
        try {
            Optional<Task> optionalEntity = taskDao.findById(id);

            if ( ! optionalEntity.isPresent()) {
                throw new PersistenceException("it isn't present!");
            }
            Task entity = optionalEntity.get();

            return Response.ok(TaskDTO.create(entity)).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Did not find the Task with id == {}: {}", id, e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public Response readTaskByIdWithMessages(Long id)
    {
        try {
            Optional<Task> optionalEntity = taskDao.findByIdWithDetails(id);

            if ( ! optionalEntity.isPresent()) {
                throw new PersistenceException("it isn't present!");
            }
            Task entity = optionalEntity.get();

            return Response.ok(entity).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Did not find the Task with id == {}: {}", id, e);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Override
    public <E extends DataSet> Response update(StringBuffer requestURL, E entity)
    {
        try {
            if (0 == entity.getId()) {
                throw new PersistenceException("id is equal 0!");
            }
            if ( ! getDao(entity).save(entity)) {
                throw new PersistenceException("can't save it!");
            }

            return Response.ok(getLocation(requestURL, entity.getId())).build();
        }
        catch (RuntimeException e) {
            LOGGER.error("Try update {}: {} ", entity.getClass().getCanonicalName(), e);
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    public Response updateConfigurationUnit(StringBuffer requestURL, ConfigurationUnit unit)
    {
        try {
            prepareConfigurationUnit(unit);

            return update(requestURL, unit);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create ConfigurationUnit: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    public Response updateIncident(StringBuffer requestURL, Incident incident)
    {
        try {
            prepareIncident(incident);
            Optional<Incident> optionalIncident = incidentDao.findByIdWithDetails(incident.getId());
            optionalIncident.ifPresent(i -> incident.setMessages(i.getMessages()));

            return update(requestURL, incident);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create Incident: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    public Response updateTask(StringBuffer requestURL, Task task)
    {
        try {
            prepareTask(task);
            Optional<Task> optionalIncident = taskDao.findByIdWithDetails(task.getId());
            optionalIncident.ifPresent(i -> task.setMessages(i.getMessages()));

            return update(requestURL, task);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try create Task: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    public Response updateUser(StringBuffer requestURL, User user)
    {
        try {
            prepareUser(user);

            return update(requestURL, user);
        }
        catch (RuntimeException e) {
            LOGGER.error("Try update User: {} ", e.getMessage());
            return Response.notAcceptable(Collections.emptyList()).build();
        }
    }

    @Override
    public  <E extends DataSet> Response delete(Class<E> clazz, Long id)
    {
        if ( ! getDao(clazz).delete(id)) {
            LOGGER.warn("Error delete {} with id == {}", clazz.getCanonicalName(), id);
        }

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
