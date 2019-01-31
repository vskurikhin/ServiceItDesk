package su.svn.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.db.Dao;
import su.svn.db.GroupDao;
import su.svn.db.PrimaryGroupDao;
import su.svn.db.UserDao;
import su.svn.models.DataSet;
import su.svn.models.Group;
import su.svn.models.PrimaryGroup;
import su.svn.models.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static su.svn.exceptions.ExceptionsFabric.getWebApplicationException;

@Stateless
public class ResponseStorageServiceImpl implements ResponseStorageService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseStorageServiceImpl.class);

    @EJB
    private GroupDao groupDao;

    @EJB
    private PrimaryGroupDao primaryGroupDao;

    @EJB
    private UserDao userDao;

    private <E extends DataSet> URI getLocation(StringBuffer url, Long id)
    {
        return URI.create(url.append('/').append(id).toString());
    }

    @SuppressWarnings("unchecked") // Dao type reduction
    private <E extends DataSet, D extends Dao<E, Long>> D getDao(Class<E> clazz)
    {
        if (clazz == Group.class) {
            return (D) groupDao;
        }
        if (clazz == PrimaryGroup.class) {
            return (D) primaryGroupDao;
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
        if (0 != entity.getId()) {
            LOGGER.warn("Try create {} with id != 0", entity.getClass().getCanonicalName());
        }
        else if (getDao(entity).save(entity)) {
            return Response.created(getLocation(requestURL, entity.getId())).build();
        }
        LOGGER.warn("Error create DataSet with id == {}", entity.getId());
        throw getWebApplicationException(new Throwable("Error create!"));
    }

    private <E extends DataSet> Response readAll(Class<E> clazz)
    {
        return Response.ok(getDao(clazz).findAll()).build();
    }

    @Override
    public Response readAllGroups()
    {
        return Response.ok(
            groupDao.findAll()
                .stream()
                .peek(g -> g.setUsers(null))
                .collect(Collectors.toList())
        ).build();
    }

    @Override
    public Response readGroupById(Long id)
    {
        Optional<Group> optionalEntity = groupDao.findById(id);

        if (optionalEntity.isPresent()) {
            Group entity = optionalEntity.get();
            entity.setUsers(null);
            return Response.ok(entity).build();
        }

        LOGGER.warn("Not found Group with id == {}", id);
        throw getWebApplicationException(new Throwable("Not Found!"));
    }

    @Override
    public Response readGroupByIdWithUsers(Long id)
    {
        Optional<Group> optionalEntity = groupDao.findByIdWithUsers(id);

        if (optionalEntity.isPresent()) {
            return Response.ok(optionalEntity).build();
        }

        LOGGER.warn("Not found Group with id == {}", id);
        throw getWebApplicationException(new Throwable("Not Found!"));
    }

    @Override
    public Response readAllUsers()
    {
        return Response.ok(new ArrayList<>(userDao.findAll())).build();
    }

    @Override
    public Response readUserById(Long id)
    {
        Optional<User> optionalEntity = userDao.findById(id);

        System.out.println("optionalEntity = " + optionalEntity);

        if (optionalEntity.isPresent()) {
            User entity = optionalEntity.get();
            return Response.ok(entity).build();
        }

        LOGGER.warn("Not found User with id == {}", id);
        throw getWebApplicationException(new Throwable("Not Found!"));
    }

    @Override
    public <E extends DataSet> Response update(StringBuffer requestURL, E entity)
    {
        if (0 == entity.getId()) {
            LOGGER.warn("Try update {} with id == 0", entity.getClass().getCanonicalName());
        }
        else if (getDao(entity).save(entity)) {
            return Response.ok(getLocation(requestURL, entity.getId())).build();
        }
        LOGGER.warn("Error update DataSet with id == {}", entity.getId());
        throw getWebApplicationException(new Throwable("Error update!"));
    }

    @Override
    public  <E extends DataSet> Response delete(Class<E> clazz, Long id)
    {
        if (getDao(clazz).delete(id)) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        LOGGER.warn("Error delete DataSet with id == {}", id);
        throw getWebApplicationException(new Throwable("Error delete!"));
    }
}
