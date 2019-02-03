<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import = "su.svn.shared.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ group.jsp
  ~ This file was last modified at 2019-02-03 22:06 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Service It Desk CMDB Console Группа | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href="<c:out value="${baseURL}/"/>css/style-all.min.css"/>
</head>

<body class="body">
    <h1>Let's go!</h1>
    <table class="tg body">
<%@ include file = "menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left">
                <main id="main" class="w3-col m12 w3-margin-0 my-left">

                    <form id="group" action="${baseURL}<%= Constants.Servlet.CMDB_GROUP_SERVLET %>" method="post">
                        <ol>
                            <li><input form="group" name="id" value="${group.id}" readonly title="id: "/></li>
                            <li><input form="group" name="name" value="${group.name}" title="name: "/></li>
                            <li><input form="group" name="description" value="${group.description}" title="description"/></li>
                            <p>
                                <input type="submit" value="Сохранить" form="group"/>
                                <input form="groupDelete" type="submit" value="Удалить"/>
                            </p>
                        </ol>
                    </form>
                    <form id="groupDelete" action="${baseURL}<%= Constants.Servlet.CMDB_GROUP_SERVLET %>" method="detete" if-match="*">
                        <input form="groupDelete" name="id" type="text" value="${group.id}" hidden  title=""/>
                        <input form="groupDelete" type="hidden" name="_method" value="DELETE">
                    </form>
                </main>
            </td>
            <td class="tg-0lax my-right" style="border-left: 2px dotted black;" colspan="2">
<%@ include file = "aside.jsp" %>
            </td>
        </tr>
<%@ include file = "footer.jsp" %>
    </table>
</body>
</html>
