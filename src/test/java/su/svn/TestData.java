package su.svn;

import lombok.experimental.FieldNameConstants;
import su.svn.models.*;

import javax.persistence.TypedQuery;

import static org.mockito.Mockito.mock;

@FieldNameConstants
public class TestData
{
    public static final String TEST_STR1 = "TEST_STR1";
    public static final String TEST_STR2 = "TEST_STR2";
    public static final String TEST_STR3 = "TEST_STR3";
    public static final String TEST_STR4 = "TEST_STR4";
    public static final String TEST_STR5 = "TEST_STR5";
    public static final String TEST_STR6 = "TEST_STR6";
    public static final String TEST_STR7 = "TEST_STR7";
    public static final String TEST_STR8 = "TEST_STR8";
    public static final String TEST_STR9 = "TEST_STR9";

    public static final String TEST_NAME = "TEST_NAME";
    public static final String TEST_DESCRIPTION = "TEST_DESCRIPTION";
    public static final String TEST_TEXT = "TEST_TEXT";

    public static final long TEST_ID1 = 1L;
    public static final long TEST_ID2 = 2L;
    public static final long TEST_ID3 = 3L;
    public static final long TEST_ID4 = 4L;
    public static final long TEST_ID5 = 5L;
    public static final long TEST_ID6 = 6L;
    public static final long TEST_ID7 = 7L;
    public static final long TEST_ID8 = 8L;
    public static final long TEST_ID9 = 9L;

    @SuppressWarnings("unchecked") // still needed :( but just once :)
    public static <T> TypedQuery<T> mockTypedQuery() {
        return mock(TypedQuery.class);
    }

    public static Group createGroup1()
    {
        return new Group(TEST_ID1, TEST_NAME, TEST_DESCRIPTION);
    }
    public static final Group TEST_GROUP1 = createGroup1();

    public static User createUser1()
    {
        return new User(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, createGroup1());
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
            TEST_ID1, TEST_NAME, TEST_DESCRIPTION, TEST_GROUP1, TEST_USER1, TEST_CONFIGURATION_TYPE1
        );
    }
    public static final ConfigurationUnit TEST_CONFIGURATION_UNIT1 = createConfigurationUnit1();

    public static Message createMessage1()
    {
        return new Message(TEST_ID1, TEST_TEXT);
    }
    public static final Message TEST_MESSAGE1 = createMessage1();
}
