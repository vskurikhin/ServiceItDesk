/*
 * ConfigurationUnitTest.java
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

@DisplayName("Class ConfigurationUnit")
class ConfigurationUnitTest
{
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String GROUP = "group";
    private static final String OWNER = "owner";
    private static final String TYPE = "type";

    private ConfigurationUnit cunit;

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
            assertThat(cunit).hasFieldOrPropertyWithValue("admin", null);
            assertThat(cunit).hasFieldOrPropertyWithValue(OWNER, null);
            assertThat(cunit).hasFieldOrPropertyWithValue(GROUP, null);
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
        void isValidForSave()
        {
            assertFalse(ConfigurationUnit.isValidForSave(cunit));
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
        User newUser1 = createUser0();
        User newUser2 = createUser0();
        Group group = createGroup1();
        ConfigurationType type = createConfigurationType1();

        @BeforeEach
        void createNew()
        {
            cunit = new ConfigurationUnit(TEST_ID1, TEST_NAME, TEST_DESCRIPTION, newUser1, newUser2, group, type);
        }

        @Test
        @DisplayName("initialized values in ConfigurationUnit()")
        void defaults()
        {
            assertThat(cunit).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(cunit).hasFieldOrPropertyWithValue(NAME, TEST_NAME);
            assertThat(cunit).hasFieldOrPropertyWithValue(DESCRIPTION, TEST_DESCRIPTION);
            assertThat(cunit).hasFieldOrPropertyWithValue("admin", newUser1);
            assertThat(cunit).hasFieldOrPropertyWithValue(OWNER, newUser2);
            assertThat(cunit).hasFieldOrPropertyWithValue(GROUP, group);
            assertThat(cunit).hasFieldOrPropertyWithValue(TYPE, type);
        }

        @Test
        @DisplayName("equals for class ConfigurationUnit and hashCode")
        void testEquals()
        {
            assertNotEquals(new ConfigurationUnit(), cunit);
            final ConfigurationUnit expected = new ConfigurationUnit(
                TEST_ID1, TEST_NAME, TEST_DESCRIPTION, newUser1, newUser2, group, type
            );
            assertEquals(expected.hashCode(), cunit.hashCode());
            assertEquals(expected, cunit);
        }

        @Test
        void isValidForSave()
        {
            assertTrue(ConfigurationUnit.isValidForSave(cunit));
        }
    }
}