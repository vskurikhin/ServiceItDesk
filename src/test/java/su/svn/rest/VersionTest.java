/*
 * VersionTest.java
 * This file was last modified at 2019-01-26 23:10 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class Version")
class VersionTest
{
    Version version;

    @Test
    @DisplayName("is instantiated with new Version()")
    void isInstantiatedWithNew() {
        new Version();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            version = new Version();
        }

        @Test
        @DisplayName("use setter and getter for name")
        void testGetSetName()
        {
            Response response = version.onJson();
            assertEquals(200, response.getStatus());
        }
    }
}