/*
 * IncidentTest.java
 * This file was last modified at 2019-01-26 17:43 by Victor N. Skurikhin.
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

@DisplayName("Class Incident")
class IncidentTest
{
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";

    Incident incident;

    @Test
    @DisplayName("is instantiated with new Incident()")
    void isInstantiatedWithNew() {
        new Incident();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            incident = new Incident();
        }

        @Test
        @DisplayName("default values in Incident()")
        void defaults()
        {
            assertThat(incident).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(incident).hasFieldOrPropertyWithValue(TITLE, null);
            assertThat(incident).hasFieldOrPropertyWithValue(DESCRIPTION, null);
            assertThat(incident).hasFieldOrPropertyWithValue("consumer", null);
            assertThat(incident).hasFieldOrPropertyWithValue("status", null);
        }

        @Test
        @DisplayName("use setter and getter for title")
        void testGetSetTitle()
        {
            incident.setTitle(TEST_STR1);
            assertThat(incident).hasFieldOrPropertyWithValue(TITLE, TEST_STR1);
            assertEquals(TEST_STR1, incident.getTitle());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            incident.setDescription(TEST_STR1);
            assertThat(incident).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_STR1);
            assertEquals(TEST_STR1, incident.getDescription());
        }

        @Test
        @DisplayName("use setter and getter for consumer")
        void testGetSetConsumer()
        {
            incident.setConsumer(TEST_USER1);
            assertThat(incident).hasFieldOrPropertyWithValue("consumer", TEST_USER1);
            assertEquals(TEST_USER1, incident.getConsumer());
        }

        @Test
        @DisplayName("use setter and getter for status")
        void testGetSetStatus()
        {
            incident.setStatus(TEST_STATUS1);
            assertThat(incident).hasFieldOrPropertyWithValue("status", TEST_STATUS1);
            assertEquals(TEST_STATUS1, incident.getStatus());
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(incident.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            incident = new Incident(TEST_ID1, TEST_TITLE, TEST_DESCRIPTION, TEST_USER1, TEST_STATUS1);
        }

        @Test
        @DisplayName("initialized values in Incident()")
        void defaults()
        {
            assertThat(incident).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(incident).hasFieldOrPropertyWithValue(TITLE, TEST_TITLE);
            assertThat(incident).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
            assertThat(incident).hasFieldOrPropertyWithValue("consumer", TEST_USER1);
            assertThat(incident).hasFieldOrPropertyWithValue("status", TEST_STATUS1);
        }

        @Test
        @DisplayName("equals for class Incident and hashCode")
        void testEquals()
        {
            assertNotEquals(new Incident(), incident);
            final Incident expected = new Incident(TEST_ID1, TEST_TITLE, TEST_DESCRIPTION, TEST_USER1, TEST_STATUS1);
            assertEquals(expected.hashCode(), incident.hashCode());
            assertEquals(expected, incident);
        }
    }
}