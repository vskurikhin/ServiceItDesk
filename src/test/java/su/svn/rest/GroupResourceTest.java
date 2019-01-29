/*
 * GroupResourceTest.java
 * This file was last modified at 2019-01-28 21:33 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class GroupResourceTest extends JerseyTest
{
    @Before
    public void setUpChild() { }

    @After
    public void tearDownChild() { }

    @Override
    protected Application configure() {
        return new ResourceConfig(GroupResource.class);
    }

    @Test
    public void ordersPathParamTest() {
        String response = target("v1/groups").request().get(String.class);
        System.out.println("response = " + response);
        // assertTrue("orderId: 453".equals(response));
    }
}