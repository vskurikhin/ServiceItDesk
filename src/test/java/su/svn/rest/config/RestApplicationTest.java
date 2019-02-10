/*
 * RestApplicationTest.java
 * This file was last modified at 2019-01-26 23:07 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Class RestApplication")
class RestApplicationTest
{
    RestApplication restApplication;

    @Test
    @DisplayName("is instantiated with new RestApplication()")
    void isInstantiatedWithNew() {
        new RestApplication();
    }
}