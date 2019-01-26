/*
 * ConfigurationTypeTest.java
 * This file was last modified at 2019-01-26 11:28 by Victor N. Skurikhin.
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

@DisplayName("Class ConfigurationType")
class ConfigurationTypeTest
{
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";

    ConfigurationType ctype;

    @Test
    @DisplayName("is instantiated with new ConfigurationType()")
    void isInstantiatedWithNew() {
        new ConfigurationType();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            ctype = new ConfigurationType();
        }

        @Test
        @DisplayName("default values in ConfigurationType()")
        void defaults()
        {
            assertThat(ctype).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(ctype).hasFieldOrPropertyWithValue(NAME, null);
            assertThat(ctype).hasFieldOrPropertyWithValue(DESCRIPTION, null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            ctype.setName(TEST_STR1);
            assertThat(ctype).hasFieldOrPropertyWithValue(NAME, TEST_STR1);
            assertEquals(TEST_STR1, ctype.getName());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            ctype.setDescription(TEST_STR1);
            assertThat(ctype).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_STR1);
            assertEquals(TEST_STR1, ctype.getDescription());
        }
        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(ctype.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            ctype = new ConfigurationType(TEST_ID1, TEST_NAME, TEST_DESCRIPTION);
        }

        @Test
        @DisplayName("initialized values in ConfigurationType()")
        void defaults()
        {
            assertThat(ctype).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(ctype).hasFieldOrPropertyWithValue(NAME, TEST_NAME);
            assertThat(ctype).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
        }

        @Test
        @DisplayName("equals for class ConfigurationType and hashCode")
        void testEquals()
        {
            assertNotEquals(new ConfigurationType(), ctype);
            ConfigurationType expected = TEST_CONFIGURATION_TYPE1;
            assertEquals(expected.hashCode(), ctype.hashCode());
            assertEquals(expected, ctype);
        }
    }
}