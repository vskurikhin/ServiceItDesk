/*
 * RolesServlet.java
 * This file was last modified at 2019-02-11 21:49 by Victor N. Skurikhin.
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

@WebServlet("/roles")
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
            out.println("ADMIN = " + securityContext.isCallerInRole("ADMIN"));
            out.println("ACTUARY = " + securityContext.isCallerInRole("ACTUARY"));
            out.println("COORDINATOR = " + securityContext.isCallerInRole("COORDINATOR"));
            out.println("USER = " + securityContext.isCallerInRole("USER"));
            String sessionId = request.getSession().getId();
            out.println("sessionId = " + sessionId);
        }
    }
}
