/*
 * RestApplication.java
 * This file was last modified at 2019-02-03 15:28 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.rest.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import su.svn.rest.GroupResource;
import su.svn.rest.UserResource;
import su.svn.rest.Version;

import javax.ws.rs.ApplicationPath;

import java.nio.charset.StandardCharsets;

import static su.svn.rest.config.RestApplication.API_URL;

@ApplicationPath(API_URL)
public class RestApplication extends ResourceConfig
{
    public static final String API_URL = "/rest/api";

    public static final String CONFIGURATION_TYPE_RESOURCE = "/configuration-types";

    public static final String CONFIGURATION_UNIT_RESOURCE = "/configuration-units";

    public static final String GROUP_RESOURCE = "/groups";

    public static final String INCIDENT_RESOURCE = "/incidents";

    public static final String MESSAGE_RESOURCE = "/messages";

    public static final String STATUS_RESOURCE = "/statuses";

    public static final String USER_RESOURCE = "/users";

    private static final String ENCODING_PROPERTY = "encoding";

    private static final String DEFAULT_SCHEME_NAME = "http";

    public RestApplication(){
        registerResourceClasses();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
        jacksonProvider.setMapper(objectMapper);
        register(jacksonProvider);

        property(ENCODING_PROPERTY, StandardCharsets.UTF_8.toString());
    }

    private void registerResourceClasses() {
        register(GroupResource.class);
        register(UserResource.class);
        register(Version.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
