package su.svn.services;

import su.svn.models.DataSet;

import javax.ws.rs.core.Response;

public interface ResponseStorageService
{
    <E extends DataSet> Response create(StringBuffer requestURL, E entity);

    Response readAllGroups();

    Response readGroupById(Long id);

    Response readGroupByIdWithUsers(Long id);

    Response readAllUsers();

    Response readUserById(Long id);

    <E extends DataSet> Response update(StringBuffer requestURL, E entity);

    <E extends DataSet> Response delete(Class<E> clazz, Long id);
}
