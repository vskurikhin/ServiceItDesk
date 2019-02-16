/*
 * RolesServlet.java
 * This file was last modified at 2019-02-16 14:41 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package su.svn.servlets;

import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import static su.svn.shared.Constants.Security.*;
import static su.svn.shared.Constants.Servlet.ROLES;

@WebServlet("/" + ROLES)
public class RolesServlet extends HttpServlet
{
    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Principal principal = request.getUserPrincipal();

        try (PrintWriter out = response.getWriter()) {
            out.println("principal = " + principal);
            out.println("securityContext = " + securityContext.getCallerPrincipal());
            out.println("ACTUARY = " + securityContext.isCallerInRole(ROLE_ACTUARY));
            out.println("ADMIN = " + securityContext.isCallerInRole(ROLE_ADMIN));
            out.println("COORDINATOR = " + securityContext.isCallerInRole(ROLE_COORDINATOR));
            out.println("SUPER = " + securityContext.isCallerInRole(ROLE_SUPERUSER));
            out.println("USER = " + securityContext.isCallerInRole(ROLE_USER));
            String sessionId = request.getSession().getId();
            out.println("sessionId = " + sessionId);
        }
    }
}
