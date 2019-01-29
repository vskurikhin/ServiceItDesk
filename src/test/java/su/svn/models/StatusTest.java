/*
 * StatusTest.java
 * This file was last modified at 2019-01-26 11:32 by Victor N. Skurikhin.
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

@DisplayName("Class Status")
class StatusTest
{
    public static final String ID = "id";
    public static final String STATUS = "status";
    public static final String DESCRIPTION = "description";

    Status status;

    @Test
    @DisplayName("is instantiated with new Status()")
    void isInstantiatedWithNew() {
        new Status();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            status = new Status();
        }

        @Test
        @DisplayName("default values in Status()")
        void defaults()
        {
            assertThat(status).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(status).hasFieldOrPropertyWithValue(STATUS, null);
            assertThat(status).hasFieldOrPropertyWithValue(DESCRIPTION, null);
        }

        @Test
        @DisplayName("use setter and getter for status")
        void testGetSetName()
        {
            status.setStatus(TEST_STR1);
            assertThat(status).hasFieldOrPropertyWithValue(STATUS, TEST_STR1);
            assertEquals(TEST_STR1, status.getStatus());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            status.setDescription(TEST_STR1);
            assertThat(status).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_STR1);
            assertEquals(TEST_STR1, status.getDescription());
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(status.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            status = new Status(TEST_ID1, TEST_STATUS, TEST_DESCRIPTION);
        }

        @Test
        @DisplayName("initialized values in Status()")
        void defaults()
        {
            assertThat(status).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(status).hasFieldOrPropertyWithValue(STATUS, TEST_STATUS);
            assertThat(status).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
        }

        @Test
        @DisplayName("equals for class Status and hashCode")
        void testEquals()
        {
            assertNotEquals(new Status(), status);
            final Status expected = new Status(TEST_ID1, TEST_STATUS, TEST_DESCRIPTION);
            assertEquals(expected.hashCode(), status.hashCode());
            assertEquals(expected, status);
        }
    }
}