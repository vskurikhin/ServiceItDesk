/*
 * LogoutServlet.java
 * This file was last modified at 2019-02-11 23:50 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.servlets;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static su.svn.shared.Constants.Security.ROLE_ACTUARY;
import static su.svn.shared.Constants.Security.ROLE_ADMIN;
import static su.svn.shared.Constants.Security.ROLE_COORDINATOR;

@WebServlet("/welcome.do")
public class WelcomeServlet extends HttpServlet
{
    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (securityContext.isCallerInRole(ROLE_ACTUARY)) {
            response.sendRedirect(request.getContextPath() + "/actuaries/index.jsp");
        }
        else if (securityContext.isCallerInRole(ROLE_ADMIN)) {
            response.sendRedirect(request.getContextPath() + "/admins/index.jsp");
        }
        else if (securityContext.isCallerInRole(ROLE_COORDINATOR)) {
            response.sendRedirect(request.getContextPath() + "/coordinators/index.jsp");
        }
        else {
            response.sendRedirect(request.getContextPath() + "/welcome.jsp");
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
