/*
 * GroupTest.java
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

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.TestData.*;

@DisplayName("Class Group")
class GroupTest
{
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    private Group group;

    @Test
    @DisplayName("is instantiated with new Group()")
    void isInstantiatedWithNew() {
        new Group();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            group = new Group();
        }

        @Test
        @DisplayName("default values in Group()")
        void defaults()
        {
            assertThat(group).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(group).hasFieldOrPropertyWithValue(NAME, null);
            assertThat(group).hasFieldOrPropertyWithValue(DESCRIPTION, null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            group.setName(TEST_STR1);
            assertThat(group).hasFieldOrPropertyWithValue(NAME, TEST_STR1);
            assertEquals(TEST_STR1, group.getName());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            group.setDescription(TEST_STR1);
            assertThat(group).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_STR1);
            assertEquals(TEST_STR1, group.getDescription());
        }

        @Test
        void isValidForSave()
        {
            assertFalse(Group.isValidForSave(group));
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(group.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        Set<User> emptySet = new HashSet<>();

        @BeforeEach
        void createNew()
        {
            group = new Group(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, emptySet);
        }

        @Test
        @DisplayName("initialized values in Group()")
        void defaults()
        {
            assertThat(group).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(group).hasFieldOrPropertyWithValue(NAME, TEST_NAME);
            assertThat(group).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
            assertThat(group).hasFieldOrPropertyWithValue("users", emptySet);
        }

        @Test
        @DisplayName("equals for class Group and hashCode")
        void testEquals()
        {
            assertNotEquals(new Group(), group);
            final Group expected = new Group(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, emptySet);
            expected.setUsers(emptySet);
            assertEquals(expected.hashCode(), group.hashCode());
            assertEquals(expected, group);
        }

        @Test
        void isValidForSave()
        {
            assertTrue(Group.isValidForSave(group));
        }
    }
}