package su.svn.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.TestData.*;

@DisplayName("Class User")
class UserTest
{
    User user;

    @Test
    @DisplayName("is instantiated with new User()")
    void isInstantiatedWithNew() {
        new User();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            user = new User();
        }

        @Test
        @DisplayName("default values in User()")
        void defaults()
        {
            assertThat(user).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(user).hasFieldOrPropertyWithValue("name", null);
            assertThat(user).hasFieldOrPropertyWithValue("description", null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            user.setName(TEST_STR1);
            assertThat(user).hasFieldOrPropertyWithValue("name", TEST_STR1);
            assertEquals(TEST_STR1, user.getName());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            user.setDescription(TEST_STR1);
            assertThat(user).hasFieldOrPropertyWithValue("description", TEST_STR1);
            assertEquals(TEST_STR1, user.getDescription());
        }
        @Test
        @DisplayName("The length of string from Genre::toString is great than zero")
        void testToString()
        {
            assertTrue(user.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            user = new User(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, createGroup1());
        }

        @Test
        @DisplayName("initialized values in User()")
        void defaults()
        {
            assertThat(user).hasFieldOrPropertyWithValue("id", 1L);
            assertThat(user).hasFieldOrPropertyWithValue("name", TEST_NAME);
            assertThat(user).hasFieldOrPropertyWithValue("description", TEST_DESCRIPTION);
        }

        @Test
        @DisplayName("equals for class User and hashCode")
        void testEquals()
        {
            assertNotEquals(new User(), user);
            final User expected = TEST_USER1;
            assertEquals(expected.hashCode(), user.hashCode());
            assertEquals(expected, user);
        }
    }
}