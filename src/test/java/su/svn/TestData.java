/*
 * TestData.java
 * This file was last modified at 2019-02-10 23:27 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn;


import su.svn.models.*;

import javax.persistence.TypedQuery;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.mockito.Mockito.mock;

public class TestData
{
    public static final String TEST_STR1 = "TEST_STR1";
    public static final String TEST_STR2 = "TEST_STR2";

    public static final String TEST_DESCRIPTION = "TEST_DESCRIPTION";
    public static final String TEST_NAME = "TEST_NAME";
    public static final String TEST_STATUS = "TEST_STATUS";
    public static final String TEST_TEXT = "TEST_TEXT";
    public static final String TEST_TITLE = "TEST_TITLE";

    public static final long TEST_ID1 = 1L;
    public static final long TEST_ID9 = 9L;

    @SuppressWarnings("unchecked") // still needed :( but just once :)
    public static <T> TypedQuery<T> mockTypedQuery() {
        return mock(TypedQuery.class);
    }

    private static final Set<User> EMPTY_USER_SET = new HashSet<>();

    public static final Set<Message> EMPTY_MESSAGE_SET = new TreeSet<>();

    public static PrimaryGroup createPrimaryGroup1()
    {
        return new PrimaryGroup(TEST_ID1, TEST_NAME, TEST_DESCRIPTION);
    }
    public static final Group TEST_GROUP1 = createGroup1();

    public static Group createGroup1()
    {
        return new Group(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, EMPTY_USER_SET);
    }

    public static User createUser0()
    {
        PrimaryGroup group = createPrimaryGroup1();
        return new User(0L, TEST_NAME, TEST_DESCRIPTION, group);
    }
    public static final User TEST_USER0 = createUser0();

    public static User createUser1()
    {
        PrimaryGroup group = createPrimaryGroup1();
        return new User(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, group);
    }
    public static final User TEST_USER1 = createUser1();

    public static ConfigurationType createConfigurationType1()
    {
        return new ConfigurationType(TEST_ID1, TEST_NAME, TEST_DESCRIPTION);
    }
    public static final ConfigurationType TEST_CONFIGURATION_TYPE1 = createConfigurationType1();


    public static ConfigurationUnit createConfigurationUnit1()
    {
        return new ConfigurationUnit(
            TEST_ID1, TEST_NAME, TEST_DESCRIPTION,
            createUser1(), createUser1(), createGroup1(), createConfigurationType1()
        );
    }
    public static final ConfigurationUnit TEST_CONFIGURATION_UNIT1 = createConfigurationUnit1();

    public static Message createMessage1()
    {
        return new Message(TEST_ID1, TEST_TEXT);
    }
    public static final Message TEST_MESSAGE1 = createMessage1();

    public static Status createStatus1()
    {
        return new Status(TEST_ID1, TEST_STATUS, TEST_DESCRIPTION);
    }
    public static final Status TEST_STATUS1 = createStatus1();

    public static Task createTask1()
    {
        return new Task(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, createUser1(), createStatus1(), EMPTY_MESSAGE_SET);
    }
    public static final Task TEST_TASK1 = createTask1();

    public static Incident createIncident1()
    {
        return new Incident(TEST_ID1, TEST_TITLE, TEST_DESCRIPTION, createUser1(), createStatus1(), EMPTY_MESSAGE_SET);
    }
    public static final Incident TEST_INCIDENT1 = createIncident1();
}
