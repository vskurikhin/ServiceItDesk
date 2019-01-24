package su.svn.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.TestData.*;

@DisplayName("Class Group")
class GroupTest
{
    Group group;

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
            assertThat(group).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(group).hasFieldOrPropertyWithValue("name", null);
            assertThat(group).hasFieldOrPropertyWithValue("description", null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSettName()
        {
            group.setName(TEST_STR1);
            assertThat(group).hasFieldOrPropertyWithValue("name", TEST_STR1);
            assertEquals(TEST_STR1, group.getName());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSettDescription()
        {
            group.setDescription(TEST_STR1);
            assertThat(group).hasFieldOrPropertyWithValue("description", TEST_STR1);
            assertEquals(TEST_STR1, group.getDescription());
        }
        @Test
        @DisplayName("The length of string from Genre::toString is great than zero")
        void testToString()
        {
            assertTrue(group.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {

        @BeforeEach
        void createNew()
        {
            group = new Group(TEST_ID1, TEST_NAME, TEST_DESCRIPTION);
        }

        @Test
        @DisplayName("initialized values in Group()")
        void defaults()
        {
            assertThat(group).hasFieldOrPropertyWithValue("id", 1L);
            assertThat(group).hasFieldOrPropertyWithValue("name", TEST_NAME);
            assertThat(group).hasFieldOrPropertyWithValue("description", TEST_DESCRIPTION);
        }

        @Test
        @DisplayName("equals for class Group and hashCode")
        void testEquals()
        {
            assertNotEquals(new Group(), group);
            Group expected = createGroup1();
            assertEquals(expected.hashCode(), group.hashCode());
            assertEquals(expected, group);
        }
    }
}