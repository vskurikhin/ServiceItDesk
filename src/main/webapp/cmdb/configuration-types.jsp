<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ configuration-types.jsp
  ~ This file was last modified at 2019-02-04 23:44 by Victor N. Skurikhin.
  ~ $Id$
  ~ This is free and unencumbered software released into the public domain.
  ~ For more information, please refer to <http://unlicense.org>
  --%>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/" />

<!DOCTYPE html>
<html lang=ru>
<head>
    <meta charset=UTF-8>
    <title id="title">Service It Desk CMDB Console | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href="<c:out value="${baseURL}"/>css/style-all.min.css"/>
</head>

<body class="body">
    <h1>Let's go!</h1>
    <table class="tg body">
<%@ include file = "menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left">
                <main id="main" class="w3-col m12 w3-margin-0 my-left">
                    <div id="main-container"></div>
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
