/*
 * TaskTest.java
 * This file was last modified at 2019-01-26 15:24 by Victor N. Skurikhin.
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

@DisplayName("Class Task")
class TaskTest
{
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";

    Task task;

    @Test
    @DisplayName("is instantiated with new Task()")
    void isInstantiatedWithNew() {
        new Task();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            task = new Task();
        }

        @Test
        @DisplayName("default values in Task()")
        void defaults()
        {
            assertThat(task).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(task).hasFieldOrPropertyWithValue(TITLE, null);
            assertThat(task).hasFieldOrPropertyWithValue(DESCRIPTION, null);
            assertThat(task).hasFieldOrPropertyWithValue("consumer", null);
            assertThat(task).hasFieldOrPropertyWithValue("status", null);
        }

        @Test
        @DisplayName("use setter and getter for title")
        void testGetSetName()
        {
            task.setTitle(TEST_STR1);
            assertThat(task).hasFieldOrPropertyWithValue(TITLE, TEST_STR1);
            assertEquals(TEST_STR1, task.getTitle());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            task.setDescription(TEST_STR1);
            assertThat(task).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_STR1);
            assertEquals(TEST_STR1, task.getDescription());
        }

        @Test
        @DisplayName("use setter and getter for consumer")
        void testGetSetConsumer()
        {
            task.setConsumer(TEST_USER1);
            assertThat(task).hasFieldOrPropertyWithValue("consumer", TEST_USER1);
            assertEquals(TEST_USER1, task.getConsumer());
        }

        @Test
        @DisplayName("use setter and getter for status")
        void testGetSetStatus()
        {
            task.setStatus(TEST_STATUS1);
            assertThat(task).hasFieldOrPropertyWithValue("status", TEST_STATUS1);
            assertEquals(TEST_STATUS1, task.getStatus());
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(task.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            task = new Task(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, TEST_USER1, TEST_STATUS1);
        }

        @Test
        @DisplayName("initialized values in Task()")
        void defaults()
        {
            assertThat(task).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(task).hasFieldOrPropertyWithValue(TITLE, TEST_NAME);
            assertThat(task).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
            assertThat(task).hasFieldOrPropertyWithValue("consumer", TEST_USER1);
            assertThat(task).hasFieldOrPropertyWithValue("status", TEST_STATUS1);
        }

        @Test
        @DisplayName("equals for class Task and hashCode")
        void testEquals()
        {
            assertNotEquals(new Task(), task);
            final Task expected = TEST_TASK1;
            assertEquals(expected.hashCode(), task.hashCode());
            assertEquals(expected, task);
        }
    }
}