/*
 * ConfigurationUnitTest.java
 * This file was last modified at 2019-01-26 11:30 by Victor N. Skurikhin.
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

@DisplayName("Class ConfigurationUnit")
class ConfigurationUnitTest
{
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String GROUP = "group";
    public static final String OWNER = "owner";
    public static final String TYPE = "type";

    ConfigurationUnit cunit;

    @Test
    @DisplayName("is instantiated with new ConfigurationUnit()")
    void isInstantiatedWithNew() {
        new ConfigurationUnit();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            cunit = new ConfigurationUnit();
        }

        @Test
        @DisplayName("default values in ConfigurationUnit()")
        void defaults()
        {
            assertThat(cunit).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(cunit).hasFieldOrPropertyWithValue(NAME, null);
            assertThat(cunit).hasFieldOrPropertyWithValue(DESCRIPTION, null);
            assertThat(cunit).hasFieldOrPropertyWithValue(GROUP, null);
            assertThat(cunit).hasFieldOrPropertyWithValue(OWNER, null);
            assertThat(cunit).hasFieldOrPropertyWithValue(TYPE, null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            cunit.setName(TEST_STR1);
            assertThat(cunit).hasFieldOrPropertyWithValue(NAME, TEST_STR1);
            assertEquals(TEST_STR1, cunit.getName());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            cunit.setDescription(TEST_STR1);
            assertThat(cunit).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_STR1);
            assertEquals(TEST_STR1, cunit.getDescription());
        }
        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(cunit.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            cunit = new ConfigurationUnit(
                TEST_ID1, TEST_NAME, TEST_DESCRIPTION, createGroup1(), createUser1(), createConfigurationType1()
            );
        }

        @Test
        @DisplayName("initialized values in ConfigurationUnit()")
        void defaults()
        {
            assertThat(cunit).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(cunit).hasFieldOrPropertyWithValue(NAME, TEST_NAME);
            assertThat(cunit).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
            assertThat(cunit).hasFieldOrPropertyWithValue(GROUP, TEST_GROUP1);
            assertThat(cunit).hasFieldOrPropertyWithValue(OWNER, TEST_USER1);
            assertThat(cunit).hasFieldOrPropertyWithValue(TYPE, TEST_CONFIGURATION_TYPE1);
        }

        @Test
        @DisplayName("equals for class ConfigurationUnit and hashCode")
        void testEquals()
        {
            assertNotEquals(new ConfigurationUnit(), cunit);
            final ConfigurationUnit expected = TEST_CONFIGURATION_UNIT1;
            assertEquals(expected.hashCode(), cunit.hashCode());
            assertEquals(expected, cunit);
        }
    }
}