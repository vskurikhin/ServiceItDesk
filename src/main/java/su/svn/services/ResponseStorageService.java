/*
 * ResponseStorageService.java
 * This file was last modified at 2019-02-09 22:19 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import su.svn.models.*;

import javax.ws.rs.core.Response;

public interface ResponseStorageService
{
    <E extends DataSet> Response create(StringBuffer requestURL, E entity);

    Response createConfigurationUnit(StringBuffer requestURL, ConfigurationUnit unit);

    Response createIncident(StringBuffer requestURL, Incident incident);

    Response createTask(StringBuffer requestURL, Task task);

    Response createUser(StringBuffer requestURL, User user);

    <E extends DataSet> Response readAll(Class<E> eClass);

    Response readAllConfigurationUnit();

    Response readAllGroups();

    Response readAllIncidents();

    <E extends DataSet> Response readById(Class<E> eClass, Long id);

    Response readGroupByIdWithUsers(Long id);

    Response readIncidentById(Long id);

    Response readIncidentByIdWithMessages(Long id);

    <E extends DataSet> Response update(StringBuffer requestURL, E entity);

    Response updateConfigurationUnit(StringBuffer requestURL, ConfigurationUnit unit);

    Response updateIncident(StringBuffer requestURL, Incident unit);

    Response updateUser(StringBuffer requestURL, User entity);

    <E extends DataSet> Response delete(Class<E> eClass, Long id);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
