/*
 * Constants.java
 * This file was last modified at 2019-02-04 21:38 by Victor N. Skurikhin.
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

        public static final String USER_RESOURCE = "/users";

        public static final String VERSION = "version";
    }

    public static class Servlet
    {
        public static final String CMDB_GROUP_JSP = "/cmdb/group.jsp";
        public static final String CMDB_GROUPS_JSP = "/cmdb/groups.jsp";
        public static final String CMDB_GROUP_SERVLET = "/cmdb/group.do";
        public static final String CMDB_GROUPS_SERVLET = "/cmdb/groups.do";

    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
