/*
 * CmdbManagementServiceImplTest.java
 * This file was last modified at 2019-02-04 00:06 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.services;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import su.svn.db.*;
import su.svn.models.Group;
import su.svn.utils.services.TestWeldService;

import javax.enterprise.inject.spi.InjectionPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(WeldJunit5Extension.class)
class CmdbManagementServiceImplTest extends TestWeldService
{
    private HttpServletRequest mockedRequest = Mockito.mock(HttpServletRequest.class);

    private HttpServletResponse mockedResponse = Mockito.mock(HttpServletResponse.class);

    @WeldSetup
    public WeldInitiator weld = WeldInitiator
        .from(CmdbManagementServiceImpl.class, ConfigurationTypeDaoJpa.class, ConfigurationUnitDaoJpa.class,
            GroupDaoJpa.class, IncidentDaoJpa.class, MessageDaoJpa.class, PrimaryGroupDaoJpa.class, StatusDaoJpa.class,
            TaskDaoJpa.class, UserDaoJpa.class)
        .setEjbFactory(getEjbFactory())
        .setPersistenceContextFactory(getPCFactory())
        .build();

    @BeforeAll
    static void setMockedQuery()
    {
        mockedQuery = mockQuery();
    }

    @Test
    void getGroup()
    {
    }

    @Test
    void getGroups(CmdbManagementService service)
    {
        dataSetSupplier = Group::new;
        service.getGroups(mockedRequest, mockedResponse);
    }

    @Test
    void getUser()
    {
    }

    @Test
    void getUsers()
    {
    }

    @Test
    void putGroup()
    {
    }

    @Test
    void putUser()
    {
    }

    @SuppressWarnings("Duplicates")
    private Function<InjectionPoint, Object> getEjbFactory()
    {
        return ip -> {
            switch (ip.getAnnotated().getBaseType().getTypeName()) {
                case "su.svn.db.ConfigurationTypeDao":
                    return weld.select(ConfigurationTypeDaoJpa.class).get();
                case "su.svn.db.ConfigurationUnitDao":
                    return weld.select(ConfigurationUnitDaoJpa.class).get();
                case "su.svn.db.GroupDao":
                    return weld.select(GroupDaoJpa.class).get();
                case "su.svn.db.IncidentDao":
                    return weld.select(IncidentDaoJpa.class).get();
                case "su.svn.db.MessageDao":
                    return weld.select(MessageDaoJpa.class).get();
                case "su.svn.db.PrimaryGroupDao":
                    return weld.select(PrimaryGroupDaoJpa.class).get();
                case "su.svn.db.StatusDao":
                    return weld.select(StatusDaoJpa.class).get();
                case "su.svn.db.TaskDao":
                    return weld.select(TaskDaoJpa.class).get();
                case "su.svn.db.UserDao":
                    return weld.select(UserDaoJpa.class).get();
                default:
                    return null;
            }
        };
    }
}