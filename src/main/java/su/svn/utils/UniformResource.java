/*
 * UniformResource.java
 * This file was last modified at 2019-02-10 16:26 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;

public class UniformResource
{
    public static String getRequestURI(HttpServletRequest request)
    {
        return request.getRequestURI();
    }

    public static String getRequestURL(HttpServletRequest request)
    {
        return request.getRequestURL().toString();
    }

    public static String getBaseURL(HttpServletRequest request)
    {
        return getRequestURL(request).replaceFirst(getRequestURI(request),"") + request.getContextPath();
    }

    public static URI getLocation(StringBuffer url, Long id)
    {
        return URI.create(url.append('/').append(id).toString());
    }
}
