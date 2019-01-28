/*
 * MessageTest.java
 * This file was last modified at 2019-01-26 11:31 by Victor N. Skurikhin.
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

@DisplayName("Class Message")
class MessageTest
{
    public static final String ID = "id";
    public static final String TEXT = "text";

    Message message;

    @Test
    @DisplayName("is instantiated with new Message()")
    void isInstantiatedWithNew() {
        new Message();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            message = new Message();
        }

        @Test
        @DisplayName("default values in Message()")
        void defaults()
        {
            assertThat(message).hasFieldOrPropertyWithValue(ID, 0L);
            assertThat(message).hasFieldOrPropertyWithValue(TEXT, null);
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            message.setText(TEST_STR1);
            assertThat(message).hasFieldOrPropertyWithValue(TEXT, TEST_STR1);
            assertEquals(TEST_STR1, message.getText());
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(message.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            message = new Message(TEST_ID1, TEST_TEXT);
        }

        @Test
        @DisplayName("initialized values in Message()")
        void defaults()
        {
            assertThat(message).hasFieldOrPropertyWithValue(ID, 1L);
            assertThat(message).hasFieldOrPropertyWithValue(TEXT, TEST_TEXT);
        }

        @Test
        @DisplayName("equals for class Message and hashCode")
        void testEquals()
        {
            assertNotEquals(new Message(), message);
            final Message expected = new Message(TEST_ID1, TEST_TEXT);
            assertEquals(expected.hashCode(), message.hashCode());
            assertEquals(expected, message);
        }
    }
}