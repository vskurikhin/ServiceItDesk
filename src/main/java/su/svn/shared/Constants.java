/*
 * Constants.java
 * This file was last modified at 2019-02-16 14:39 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.shared;

public class Constants
{
    public static class Db
    {
        public static final String PERSISTENCE_UNIT_NAME = "jpa";
    }

    public static class Rest
    {
        public static final String API_URL = "/rest/api";
        public static final String CONFIGURATION_TYPE_RESOURCE = "/configuration-types";
        public static final String CONFIGURATION_UNIT_RESOURCE = "/configuration-units";
        public static final String GROUP_RESOURCE = "/groups";
        public static final String INCIDENT_RESOURCE = "/incidents";
        public static final String MESSAGE_RESOURCE = "/messages";
        public static final String STATUS_RESOURCE = "/statuses";
        public static final String TASK_RESOURCE = "/tasks";
        public static final String USER_RESOURCE = "/users";
        public static final String VERSION = "version";
    }

    public static class Security
    {
        public static final String ROLE_ADMIN = "ADMIN";
        public static final String ROLE_ACTUARY = "ACTUARY";
        public static final String ROLE_COORDINATOR = "COORDINATOR";
        public static final String ROLE_USER= "USER";
        public static final String ROLE_SUPERUSER= "SUPER";
    }

    public static class Servlet
    {
        public static final String LOGOUT = "logout.do";
        public static final String ROLES = "roles";
        public static final String WELCOME = "welcome.do";
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
