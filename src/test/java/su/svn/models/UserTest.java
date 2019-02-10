/*
 * UserTest.java
 * This file was last modified at 2019-02-03 17:25 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

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
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    private User user;

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
            assertThat(user).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(user).hasFieldOrPropertyWithValue(NAME, null);
            assertThat(user).hasFieldOrPropertyWithValue(DESCRIPTION, null);
            assertThat(user).hasFieldOrPropertyWithValue("group", null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            user.setName(TEST_STR1);
            assertThat(user).hasFieldOrPropertyWithValue(NAME, TEST_STR1);
            assertEquals(TEST_STR1, user.getName());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            user.setDescription(TEST_STR1);
            assertThat(user).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_STR1);
            assertEquals(TEST_STR1, user.getDescription());
        }

        @Test
        void isValidForSave()
        {
            assertFalse(User.isValidForSave(user));
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
        PrimaryGroup group = createPrimaryGroup1();

        @BeforeEach
        void createNew()
        {
            user = new User(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, group);
        }

        @Test
        @DisplayName("initialized values in User()")
        void defaults()
        {
            assertThat(user).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(user).hasFieldOrPropertyWithValue(NAME, TEST_NAME);
            assertThat(user).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
            assertThat(user).hasFieldOrPropertyWithValue("group", group);
        }

        @Test
        @DisplayName("equals for class User and hashCode")
        void testEquals()
        {
            assertNotEquals(new User(), user);
            User expected = new User(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, group);
            assertEquals(expected.hashCode(), user.hashCode());
            assertEquals(expected, user);
        }

        @Test
        void isValidForSave()
        {
            assertTrue(User.isValidForSave(user));
        }
    }
}