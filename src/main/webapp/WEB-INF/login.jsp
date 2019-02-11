<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  ~ welcome.jsp
  ~ This file was last modified at 2019-02-09 10:13 by Victor N. Skurikhin.
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
    <title id="title">Service It Desk | JavaEE-09-2018 welcome</title>
    <link rel="stylesheet" href="<c:out value="${baseURL}/"/>css/style-all.min.css"/>
</head>

<body class="body">
    <table class="tg body">
<%@ include file = "menu.jsp" %>
        <tr>
            <td class="tg-0lax my-left-aside" style="border-right: 2px dotted black; height: 600px;">
                <%@ include file = "aside.jsp" %>
            </td>
            <td class="tg-0lax my-right-main">
                <main id="main" class="w3-col m12 w3-margin-0 my-left">
                    <div id="main-container">
                    <!-- form method="post" action="j_security_check" class="loginForm" onsubmit="return checkPassword(this);" -->
                    <form method="post" action="j_security_check" class="loginForm">
                        <div class="w3-row login-margin">
                            <div class="w3-col m1 text-margin">
                                <label for="login">Login: </label>
                            </div>
                            <div class="w3-col m2">
                                <input type="text" id="login" name="j_username" autocomplete="off" placeholder="Type your login"/>
                            </div>
                        </div>
                        <div class="w3-row login-margin">
                            <div class="w3-col m1 text-margin">
                                <label for="password">Password: </label>
                            </div>
                            <div class="w3-col m2">
                                <input type="password" id="password" name="j_password" autocomplete="off" placeholder="Type your password"/>
                            </div>
                        </div>
                        <div class="w3-row login-margin">
                            <div class="w3-col m1 submitArea text-margin">
                                <input type="submit" id="submit" value="Logon"/>
                            </div>
                        </div>
                    </form>
                    </div>
                </main>
            </td>
        </tr>
<%@ include file = "footer.jsp" %>
    </table>
</body>
</html>
