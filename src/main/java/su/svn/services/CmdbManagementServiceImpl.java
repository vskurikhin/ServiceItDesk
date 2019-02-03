/*
 * CmdbManagementServiceImpl.java
 * This file was last modified at 2019-02-04 00:59 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.db.*;
import su.svn.models.Group;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

@Stateless
public class CmdbManagementServiceImpl implements CmdbManagementService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CmdbManagementServiceImpl.class);

    @EJB
    private ConfigurationTypeDao configurationTypeDao;

    @EJB
    private ConfigurationUnitDao configurationUnitDao;

    @EJB
    private GroupDao groupDao;

    @EJB
    private PrimaryGroupDao primaryGroupDao;

    @EJB
    private UserDao userDao;

    private Group doGetGroup(long id)
    {
        if (id > 0) {
            Optional<Group> optionalGroup = groupDao.findByIdWithUsers(id);

            if ( ! optionalGroup.isPresent()) {
                throw new RuntimeException("group doesn't exist!"); // TODO Custom Exception
            }
            return optionalGroup.get();
        }
        if (0 == id) {
            return new Group();
        }
        throw new RuntimeException("id less than zero!"); // TODO Custom Exception
    }

    @Override
    public void getGroup(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        try {
            String parameterId = req.getParameter("id");
            long id = Long.parseLong(parameterId);

            if ("DELETE".equals(req.getParameter("_method"))) {
                groupDao.delete(id);
            }
            else {
                req.setAttribute("group", doGetGroup(id));
            }
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_GONE);
            req.setAttribute("group", null);
            LOGGER.error(String.valueOf(e));
        }
    }

    @Override
    public void getGroups(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        try {
            List<Group> groups = groupDao.findAll()
                .stream()
                .peek(g -> g.setUsers(null))
                .collect(Collectors.toList());
            req.setAttribute("groups", groups);
        }
        catch (Exception e) {
            req.setAttribute("groups", Collections.emptyList());
            LOGGER.error(String.valueOf(e));
        }
    }

    @Override
    public void getUser(HttpServletRequest req, HttpServletResponse resp)
    {
    }

    @Override
    public void getUsers(HttpServletRequest req, HttpServletResponse resp)
    {
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public void putGroup(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/plain; charset=UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);

        try {
            String parameterId = req.getParameter("id");
            long id = Long.parseLong(parameterId);
            String parameterName = req.getParameter("name");
            String parameterDescription = req.getParameter("description");

            Group group = new Group();

            if (id < 0) {
                throw new RuntimeException("id less than zero!"); // TODO Custom Exception
            }
            if (id > 0) {
                Optional<Group> optionalGroup = groupDao.findById(id);
                if ( ! optionalGroup.isPresent()) {
                    throw new RuntimeException("group doesn't exist!"); // TODO Custom Exception
                }
                group = optionalGroup.get();
            }
            else {
                List<Group> list = groupDao.findByName(parameterName);

                if (list.size() > 0) {
                    throw new RuntimeException("name exists!"); // TODO Custom Exception
                }
            }

            group.setName(parameterName);
            group.setDescription(parameterDescription);
            groupDao.save(group);
        }
        catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_GONE);
            LOGGER.error(String.valueOf(e));
        }
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public void putUser(HttpServletRequest req, HttpServletResponse resp)
    {
    }
}
