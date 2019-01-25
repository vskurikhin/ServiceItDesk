package su.svn;

import lombok.experimental.FieldNameConstants;
import lombok.AccessLevel;
import su.svn.models.Group;

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
}
