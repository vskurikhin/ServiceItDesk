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
            assertThat(cunit).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(cunit).hasFieldOrPropertyWithValue("name", null);
            assertThat(cunit).hasFieldOrPropertyWithValue("description", null);
            assertThat(cunit).hasFieldOrPropertyWithValue("group", null);
            assertThat(cunit).hasFieldOrPropertyWithValue("owner", null);
            assertThat(cunit).hasFieldOrPropertyWithValue("type", null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            cunit.setName(TEST_STR1);
            assertThat(cunit).hasFieldOrPropertyWithValue("name", TEST_STR1);
            assertEquals(TEST_STR1, cunit.getName());
        }

        @Test
        @DisplayName("use setter and getter for description")
        void testGetSetDescription()
        {
            cunit.setDescription(TEST_STR1);
            assertThat(cunit).hasFieldOrPropertyWithValue("description", TEST_STR1);
            assertEquals(TEST_STR1, cunit.getDescription());
        }
        @Test
        @DisplayName("The length of string from Genre::toString is great than zero")
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
            assertThat(cunit).hasFieldOrPropertyWithValue("id", 1L);
            assertThat(cunit).hasFieldOrPropertyWithValue("name", TEST_NAME);
            assertThat(cunit).hasFieldOrPropertyWithValue("description", TEST_DESCRIPTION);
            assertThat(cunit).hasFieldOrPropertyWithValue("group", TEST_GROUP1);
            assertThat(cunit).hasFieldOrPropertyWithValue("owner", TEST_USER1);
            assertThat(cunit).hasFieldOrPropertyWithValue("type", TEST_CONFIGURATION_TYPE1);
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