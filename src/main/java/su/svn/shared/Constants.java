/*
 * Constants.java
 * This file was last modified at 2019-02-03 20:08 by Victor N. Skurikhin.
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
